package com.minhkien.mobile.service.sendemail;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class EmailService {

    JavaMailSender mailSender;

    public void sendSimpleMail(String to, String subject, String text) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(to);
        msg.setSubject(subject);
        msg.setText(text);
        mailSender.send(msg);
    }

//    @Value("${app.mail.from}")
//    private String mailFrom;
//
//    public void sendOtp(String to, String otp) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom(mailFrom);
//        message.setTo(to);
//        message.setSubject("Your OTP Code");
//        message.setText("OTP: " + otp);
//        mailSender.send(message);
//    }
}
