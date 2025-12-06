package com.minhkien.mobile.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // 1. Thiết lập mã trạng thái HTTP (401 Unauthorized)
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // 2. Thiết lập loại nội dung là JSON
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        // 3. Định nghĩa trực tiếp thông tin lỗi
        // (Bỏ qua lớp ErrorCode và ApiResponse)
        String errorCode = "UNAUTHENTICATED";
        String errorMessage = "Bạn chưa đăng nhập hoặc token không hợp lệ.";

        // 4. Tạo đối tượng JSON thủ công
        String jsonResponse = String.format(
                "{\"code\": \"%s\", \"message\": \"%s\"}",
                errorCode,
                errorMessage
        );

        // 5. Ghi phản hồi về client
        response.getWriter().write(jsonResponse);
        response.getWriter().flush();
    }
}
