package com.minhkien.mobile.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder //tạo đối tượng từ bất kì field nào
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SeatConfigResponse {

    String maSeatType;
    String tenSeatType;
    Double gia;
}
