package com.minhkien.mobile.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder //tạo đối tượng từ bất kì field nào
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShowtimeResponse {

    Long id;
    String maPhim;
    String tenPhim;
    Long maRap;
//    String tenRap;
//    String diaDiem;
    String tenPhong;
    LocalDateTime tgBatDau;
    LocalDateTime tgKetThuc;
}
