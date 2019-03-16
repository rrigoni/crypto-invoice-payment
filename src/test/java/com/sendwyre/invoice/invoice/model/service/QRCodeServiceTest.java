package com.sendwyre.invoice.invoice.model.service;

import com.google.zxing.WriterException;
import com.sendwyre.invoice.invoice.AbstractTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.image.BufferedImage;
import java.io.IOException;

import static org.junit.Assert.*;

public class QRCodeServiceTest extends AbstractTest {

    @Autowired
    private QRCodeService qrCodeService;
    @Autowired
    private WalletService walletService;

    @Test
    public void testGenerateQRCodeIsNotNull() throws IOException, WriterException {
        final String address = walletService.getNetReceivingAddress();
        final BufferedImage bufferedImage = qrCodeService.generateQRCodeImage(address);

        assertNotNull("QRCode image cannot be null", bufferedImage);
    }


    @Test
    public void testGenerateQRCodeProducesDifferntImagesFromDifferentAddress() throws IOException, WriterException {
        final String oldAddress = walletService.getNetReceivingAddress();
        final String newAddress = walletService.getNetReceivingAddress();
        final BufferedImage oldImage = qrCodeService.generateQRCodeImage(oldAddress);
        final BufferedImage newImage = qrCodeService.generateQRCodeImage(oldAddress);

        assertNotEquals("QRCode image cannot be null", oldImage, newImage);
    }

}