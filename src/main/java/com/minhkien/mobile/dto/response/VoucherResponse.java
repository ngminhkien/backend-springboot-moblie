package com.minhkien.mobile.dto.response;

import com.minhkien.mobile.enums.DiscountType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VoucherResponse {

    String maGiamGia;
    String moTa;
    Double giaTriGiam;
    DiscountType loaiGiamGia; // Trả về Enum hoặc String đều được
    LocalDateTime ngayTao;
    LocalDateTime ngayHetHan;
    Integer soLuong;
    Boolean trangThai;
}
