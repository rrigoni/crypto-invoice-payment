package com.sendwyre.invoice.invoice.model.entity;

import org.bitcoinj.core.Coin;

import java.util.List;

public class Wallet {

    private String balance;
    private List<Transaction> transactions;

    public Wallet(String balance, List<Transaction> transactions) {
        this.balance = balance;
        this.transactions = transactions;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
