package com.career.user_service.utility;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class OTPService {
    private final RedisTemplate<String, String> redisTemplate;

    private static final String OTP_PREFIX = "otp:";
    private static final long OTP_EXPIRATION_TIME = 60; // 1 minutes in seconds

    public String generateOTP(String email) {
        String otp = String.valueOf((int) (Math.random() * 9000) + 1000); // Generate a 4-digit OTP

        redisTemplate.opsForValue().set(
                OTP_PREFIX + email,
                otp,
                Duration.ofSeconds(OTP_EXPIRATION_TIME)
        ); // Store OTP in Redis with expiration time (known as TTL)

        return otp;
    }

    public boolean validateOTP(String email, String otp) {
        String storedOtp = redisTemplate.opsForValue().get(OTP_PREFIX + email); // Retrieve OTP from Redis
        //String storedOtp = storedOtpFromRedis.substring(storedOtpFromRedis.length()-4, storedOtpFromRedis.length()); // Extract the last 4 characters as OTP

        if (storedOtp != null && storedOtp.equals(otp)) {
            redisTemplate.delete(OTP_PREFIX + email); // Delete OTP after successful validation
            return true;
        }
        return false;
    }

    public OTPService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
