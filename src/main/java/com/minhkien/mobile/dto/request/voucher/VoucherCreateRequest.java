package com.minhkien.mobile.dto.request.voucher;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder //tạo đối tượng từ bất kì field nào
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VoucherCreateRequest {

    String maGiamGia; // User tự nhập mã, ví dụ: "SALE50"
    String moTa;
    Double giaTriGiam;
    String loaiGiamGia; // Client gửi String "PERCENTAGE" hoặc "AMOUNT"
    LocalDateTime ngayHetHan;
    Integer soLuong;
}
