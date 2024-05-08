package ru.jbisss.hrsservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.jbisss.hrsservice.entity.RemainsEntity;
import ru.jbisss.hrsservice.entity.ServiceInTariffEntity;

import java.util.Optional;

public interface RemainsRepository extends JpaRepository<RemainsEntity, Integer> {

    Optional<RemainsEntity> findByAbonentPhoneNumberAndServiceInTariff(String abonentPhoneNumber, ServiceInTariffEntity serviceInTariff);
}
