package com.career.mentorship_matching_service.kafka;

import com.career.mentorship_matching_service.dto.MailDTO;
import com.career.mentorship_matching_service.model.Mentor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MailToMentorProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendMailToMentor(MailDTO mailDTO) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(mailDTO);
        kafkaTemplate.send("mentor-mail-topic", json);
    }
}
