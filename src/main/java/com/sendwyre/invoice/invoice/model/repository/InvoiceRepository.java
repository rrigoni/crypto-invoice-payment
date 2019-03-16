package com.sendwyre.invoice.invoice.model.repository;

import com.sendwyre.invoice.invoice.model.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}
