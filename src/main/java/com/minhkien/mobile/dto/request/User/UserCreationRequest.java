package com.minhkien.mobile.dto.request.User;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

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
