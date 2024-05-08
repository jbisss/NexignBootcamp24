package ru.jbisss.hrsservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.jbisss.hrsservice.entity.DebtEntity;

import java.time.LocalDateTime;
import java.util.Optional;

public interface DebtRepository extends JpaRepository<DebtEntity, Integer> {

    Optional<DebtEntity> findByAbonentPhoneNumberAndDebtDate(String abonentPhoneNumber, LocalDateTime debtDate);
}
