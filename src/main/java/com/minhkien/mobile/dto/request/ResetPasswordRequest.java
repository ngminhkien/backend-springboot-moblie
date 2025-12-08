package com.minhkien.mobile.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder //tạo đối tượng từ bất kì field nào
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResetPasswordRequest {

    String email;
    String otp;
    String newPassword;
}
