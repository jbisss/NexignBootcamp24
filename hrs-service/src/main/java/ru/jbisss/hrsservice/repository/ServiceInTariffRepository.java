package ru.jbisss.hrsservice.repository;

import org.springframework.data.repository.CrudRepository;
import ru.jbisss.hrsservice.entity.ServiceInTariffEntity;

public interface ServiceInTariffRepository extends CrudRepository<ServiceInTariffEntity, Integer> {
}
