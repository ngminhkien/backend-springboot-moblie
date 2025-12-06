package com.minhkien.mobile.configuration;

import com.minhkien.mobile.entity.User;
import com.minhkien.mobile.enums.Role;
import com.minhkien.mobile.responsitory.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository, PasswordEncoder passwordEncoder) {

        return args -> {
            // Kiểm tra xem user "admin" đã tồn tại chưa
            if (userRepository.findByEmail("admin@hotmail.com").isEmpty()) {

                // 1. Tạo set roles cho user, sử dụng trực tiếp Enum
                var roles = Set.of(Role.ADMIN); // Gán cả hai quyền nếu cần

                User user = User.builder()
                        .email("admin@hotmail.com")
                        .hoTen("admin")
                        // Mã hóa mật khẩu trước khi lưu
                        .matKhau(passwordEncoder.encode("admin"))
                        .roles(roles)
                        .build();

                userRepository.save(user);

                log.warn("admin user has been created with default password: admin, please change");
            }
        };
    }
}
