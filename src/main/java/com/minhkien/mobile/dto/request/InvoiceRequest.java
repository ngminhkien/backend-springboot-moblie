package com.minhkien.mobile.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder //tạo đối tượng từ bất kì field nào
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvoiceRequest {

    String maUser;
    Long maSuatChieu;
    String voucherId; // maGiamGia
    List<SeatRequest> gheList;
    List<FoodRequest> doAnList;

    @Data
    public static class SeatRequest {
        private String maSeatType; // id loại ghế
    }

    @Data
    public static class FoodRequest {
        private String foodId;
        private Integer soLuong;
    }
}
