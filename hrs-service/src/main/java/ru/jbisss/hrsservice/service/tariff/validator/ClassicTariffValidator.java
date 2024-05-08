package ru.jbisss.hrsservice.service.tariff.validator;

import org.springframework.stereotype.Service;
import ru.jbisss.hrsservice.domain.CdrWithTariff.CdrRow;
import ru.jbisss.hrsservice.entity.ServiceInTariffEntity;
import ru.jbisss.hrsservice.entity.TariffEntity;
import ru.jbisss.hrsservice.repository.RemainsRepository;
import ru.jbisss.hrsservice.repository.ServiceInTariffRepository;
import ru.jbisss.hrsservice.repository.ServiceRepository;
import ru.jbisss.hrsservice.repository.TariffRepository;
import ru.jbisss.hrsservice.service.callDuration.ICallDurationService;

@Service
public class ClassicTariffValidator extends AbstractValidator {

    public ClassicTariffValidator(ICallDurationService callDurationService, TariffRepository tariffRepository, ServiceRepository serviceRepository, ServiceInTariffRepository serviceInTariffRepository, RemainsRepository remainsRepository) {
        super(callDurationService, tariffRepository, serviceRepository, serviceInTariffRepository, remainsRepository);
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

    // TODO нужно запрашивать информацию у BRT
    private boolean isPhoneNumberRomaskaAbonent(String phoneNumber) {
        return false;
    }

    private double defineInCallRate() {
        ServiceInTariffEntity inAny = getServiceByNameInTariff(getCorrespondingTariff(), "IN_ANY");
        return inAny.getCost().doubleValue();
    }
}
