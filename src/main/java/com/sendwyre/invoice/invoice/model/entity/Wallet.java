package com.sendwyre.invoice.invoice.model.entity;

import java.util.List;

public final class Wallet {

    public final String coinType;
    public final String balance;
    public final List<Transaction> transactions;

    public Wallet(String coinType, String balance, List<Transaction> transactions) {
        this.coinType = coinType;
        this.balance = balance;
        this.transactions = transactions;
    }

}
