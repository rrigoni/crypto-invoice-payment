package com.sendwyre.invoice.invoice.model.service;


import com.sendwyre.invoice.invoice.model.entity.Invoice;
import com.sendwyre.invoice.invoice.model.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class InvoiceService {

    @Autowired
    private WalletService walletService;
    @Autowired
    private InvoiceRepository invoiceRepository;

    public Invoice initInvoiceAndSave(Invoice invoice) {
        invoice.setAddress(walletService.getNetReceivingAddress());
        return invoiceRepository.save(invoice);
    }

    public List<Invoice> findAll() {
        return invoiceRepository.findAll();
    }

}
