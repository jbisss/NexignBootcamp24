package ru.jbisss.securityservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.jbisss.securityservice.entity.UserCredential;

import java.util.Optional;

public interface UserCredentialRepository extends JpaRepository<UserCredential,Integer> {

    Optional<UserCredential> findByName(String username);
}
