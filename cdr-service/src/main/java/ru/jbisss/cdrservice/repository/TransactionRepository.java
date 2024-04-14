package ru.jbisss.cdrservice.repository;

import org.springframework.data.repository.CrudRepository;
import ru.jbisss.cdrservice.entity.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
}
