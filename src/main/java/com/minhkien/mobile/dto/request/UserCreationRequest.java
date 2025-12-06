package com.minhkien.mobile.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder //tạo đối tượng từ bất kì field nào
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {

    String hoTen;
    String gioiTinh;
    LocalDate ngaySinh;
    String email;
    String matKhau;

    //Set<String> maGenres;
}
