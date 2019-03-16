package com.sendwyre.invoice.invoice.model;

import javax.persistence.*;
import java.util.Date;


@Entity
public final class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String receivingAddress;
    private long total;
    private Date createdAt;

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

}
