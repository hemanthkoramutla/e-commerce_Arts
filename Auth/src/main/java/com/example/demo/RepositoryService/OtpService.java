package com.example.demo.RepositoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Entity.OtpEntity;
import com.example.demo.Repository.OtpRepository;

import org.apache.commons.lang3.RandomStringUtils;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OtpService {

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Transactional
    public String generateAndSendOtp(String email) {
        String otp = generateRandomOtp();
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(2);

        OtpEntity otpEntity = new OtpEntity();
        otpEntity.setEmail(email);
        otpEntity.setOtp(otp);
        otpEntity.setExpiryTime(expiryTime);

        otpRepository.save(otpEntity);

        // Send email with OTP
        sendOtpByEmail(email, otp);

        return otp;
    }

    public boolean validateOtp(String email, String enteredOtp) {
        Optional<OtpEntity> otpEntityOptional = otpRepository.findByEmail(email);

        if (otpEntityOptional.isPresent()) {
            OtpEntity otpEntity = otpEntityOptional.get();

            if (enteredOtp.equals(otpEntity.getOtp()) && LocalDateTime.now().isBefore(otpEntity.getExpiryTime())) {
                otpRepository.delete(otpEntity); // Remove the used OTP
                return true;
            }
        }

        return false;
    }

    @Transactional
    @Scheduled(fixedRate = 60000) // Run every minute to clean up expired OTPs
    public void cleanUpExpiredOtps() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        otpRepository.deleteByExpiryTimeBefore(currentDateTime);
    }

    private String generateRandomOtp() {
        return RandomStringUtils.randomNumeric(6);
    }

    private void sendOtpByEmail(String email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("hemanth.ecommerce@gmail.com"); // Replace with your Gmail email
        message.setTo(email);
        message.setSubject("Your OTP for verification");
        message.setText("Your OTP is: " + otp);

        mailSender.send(message);
    }
}
