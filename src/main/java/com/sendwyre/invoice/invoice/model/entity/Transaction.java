package com.sendwyre.invoice.invoice.model.entity;

import org.bitcoinj.core.Coin;

import java.util.Date;

public final class Transaction {

    private String receivingAddress;
    private long total;
    private Date createdAt;

    public Transaction() { }

    public Transaction(String receivingAddress, long total, Date createdAt) {
        this.receivingAddress = receivingAddress;
        this.total = total;
        this.createdAt = createdAt;
    }

    public String getReceivingAddress() {
        return receivingAddress;
    }

    public void setReceivingAddress(String receivingAddress) {
        this.receivingAddress = receivingAddress;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getCryptoAmount() {
        return Coin.valueOf(total).toFriendlyString();
    }

}