package ru.jbisss.cdrservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.jbisss.cdrservice.entity.Abonent;

public interface AbonentRepository extends JpaRepository<Abonent, Integer> {

    @Query("SELECT a.id FROM Abonent a WHERE a.phoneNumber = ?1")
    int findByPhoneNumber(String phoneNumber);
}
