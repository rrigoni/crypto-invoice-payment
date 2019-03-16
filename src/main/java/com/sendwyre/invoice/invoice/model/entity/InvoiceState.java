package com.sendwyre.invoice.invoice.model.entity;

public enum InvoiceState {
    /**
     * Expired means if the payment was not completed in teh first 24h of the invoices creation
     */
    EXPIRED,
    /**
     * Paid means if the invoice was paid in 100% during the first 24h of its creation.
     */
    PAID,
    /**
     * Invoice was not completely cleared and the current time did not exceed 24 of the invoice's creation.
     */
    PARTIALLY_PAID
}
