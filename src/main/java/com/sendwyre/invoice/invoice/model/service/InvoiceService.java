package com.sendwyre.invoice.invoice.model.service;


import com.sendwyre.invoice.invoice.model.entity.Invoice;
import com.sendwyre.invoice.invoice.model.entity.InvoiceItem;
import com.sendwyre.invoice.invoice.model.entity.Transaction;
import com.sendwyre.invoice.invoice.model.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public final class InvoiceService {

    @Autowired
    private WalletService walletService;
    @Autowired
    private InvoiceRepository invoiceRepository;

    public Invoice initInvoiceAndSave(Invoice invoice) {
        // we assign a unique address to the invoice to match incoming trasactions in the wallet.
        invoice.setAddress(walletService.getNetReceivingAddress());
        return invoiceRepository.save(invoice);
    }

    public List<Invoice> findAll() {
        final List<Invoice> all = invoiceRepository.findAll();
        for (Invoice invoice : all) {
            updateInvoice(invoice);
        }
        return all;
    }

    private void updateInvoice(Invoice invoice) {
        updateInvoiceTranscations(invoice);
        updateInvoiceAmounts(invoice);
    }

    private void updateInvoiceAmounts(Invoice invoice) {
        // reset payments values to prevent double summing
        invoice.setPaidAmount(0);
        invoice.setTotalAmount(0);
        for(Transaction transaction : invoice.getTransactions()) {
            invoice.setPaidAmount(invoice.getPaidAmount() + transaction.getTotal());
        }
        for(InvoiceItem invoiceItem : invoice.getItems()) {
            invoice.setTotalAmount(invoice.getTotalAmount() + (invoiceItem.getPrice() * invoiceItem.getQty()));
        }
    }


    public Invoice updateInvoiceTranscations(Invoice invoice) {
        final List<Transaction> transactions = new ArrayList<>();

        for (Transaction tx : walletService.getWalletTransctions()) {
            if(invoice.getAddress().equals(tx.getReceivingAddress())) {
                transactions.add(tx);
            }
        }
        invoice.setTransactions(transactions);
        return invoice;
    }


    public Invoice findById(Long id) {
        Invoice invoice = invoiceRepository.findById(id).orElse(null);
        if (invoice != null) {
            updateInvoice(invoice);
        }
        return invoice;
    }

}
