package com.career.user_service.kafka;

import com.career.user_service.dto.MailDTO;
import com.career.user_service.repo.UserRepo;
import com.career.user_service.utility.EmailService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MailToMentorConsumer {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepo repo;

    @Autowired
    private EmailService mailService;

    @KafkaListener(topics = "mentor-mail-topic", groupId = "mentorship-group")
    public void consumeMailtoMentor(ConsumerRecord<String, String> record) throws JsonProcessingException {

        // Deserialize the JSON string to MailDTO object
        MailDTO mailDTO;
        mailDTO = objectMapper.readValue(record.value(), MailDTO.class);

        // Send email to mentor and mentee
        mailService.sendEmailToMentorAndMentee(mailDTO);
    }
}
