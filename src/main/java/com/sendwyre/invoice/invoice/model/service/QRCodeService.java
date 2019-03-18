package com.sendwyre.invoice.invoice.model.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
public final class QRCodeService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${qrcode.height}")
    private Integer qrcodeHeight;

    @Value("${qrcode.width}")
    private Integer qrcodeWidth;


    public byte[] generateQRCodeByteArray(String address) {
        final BufferedImage image = generateQRCodeImage(address);

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", baos );
            baos.flush();
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public BufferedImage generateQRCodeImage(String address) {
        final QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            final BitMatrix bitMatrix = qrCodeWriter.encode(address, BarcodeFormat.QR_CODE, qrcodeWidth, qrcodeHeight);
            return MatrixToImageWriter.toBufferedImage(bitMatrix);
        } catch (WriterException e) {
            logger.error("Error while generating QRCode", e);
        }
        return null;
    }



}
