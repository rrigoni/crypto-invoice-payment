package com.sendwyre.invoice.invoice.controller;

import com.sendwyre.invoice.invoice.model.Invoice;
import com.sendwyre.invoice.invoice.model.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public final class InvoiceController {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @GetMapping("/invoices") @ResponseBody final List<Invoice> all() {
        return invoiceRepository.findAll();
    }


    @PostMapping("/invoices") @ResponseBody final Invoice newInvoice(@RequestBody Invoice invoice) {
        return invoiceRepository.save(invoice);
    }



}
