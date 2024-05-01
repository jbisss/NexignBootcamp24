package ru.jbisss.hrsservice.repository;

import org.springframework.data.repository.CrudRepository;
import ru.jbisss.hrsservice.entity.ServiceEntity;

public interface ServiceRepository extends CrudRepository<ServiceEntity, Integer> {
}
