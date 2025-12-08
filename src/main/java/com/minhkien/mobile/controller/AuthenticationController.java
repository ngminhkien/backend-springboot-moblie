package com.minhkien.mobile.controller;

import com.minhkien.mobile.dto.request.AuthenticationRequest;
import com.minhkien.mobile.dto.request.IntrospectRequest;
import com.minhkien.mobile.dto.response.AuthenticationResponse;
import com.minhkien.mobile.dto.response.IntrospectResponse;
import com.minhkien.mobile.entity.User;
import com.minhkien.mobile.responsitory.UserRepository;
import com.minhkien.mobile.service.AuthenticationService;
import com.minhkien.mobile.service.sendemail.EmailService;
import com.minhkien.mobile.service.sendemail.PasswordResetService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordResetService passwordResetService;
    private final EmailService emailService;

    @Value("${app.mail.from}")
    String mailFrom;

    @PostMapping("/token")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest request) {
        return authenticationService.authenticate(request);
    }

    @PostMapping("/introspect")
    public IntrospectResponse introspect(@RequestBody IntrospectRequest request) throws Exception {
        return authenticationService.introspect(request);
    }

    // 1) Request OTP: backend tạo OTP và GỬI EMAIL luôn
    @PostMapping("/request-otp")
    public String requestOtp(@RequestParam String email) {
        // check user exists
        if (!userRepository.existsByEmail(email)) {
            return "EMAIL_NOT_FOUND";
        }

        String otp = passwordResetService.generateOtpAndSave(email);

        String subject = "Your OTP Code";
        String body = "Hello,\n\nYour OTP code is: " + otp + "\nIt will expire in 5 minutes.\n\nIf you didn't request this, ignore this email.";

        // send email
        emailService.sendSimpleMail(email, subject, body);

        // returning simple message (NOT returning OTP to client)
        return "OTP_SENT";
    }

    // 2) Verify OTP
    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam String email, @RequestParam String otp) {
        boolean ok = passwordResetService.verifyOtp(email, otp);
        return ok ? "VALID" : "INVALID";
    }

    // 3) Reset password
    @PostMapping("/reset-password")
    public String resetPassword(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String otp = body.get("otp");
        String newPassword = body.get("newPassword");

        if (email == null || otp == null || newPassword == null) {
            return "BAD_REQUEST";
        }

        if (!passwordResetService.verifyOtp(email, otp)) {
            return "INVALID_OTP";
        }

        Optional<User> optUser = userRepository.findByEmail(email);
        if (optUser.isEmpty()) {
            return "EMAIL_NOT_FOUND";
        }

        User user = optUser.get();
        user.setMatKhau(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        // remove OTP so it can't be reused
        passwordResetService.removeOtp(email);

        return "SUCCESS";
    }

}
