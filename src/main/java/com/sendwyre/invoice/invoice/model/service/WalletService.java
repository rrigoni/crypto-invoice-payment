package com.sendwyre.invoice.invoice.model.service;

import org.bitcoinj.core.*;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.script.Script;
import org.bitcoinj.store.BlockStore;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.store.MemoryBlockStore;
import org.bitcoinj.store.SPVBlockStore;
import org.bitcoinj.wallet.KeyChain;
import org.bitcoinj.wallet.Wallet;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public final class WalletService implements InitializingBean {

    private Wallet wallet;
    private final NetworkParameters networkParameters = TestNet3Params.get();
    private final File walletFile = new File("wallet.file");


    @Override
    public void afterPropertiesSet() throws Exception {
        this.init();
    }

    private void init() throws BlockStoreException {
        wallet = Wallet.createDeterministic(networkParameters, Script.ScriptType.P2PKH);
        startSync();
    }

    private void startSync() throws BlockStoreException {
        // for convenience, each time the app starts we create a new wallet.
        // Walelt is not persisted after a restart.
        if (walletFile.exists()) {
            walletFile.delete();
        }
        final BlockStore blockStore = new SPVBlockStore(networkParameters, walletFile);
        final BlockChain chain = new BlockChain(networkParameters, wallet, blockStore);
        final PeerGroup peerGroup = new PeerGroup(networkParameters, chain);
        peerGroup.start();
    }

    public String getNetReceivingAddress() {
        return wallet.freshAddress(KeyChain.KeyPurpose.RECEIVE_FUNDS).toString();
    }

}
