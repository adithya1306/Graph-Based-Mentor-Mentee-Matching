package com.career.user_service.service;

import com.career.user_service.kafka.UserToGraphProducer;
import com.career.user_service.model.LoginReq;
import com.career.user_service.model.LoginResp;
import com.career.user_service.model.User;
import com.career.user_service.model.VerifyReq;
import com.career.user_service.repo.UserRepo;
import com.career.user_service.utility.EmailService;
import com.career.user_service.utility.JWTUtility;
import com.career.user_service.utility.OTPService;
import com.netflix.discovery.converters.Auto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    @Autowired
    private OTPService otpService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepo repo;

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private UserToGraphProducer userToGraphProducer;

    public Object createUser(User userRequest) {
        // Check if user already exists
        if (repo.findByEmail(userRequest.getEmail()) != null) {
            return new ResponseEntity<>("User already exists", org.springframework.http.HttpStatus.CONFLICT);
        }

        User user = User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .email(userRequest.getEmail())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .phoneNumber(userRequest.getPhoneNumber())
                .role(userRequest.getRole())
                .skills(userRequest.getSkills())
                .industry(userRequest.getIndustry())
                .experienceLevel(userRequest.getExperienceLevel())
                .availability(userRequest.getAvailability())
                .build();

//        String otp = otpService.generateOTP(user.getEmail());
//        emailService.sendEmail(user.getEmail(), otp);
        userToGraphProducer.sendUserToGraph(user); // Send user data to Graph service
        return repo.save(user);
    }


    public Object loginUser(LoginReq userRequest) {
        User user = repo.findByEmail(userRequest.getEmail());

        if(user == null) {
            throw new RuntimeException("User not found");
        }

        if (passwordEncoder.matches(userRequest.getPassword(), user.getPassword())) {
            String jwtToken = jwtUtility.generateToken(user);
            LoginResp response = new LoginResp();
            response.setToken(jwtToken);
            response.setFirstName(user.getFirstName());
            response.setRole(user.getRole());
            return response;
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }

    public ResponseEntity<?> verifyUser(VerifyReq verifyRequest) {
        String email = verifyRequest.getEmail();
        String otp = verifyRequest.getOtp();

        System.out.println("Verifying user with email: " + email + " and OTP: " + otp);

        boolean isValid = otpService.validateOTP(email, otp);

        if(isValid) {
            User user = repo.findByEmail(email);
            if (user != null) {
                user.setVerified(true);
                repo.save(user);
                return new ResponseEntity<>("User verified successfully", org.springframework.http.HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User not found", org.springframework.http.HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("Invalid OTP", org.springframework.http.HttpStatus.UNAUTHORIZED);
        }
    }
}
