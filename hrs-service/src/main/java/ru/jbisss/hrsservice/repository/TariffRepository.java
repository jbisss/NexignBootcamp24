package ru.jbisss.hrsservice.repository;

import org.springframework.data.repository.CrudRepository;
import ru.jbisss.hrsservice.entity.TariffEntity;

public interface TariffRepository extends CrudRepository<TariffEntity, Integer> {
}
