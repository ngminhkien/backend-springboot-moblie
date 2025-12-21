package com.minhkien.mobile.dto.request.User;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder //tạo đối tượng từ bất kì field nào
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {

    String hoTen;
    String gioiTinh;
    LocalDate ngaySinh;
    String anhURL;
    Set<String> maGenres;
}
