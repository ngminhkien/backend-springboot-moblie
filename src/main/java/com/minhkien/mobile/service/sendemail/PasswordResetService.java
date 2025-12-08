package com.minhkien.mobile.service.sendemail;

import com.minhkien.mobile.entity.PasswordResetToken;
import com.minhkien.mobile.responsitory.PasswordResetTokenRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PasswordResetService {

    PasswordResetTokenRepository tokenRepo;

    public String generateOtpAndSave(String email) {
        String otp = String.format("%06d", new Random().nextInt(1_000_000));
        PasswordResetToken token = tokenRepo.findByEmail(email).orElse(new PasswordResetToken());

        token.setEmail(email);
        token.setOtp(otp);
        token.setExpiresAt(LocalDateTime.now().plusMinutes(5));

        tokenRepo.save(token);
        return otp;
    }

    public boolean verifyOtp(String email, String otp) {
        return tokenRepo.findByEmail(email)
                .filter(t -> t.getOtp().equals(otp))
                .filter(t -> t.getExpiresAt().isAfter(LocalDateTime.now()))
                .isPresent();
    }

    @Transactional
    public void removeOtp(String email) {
        tokenRepo.deleteByEmail(email);
    }
}
