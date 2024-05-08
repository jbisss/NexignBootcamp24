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

import java.util.Iterator;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RemainsInitializer implements IRemainsInitializer {

    private final TariffDefiner tariffDefiner;
    private final RemainsRepository remainsRepository;

    @Override
    public void initializeRemains(CdrWithTariff cdrWithTariff) {
        for (Iterator<CdrRow> it = cdrWithTariff.getCdrRows(); it.hasNext(); ) {
            CdrRow currentCdrRow = it.next();
            String callerPhoneNumber = currentCdrRow.getCallerPhoneNumber();
            TariffEntity tariffEntity = tariffDefiner.defineTariff(currentCdrRow.getTariff());

            Set<ServiceInTariffEntity> servicesInTariffSet = tariffEntity.getServicesInTariffSet();
            servicesInTariffSet.forEach(serviceInTariffEntity -> {
                RemainsEntity remainsEntity = new RemainsEntity();
                remainsEntity.setAbonentPhoneNumber(callerPhoneNumber);
                remainsEntity.setServiceInTariff(serviceInTariffEntity);
                remainsEntity.setRemains(serviceInTariffEntity.getCount());
                remainsRepository.save(remainsEntity);
            });
        }
    }
}
