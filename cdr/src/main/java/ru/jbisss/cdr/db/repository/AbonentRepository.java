package ru.jbisss.cdr.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.jbisss.cdr.db.entity.AbonentEntity;

@Repository
public interface AbonentRepository extends JpaRepository<AbonentEntity, String> {
}
