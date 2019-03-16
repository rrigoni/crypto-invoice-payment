package com.sendwyre.invoice.invoice.controller;

import com.sendwyre.invoice.invoice.model.entity.Invoice;
import com.sendwyre.invoice.invoice.model.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public final class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping("/invoices") @ResponseBody final List<Invoice> all() {
        return invoiceService.findAll();
    }


    @PostMapping("/invoices") @ResponseBody final Invoice newInvoice(@RequestBody Invoice invoice) {
        return invoiceService.initInvoiceAndSave(invoice);
    }



}
