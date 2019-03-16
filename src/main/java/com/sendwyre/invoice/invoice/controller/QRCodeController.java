package com.sendwyre.invoice.invoice.controller;

import com.sendwyre.invoice.invoice.model.entity.Invoice;
import com.sendwyre.invoice.invoice.model.service.InvoiceService;
import com.sendwyre.invoice.invoice.model.service.QRCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

@RestController
public final class QRCodeController {

    @Autowired
    private QRCodeService  qrCodeService;

    @RequestMapping(value = "/qrcode/{address}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable String address) {
        final byte[] bytes = qrCodeService.generateQRCodeByteArray(address);
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(bytes);
    }


}
