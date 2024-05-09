package ru.jbisss.brtservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.jbisss.brtservice.entity.AbonentEntity;

import java.util.Optional;

public interface AbonentRepository extends JpaRepository<AbonentEntity, Integer> {

    Optional<AbonentEntity> findByPhoneNumber(String phoneNumber);
}
