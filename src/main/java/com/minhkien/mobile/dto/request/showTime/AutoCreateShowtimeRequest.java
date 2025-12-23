package com.minhkien.mobile.dto.request.showTime;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder //tạo đối tượng từ bất kì field nào
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AutoCreateShowtimeRequest {

    List<String> dsMaPhim;
    Long maRap;
    LocalDate ngayChieu;       // Ví dụ: 2025-12-24
    LocalTime gioMoCua;        // Ví dụ: 08:00:00
    LocalTime gioDongCua;      // Ví dụ: 23:00:00
    int thoiGianNghi;
}
