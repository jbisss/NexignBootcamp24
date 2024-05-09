package ru.jbisss.hrsservice.service.remains.initializer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.jbisss.hrsservice.domain.CdrWithTariff;
import ru.jbisss.hrsservice.domain.CdrWithTariff.CdrRow;
import ru.jbisss.hrsservice.entity.RemainsEntity;
import ru.jbisss.hrsservice.entity.ServiceInTariffEntity;
import ru.jbisss.hrsservice.entity.TariffEntity;
import ru.jbisss.hrsservice.repository.RemainsRepository;
import ru.jbisss.hrsservice.service.tariff.definer.TariffDefiner;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RemainsInitializer implements IRemainsInitializer {

    private final TariffDefiner tariffDefiner;
    private final RemainsRepository remainsRepository;

    @Override
    public void initializeRemains(CdrWithTariff cdrWithTariff) {
        final Set<String> initializedPhoneNumbers = new HashSet<>();
        for (Iterator<CdrRow> it = cdrWithTariff.getCdrRows(); it.hasNext(); ) {
            CdrRow currentCdrRow = it.next();
            String callerPhoneNumber = currentCdrRow.getCallerPhoneNumber();
            TariffEntity tariffEntity = tariffDefiner.defineTariff(currentCdrRow.getTariff());
            if (!initializedPhoneNumbers.contains(callerPhoneNumber)) {
                if (Objects.nonNull(tariffEntity.getAbonentPayment())) {
                    Set<ServiceInTariffEntity> servicesInTariffSet = tariffEntity.getServicesInTariffSet();
                    servicesInTariffSet.forEach(serviceInTariffEntity -> {
                        RemainsEntity remainsEntity = new RemainsEntity();
                        remainsEntity.setAbonentPhoneNumber(callerPhoneNumber);
                        remainsEntity.setServiceInTariff(serviceInTariffEntity);
                        Integer count = serviceInTariffEntity.getCount();
                        remainsEntity.setRemains(Objects.isNull(count) ? 0 : count);
                        remainsRepository.save(remainsEntity);
                    });
                    initializedPhoneNumbers.add(callerPhoneNumber);
                }
            }
        }
    }
}
