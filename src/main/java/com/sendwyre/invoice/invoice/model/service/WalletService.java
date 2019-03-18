package com.sendwyre.invoice.invoice.model.service;

import com.sendwyre.invoice.invoice.model.entity.Transaction;
import com.sendwyre.invoice.invoice.model.entity.Wallet;

import java.util.List;

public interface WalletService {

    /**
     * Returns a new receiving address for the wallet. The address rotates, so any call should produce
     * a new address.
     * @return
     */
    String getNetReceivingAddress();

    /**
     * The list of all wallet transactions.
     * @return
     */
    List<Transaction> getWalletTransctions();

    /**
     * Returns the wallet balance in a long format.
     * @return
     */
    long getBalance();

    /**
     * Returns a friendly and human-readable balance String format.
     * @return
     */
    String getFriendlyBalanceString();

    /**
     * Get wallet information.
     * @return
     */
    Wallet getWallet();
}
