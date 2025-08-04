package com.career.mentorship_matching_service.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserToGraphConsumer {

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "user-to-graph", groupId = "mentorship-group")
    public void consumeUserData(ConsumerRecord<String, String> userData) {
        String message = userData.value();
        String userId = userData.key();
        System.out.println("Received user data: " + message + " with key: " + userId);
    }
}
