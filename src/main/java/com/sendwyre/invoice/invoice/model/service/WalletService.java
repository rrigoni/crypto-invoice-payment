package com.sendwyre.invoice.invoice.model.service;

import org.bitcoinj.core.*;
import org.bitcoinj.net.discovery.DnsDiscovery;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptException;
import org.bitcoinj.store.BlockStore;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.store.SPVBlockStore;
import org.bitcoinj.wallet.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public final class WalletService implements InitializingBean {

    private Wallet wallet;
    private final NetworkParameters networkParameters = TestNet3Params.get();
    private final File walletFile = new File("wallet.file");
    private final File blockstore = new File("blockstore.file");
    private PeerGroup peerGroup;


    @Override
    public void afterPropertiesSet() throws Exception {
        this.init();
    }

    private void init() throws BlockStoreException, UnreadableWalletException, IOException {
        if(walletFile.exists()) {
            wallet = Wallet.loadFromFile(walletFile);
        } else {
            wallet = Wallet.createDeterministic(networkParameters, Script.ScriptType.P2PKH);
        }
        wallet.saveToFile(walletFile);
        wallet.autosaveToFile(walletFile, 60, TimeUnit.SECONDS, new WalletFiles.Listener() {
            @Override
            public void onBeforeAutoSave(File file) {

            }

            @Override
            public void onAfterAutoSave(File file) {

            }
        });
        startSync();
    }

    private void startSync() throws BlockStoreException {
        // for convenience, each time the app starts we create a new wallet.
        // Walelt is not persisted after a restart.
//        if (walletFile.exists()) {
//            walletFile.delete();
//        }
        final BlockStore blockStore = new SPVBlockStore(networkParameters, blockstore);
        final BlockChain chain = new BlockChain(networkParameters, wallet, blockStore);
        peerGroup = new PeerGroup(networkParameters, chain);
        peerGroup.addWallet(wallet);
        peerGroup.addPeerDiscovery(new DnsDiscovery(networkParameters));
        peerGroup.startAsync();
    }

    public String getNetReceivingAddress() {
        return wallet.freshAddress(KeyChain.KeyPurpose.RECEIVE_FUNDS).toString();
    }


    public List<com.sendwyre.invoice.invoice.model.entity.Transaction> getWalletTransctions() {
        Set<Transaction> transactions = wallet.getTransactions(true);
        List<com.sendwyre.invoice.invoice.model.entity.Transaction> list = new ArrayList<>();
        for (Transaction transaction : transactions) {
            final String receivingAddress = getReceivingAddress(transaction);
            com.sendwyre.invoice.invoice.model.entity.Transaction tx = new com.sendwyre.invoice.invoice.model.entity.Transaction();
            tx.setReceivingAddress(receivingAddress);
            tx.setCreatedAt(transaction.getUpdateTime());
            tx.setTotal(transaction.getValue(wallet).value);
            tx.setStatus(transaction.getConfidence().getConfidenceType().name());
            list.add(tx);
        }
        return list;
    }


    private String getReceivingAddress(Transaction tx) {
        for (TransactionOutput output : tx.getOutputs()) {
            try {
                if(output.isMineOrWatched(wallet)) {
                    Script script = output.getScriptPubKey();
                    Address address = script.getToAddress(networkParameters, true);
                    return address.toString();
                }
            } catch (final ScriptException x) {
            }
        }
        return null;
    }

    public long getBalance() {
        return wallet.getBalance().value;
    }

    public String getFriendlyAmount(long balance) {
        return Coin.valueOf(balance).toFriendlyString();
    }
}
