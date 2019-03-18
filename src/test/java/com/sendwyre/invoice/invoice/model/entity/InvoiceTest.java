package com.sendwyre.invoice.invoice.model.entity;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class InvoiceTest {

    private Invoice invoice;

    @Before
    public void setup() {
        invoice = new Invoice();
    }

    @Test
    public void testPaidStatus() {
        final Calendar date = Calendar.getInstance();
        date.add(Calendar.HOUR, -10);

        invoice.setCreatedAt(date.getTime());
        invoice.setPaidAmount(10);
        invoice.setTotalAmount(10);

        assertEquals(InvoiceState.PAID, invoice.getStatus());
    }


    @Test
    public void testPaidStatusButExpired() {
        final Calendar date = Calendar.getInstance();
        date.add(Calendar.HOUR, -30);

        invoice.setCreatedAt(date.getTime());
        invoice.setPaidAmount(10);
        invoice.setTotalAmount(10);

        // even if expired time status must be paid
        assertEquals(InvoiceState.PAID, invoice.getStatus());
    }



    @Test
    public void testPartiallyPaid() {
        final Calendar date = Calendar.getInstance();
        date.add(Calendar.HOUR, -10);

        invoice.setCreatedAt(date.getTime());
        invoice.setPaidAmount(5);
        invoice.setTotalAmount(10);

        assertEquals(InvoiceState.PARTIALLY_PAID, invoice.getStatus());
    }



    @Test
    public void testExpired() {
        final Calendar date = Calendar.getInstance();
        date.add(Calendar.HOUR, -25);

        invoice.setCreatedAt(date.getTime());
        invoice.setPaidAmount(0);
        invoice.setTotalAmount(10);

        assertEquals(InvoiceState.EXPIRED, invoice.getStatus());
    }


}