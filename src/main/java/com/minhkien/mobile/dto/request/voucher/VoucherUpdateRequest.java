package com.minhkien.mobile.dto.request.voucher;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder //tạo đối tượng từ bất kì field nào
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VoucherUpdateRequest {

    String moTa;
    Double giaTriGiam;
    String loaiGiamGia;
    LocalDateTime ngayHetHan;
    Integer soLuong;
    Boolean trangThai;
}
