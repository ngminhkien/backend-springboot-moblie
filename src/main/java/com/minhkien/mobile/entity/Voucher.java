package com.minhkien.mobile.entity;

import com.minhkien.mobile.enums.DiscountType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Table(name = "vouchers")
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Voucher {

    @Id
    String maGiamGia;
    String moTa;
    Double giaTriGiam;
    @Enumerated(EnumType.STRING)
    DiscountType loaiGiamGia; // PERCENTAGE hoặc AMOUNT
    LocalDateTime ngayTao;
    LocalDateTime ngayHetHan;
    Integer soLuong;
    Boolean trangThai;
}
