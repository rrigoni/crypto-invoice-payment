package com.sendwyre.invoice.invoice.controller;

import com.sendwyre.invoice.invoice.model.service.QRCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
