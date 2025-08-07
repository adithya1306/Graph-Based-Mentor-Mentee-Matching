package com.career.user_service.utility;

import com.career.user_service.dto.MailDTO;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;


    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void sendEmail(String to, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP code is: " + otp);

        try {
            mailSender.send(message);
        } catch (Exception e) {
            System.out.println("Failed to send email: " + e.getMessage());
        }
    }

    public void sendEmailToMentorAndMentee(MailDTO mailDTO) {

        String mentorMail = mailDTO.getMentorMail();
        String menteeMail = mailDTO.getMenteeMail();
        String mentorName = mailDTO.getMentorName();
        String menteeName = mailDTO.getMenteeName();
        String meetingDate = mailDTO.getMeetingDate();
        String meetingStartTime = mailDTO.getMeetingStartTime();
        String meetingEndTime = mailDTO.getMeetingEndTime();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mentorMail);
        message.setSubject("Mentorship Meeting Scheduled with " + menteeName);
        message.setText("Dear " + mentorName + ",\n\n" +
                        "You have been successfully matched with mentee " + menteeName + ".\n" +
                        "Mentee Email: " + menteeMail + "\n\n" +
                        "Best regards,\n" +
                        "Mentorship Team");

        SimpleMailMessage message1 = new SimpleMailMessage();
        message1.setTo(menteeMail);
        message1.setSubject("Mentorship Meeting Scheduled with " + mentorName);
        message1.setText("Dear " + menteeName + ",\n\n" +
                "You have been successfully matched with mentor " + mentorName + ".\n" +
                "Mentor Email: " + mentorMail + "\n\n" +
                "Meeting Date: " + meetingDate + "\n" +
                "Meeting Start Time: " + meetingStartTime + "\n" +
                "Meeting End Time: " + meetingEndTime + "\n\n" +
                "Best regards,\n" +
                "Mentorship Team");

        try {
            mailSender.send(message);
            mailSender.send(message1);
        } catch (Exception e) {
            System.out.println("Failed to send email: " + e.getMessage());
        }
    }
}
