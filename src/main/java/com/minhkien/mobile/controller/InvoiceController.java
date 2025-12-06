package com.minhkien.mobile.controller;

import com.minhkien.mobile.dto.request.InvoiceRequest;
import com.minhkien.mobile.dto.response.InvoiceResponse;
import com.minhkien.mobile.service.InvoiceService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/invoices")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class InvoiceController {

    InvoiceService invoiceService;

    @PostMapping()
    public ResponseEntity<InvoiceResponse> create(@RequestBody InvoiceRequest req) {
        return ResponseEntity.ok(invoiceService.createInvoice(req));
    }
}
