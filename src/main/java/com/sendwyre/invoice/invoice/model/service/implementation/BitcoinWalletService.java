package com.sendwyre.invoice.invoice.model.service.implementation;

import com.sendwyre.invoice.invoice.model.entity.CoinType;
import com.sendwyre.invoice.invoice.model.service.WalletService;
import org.bitcoinj.core.*;
import org.bitcoinj.net.discovery.DnsDiscovery;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptException;
import org.bitcoinj.store.BlockStore;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.store.SPVBlockStore;
import org.bitcoinj.wallet.KeyChain;
import org.bitcoinj.wallet.UnreadableWalletException;
import org.bitcoinj.wallet.Wallet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public final class BitcoinWalletService implements InitializingBean, DisposableBean, WalletService {

    private static String COIN_SYMBOL = "BTC";

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private Wallet wallet;
    private NetworkParameters networkParameters;
    private final File walletFile = new File("wallet.file");
    private final File blockstore = new File("blockstore.file");
    private PeerGroup peerGroup;
    private BlockStore blockStore;
    @Value("${app.testmode}")
    private Boolean testMode;


    @Override
    public void afterPropertiesSet() throws Exception {
        this.initialize();
    }

    private void initialize() throws BlockStoreException, UnreadableWalletException, IOException {
        networkParameters = testMode ? TestNet3Params.get() : MainNetParams.get();
        if(walletFile.exists()) {
            wallet = Wallet.loadFromFile(walletFile);
        } else {
            wallet = Wallet.createDeterministic(networkParameters, Script.ScriptType.P2PKH);
        }
        wallet.saveToFile(walletFile);
        wallet.autosaveToFile(walletFile, 60, TimeUnit.SECONDS, new BitcoinWalletSaverListener());
        startSyncing();
    }

    private void startSyncing() throws BlockStoreException {
        blockStore = new SPVBlockStore(networkParameters, blockstore);
        final BlockChain chain = new BlockChain(networkParameters, wallet, blockStore);
        peerGroup = new PeerGroup(networkParameters, chain);
        peerGroup.addWallet(wallet);
        peerGroup.addPeerDiscovery(new DnsDiscovery(networkParameters));
        peerGroup.startAsync();
    }

    @Override
    public String getNetReceivingAddress() {
        return wallet.freshAddress(KeyChain.KeyPurpose.RECEIVE_FUNDS).toString();
    }


    @Override
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
            } catch (final ScriptException ex) {
                logger.error("Error while getting the transaction receiver address ", ex);
            }
        }
        return null;
    }

    @Override
    public long getBalance() {
        return wallet.getBalance().value;
    }

    @Override
    public String getFriendlyBalanceString() {
        return Coin.valueOf(getBalance()).toFriendlyString();
    }

    @Override
    public com.sendwyre.invoice.invoice.model.entity.Wallet getWallet() {
        return new com.sendwyre.invoice.invoice.model.entity.Wallet(COIN_SYMBOL, getFriendlyBalanceString(), getWalletTransctions());
    }

    @Override
    public String getCoinSymbol() {
        return COIN_SYMBOL;
    }

    @Override
    public void destroy() throws Exception {
        if (wallet != null) {
            wallet.saveToFile(walletFile);
            wallet.shutdownAutosaveAndWait();
        }
        if (peerGroup != null && peerGroup.isRunning()) {
            peerGroup.stop();
        }
        if (blockStore != null) {
            blockStore.close();
        }
    }
}
