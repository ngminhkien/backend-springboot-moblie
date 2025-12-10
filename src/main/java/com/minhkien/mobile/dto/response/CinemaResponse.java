package com.minhkien.mobile.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CinemaResponse {

    Long maRap;
    String tenRap;
    String diaDiem;
}
