package com.sendwyre.invoice.invoice.model.repository;

import com.sendwyre.invoice.invoice.model.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
