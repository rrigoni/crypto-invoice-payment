package com.sendwyre.invoice.invoice.model.service.implementation;

import org.bitcoinj.wallet.WalletFiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public final class BitcoinWalletSaverListener implements WalletFiles.Listener {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onBeforeAutoSave(File file) {
        logger.info("Saving wallet file " + file.getName());
    }

    @Override
    public void onAfterAutoSave(File file) {
        logger.info("File " + file.getName() + " saved");
    }
}
