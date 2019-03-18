package com.sendwyre.invoice.invoice.controller;

import com.sendwyre.invoice.invoice.model.entity.Transaction;
import com.sendwyre.invoice.invoice.model.entity.Wallet;
import com.sendwyre.invoice.invoice.model.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public final class WalletController {

    @Autowired
    private WalletService walletService;

    @GetMapping("/wallet") @ResponseBody final Wallet all() {
        return walletService.getWallet();
    }


}
