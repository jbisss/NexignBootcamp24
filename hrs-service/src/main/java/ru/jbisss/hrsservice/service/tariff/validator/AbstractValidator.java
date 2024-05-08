package ru.jbisss.hrsservice.service.tariff.validator;

import lombok.RequiredArgsConstructor;
import ru.jbisss.hrsservice.domain.CdrWithTariff.CdrRow;
import ru.jbisss.hrsservice.entity.ServiceInTariffEntity;
import ru.jbisss.hrsservice.entity.TariffEntity;
import ru.jbisss.hrsservice.repository.RemainsRepository;
import ru.jbisss.hrsservice.repository.ServiceInTariffRepository;
import ru.jbisss.hrsservice.repository.ServiceRepository;
import ru.jbisss.hrsservice.repository.TariffRepository;
import ru.jbisss.hrsservice.service.callDuration.ICallDurationService;

@RequiredArgsConstructor
abstract public class AbstractValidator implements TariffValidator {

    protected final ICallDurationService callDurationService;

    protected final TariffRepository tariffRepository;
    protected final ServiceRepository serviceRepository;
    protected final ServiceInTariffRepository serviceInTariffRepository;
    protected final RemainsRepository remainsRepository;

    protected ServiceInTariffEntity getServiceByNameInTariff(TariffEntity tariffEntity, String serviceName) {
        return tariffEntity.getServicesInTariffSet().stream()
                .filter(service -> service.getService().getServiceName().equals(serviceName))
                .findFirst()
                .get();
    }
}
