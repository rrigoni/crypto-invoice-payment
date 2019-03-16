package com.sendwyre.invoice.invoice.model.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.io.IOException;

@Component
public final class QRCodeService {

    @Value("${qrcode.height}")
    private Integer qrcodeHeight;

    @Value("${qrcode.width}")
    private Integer qrcodeWidth;

    public BufferedImage generateQRCodeImage(String address) throws WriterException, IOException {
        final QRCodeWriter qrCodeWriter = new QRCodeWriter();
        final BitMatrix bitMatrix = qrCodeWriter.encode(address, BarcodeFormat.QR_CODE, qrcodeWidth, qrcodeHeight);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }



}
