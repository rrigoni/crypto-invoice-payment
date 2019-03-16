package com.sendwyre.invoice.invoice.model.service;

import com.sendwyre.invoice.invoice.AbstractTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

public class WalletServiceTest extends AbstractTest {

    @Autowired
    private WalletService walletService;

    @Test
    public void testAddressNotNull() {
        assertNotNull("Address should not be null", walletService.getNetReceivingAddress());
    }


    @Test
    public void testAddressRotates() {
        final String oldAddress = walletService.getNetReceivingAddress();
        final String newAddress = walletService.getNetReceivingAddress();
        assertNotEquals("New address must be different", oldAddress, newAddress);
    }
}