package com.career.mentorship_matching_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDateTime;

@SpringBootApplication
@EnableScheduling
@EnableAspectJAutoProxy
public class MentorshipMatchingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MentorshipMatchingServiceApplication.class, args);
		LocalDateTime currentDateTime = LocalDateTime.now();
		System.out.println(currentDateTime);
	}

}
