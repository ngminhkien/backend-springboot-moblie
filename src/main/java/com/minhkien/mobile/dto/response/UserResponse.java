package com.minhkien.mobile.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {

    String hoTen;
    String gioiTinh;
    LocalDate ngaySinh;
    String email;
    String anhURL;
    //Set<RoleResponse > roles;
}
