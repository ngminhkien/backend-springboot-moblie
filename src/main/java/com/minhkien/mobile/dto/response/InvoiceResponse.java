package com.minhkien.mobile.dto.response;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder //tạo đối tượng từ bất kì field nào
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvoiceResponse {

    Long maHoaDon;
    String userName;
    String tenPhim;
    Double tongTienTruocGiam;
    Double soTienGiam;
    Double tongTienSauGiam;
    String voucher;
    LocalDateTime ngayTao;
    LocalDateTime tgBatDau;
    String tenRap;
    String diaChiRap;
    String tenPhong;
    String url;
    List<GheResponse> gheList;
    List<DoAnResponse> doAnList;

    @Data
    @Builder
    public static class GheResponse {
        private String maSeatType;
        private String tenLoaiGhe;
        private Double gia;
    }

    @Data
    @Builder
    public static class DoAnResponse {
        private String foodId;
        private String tenDoAn;
        private Integer soLuong;
        private Double gia;
        private Double thanhTien;
    }
}
