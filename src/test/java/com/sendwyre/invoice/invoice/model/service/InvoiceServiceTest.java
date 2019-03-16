package com.sendwyre.invoice.invoice.model.service;

import com.sendwyre.invoice.invoice.AbstractTest;
import com.sendwyre.invoice.invoice.model.entity.CoinType;
import com.sendwyre.invoice.invoice.model.entity.Invoice;
import com.sendwyre.invoice.invoice.model.entity.InvoiceItem;
import com.sendwyre.invoice.invoice.model.entity.InvoiceState;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public final class InvoiceServiceTest extends AbstractTest {


    @Autowired
    private InvoiceService invoiceService;

    @Test
    public void testSaveInvoice() {
        final Invoice invoice = generateInvoice();
        invoiceService.initInvoiceAndSave(invoice);

        assertNotNull("Invoice address should be set before saving", invoice.getAddress());
    }

    @Test
    public void testInvoiceIsPersistedAndRetrieved() {
        final Invoice invoice = generateInvoice();
        invoiceService.initInvoiceAndSave(invoice);

        List<Invoice> all = invoiceService.findAll();
        assertTrue("Invoice should exist in the database", all.contains(invoice));
    }


    private Invoice generateInvoice() {
        Invoice invoice = new Invoice();
        invoice.setCoinType(CoinType.BTC);
        invoice.setDescription("some description");
        invoice.setInvoiceState(InvoiceState.PARTIALLY_PAID);
        invoice.setPaidAmount(0);
        invoice.setTotalAmount(100);

        final InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setDescription("some task");
        invoiceItem.setName("some name");
        invoiceItem.setPrice(100);
        invoiceItem.setQty(1);

        invoice.setItems(Arrays.asList(invoiceItem));
        return invoice;
    }

}