package com.minhkien.mobile.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder //tạo đối tượng từ bất kì field nào
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShowtimeRequest {

    String maPhim;
    Long maRap;
    Long maPhong;
    LocalDateTime tgBatDau;
}
