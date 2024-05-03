package ru.jbisss.brtservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.jbisss.brtservice.entity.AbonentEntity;

public interface AbonentRepository extends JpaRepository<AbonentEntity, Integer> {

    AbonentEntity findByPhoneNumber(String phoneNumber);
}
