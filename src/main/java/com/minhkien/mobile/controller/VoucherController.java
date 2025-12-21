package com.minhkien.mobile.controller;

import com.minhkien.mobile.dto.request.voucher.VoucherCreateRequest;
import com.minhkien.mobile.dto.request.voucher.VoucherUpdateRequest;
import com.minhkien.mobile.dto.response.VoucherResponse;
import com.minhkien.mobile.service.VoucherService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/vouchers")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class VoucherController {

    VoucherService voucherService;

    @GetMapping
    public ResponseEntity<List<VoucherResponse>> getAll() {
        return ResponseEntity.ok(voucherService.getAllVouchers());
    }

    @PostMapping
    // @Valid
    public ResponseEntity<VoucherResponse> create(@RequestBody VoucherCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(voucherService.createVoucher(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VoucherResponse> update(@PathVariable("id") String id,
                                                  @RequestBody VoucherUpdateRequest request) {
        return ResponseEntity.ok(voucherService.updateVoucher(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        voucherService.deleteVoucher(id);
        return ResponseEntity.noContent().build();
    }
}
