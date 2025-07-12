package sba.project.tuvanluatgiaothong.service;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class OtpGeneratorUtil {

    private final Random random = new Random();

    public String generateOtp() {
        int otp = random.nextInt(900000) + 100000; // Generates 6-digit number
        return String.valueOf(otp);
    }
}
