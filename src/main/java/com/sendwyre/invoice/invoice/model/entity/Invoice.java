package com.sendwyre.invoice.invoice.model.entity;

import org.bitcoinj.core.Coin;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Entity
public final class Invoice {

    private static final long MILLIS_IN_A_DAY  = 86400000l;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String address;
    private long totalAmount;
    private long paidAmount;
    private CoinType coinType = CoinType.BTC;
    private InvoiceState invoiceState;
    private String description;
    private Date createdAt = new Date();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InvoiceItem> items;
    @Transient
    private List<Transaction> transactions;


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public long getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(long paidAmount) {
        this.paidAmount = paidAmount;
    }

    public CoinType getCoinType() {
        return coinType;
    }

    public void setCoinType(CoinType coinType) {
        this.coinType = coinType;
    }

    public InvoiceState getInvoiceState() {
        return invoiceState;
    }

    public void setInvoiceState(InvoiceState invoiceState) {
        this.invoiceState = invoiceState;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<InvoiceItem> getItems() {
        return items;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setItems(List<InvoiceItem> items) {
        this.items = items;
    }

    public String getCryptoAmout() {
        return Coin.valueOf(totalAmount).toFriendlyString();
    }

    public String getCryptoPaidAmount() {
        return Coin.valueOf(paidAmount).toFriendlyString();
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public InvoiceState getStatus() {
        long elapsedCreationTimeInMillis = (System.currentTimeMillis() - createdAt.getTime());
        if (elapsedCreationTimeInMillis > MILLIS_IN_A_DAY) {
            if (paidAmount >= totalAmount) {
                return InvoiceState.PAID;
            } else {
                return InvoiceState.EXPIRED;
            }
        }
        if (paidAmount >= totalAmount) {
            return InvoiceState.PAID;
        } else {
            return InvoiceState.PARTIALLY_PAID;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return Objects.equals(id, invoice.id) &&
                Objects.equals(address, invoice.address);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, address);
    }
}
