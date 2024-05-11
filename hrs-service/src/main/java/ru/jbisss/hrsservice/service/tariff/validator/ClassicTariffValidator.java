package ru.jbisss.hrsservice.service.tariff.validator;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.DiscoveryManager;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.jbisss.hrsservice.domain.CdrWithTariff.CdrRow;
import ru.jbisss.hrsservice.entity.ServiceInTariffEntity;
import ru.jbisss.hrsservice.entity.TariffEntity;
import ru.jbisss.hrsservice.repository.RemainsRepository;
import ru.jbisss.hrsservice.repository.ServiceInTariffRepository;
import ru.jbisss.hrsservice.repository.ServiceRepository;
import ru.jbisss.hrsservice.repository.TariffRepository;
import ru.jbisss.hrsservice.service.callDuration.ICallDurationService;

import java.util.Objects;

@Service
public class ClassicTariffValidator extends AbstractValidator {

    private final RestTemplate restTemplate;

    public ClassicTariffValidator(ICallDurationService callDurationService, TariffRepository tariffRepository, ServiceRepository serviceRepository, ServiceInTariffRepository serviceInTariffRepository, RemainsRepository remainsRepository, RestTemplate restTemplate) {
        super(callDurationService, tariffRepository, serviceRepository, serviceInTariffRepository, remainsRepository);
        this.restTemplate = restTemplate;
    }

    @Override
    public TariffEntity getCorrespondingTariff() {
        return tariffRepository.findById(11).get();
    }

    @Override
    public double countDebt(CdrRow cdrRow, long minutes) {
        return defineCallPerMinuteRate(cdrRow) * minutes;
    }

    private double defineCallPerMinuteRate(CdrRow cdrRow) {
        if (cdrRow.getCallType().equals(CdrRow.CallType.OUT)) {
            return defineOutCallRate(cdrRow.getCallingPhoneNumber());
        }
        return defineInCallRate();
    }

    private double defineOutCallRate(String callingPhoneNumber) {
        ServiceInTariffEntity outRomashka = getServiceByNameInTariff(getCorrespondingTariff(), "OUT_ROMASHKA");
        ServiceInTariffEntity outOther = getServiceByNameInTariff(getCorrespondingTariff(), "OUT_OTHER");
        if (isPhoneNumberRomaskaAbonent(callingPhoneNumber)) {
            return outRomashka.getCost().doubleValue();
        }
        return outOther.getCost().doubleValue();
    }

    private boolean isPhoneNumberRomaskaAbonent(String phoneNumber) {
        String response = restTemplate.getForObject(String.format("http://%s/abonents/checkAbonent?phoneNumber=%s", getHostAndPortOfService("brt-service"), phoneNumber), String.class);
        return Objects.requireNonNull(response).equals("YES");
    }

    private String getHostAndPortOfService(String serviceName) {
        InstanceInfo instanceInfo = DiscoveryManager.getInstance().getDiscoveryClient().getNextServerFromEureka(serviceName, false);
        if (instanceInfo != null) {
            return instanceInfo.getHostName() + ":" + instanceInfo.getPort();
        } else {
            return null;
        }
    }

    private double defineInCallRate() {
        ServiceInTariffEntity inAny = getServiceByNameInTariff(getCorrespondingTariff(), "IN_ANY");
        return inAny.getCost().doubleValue();
    }
}
