package com.career.user_service.kafka;

import com.career.user_service.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserToGraphProducer {
    private static final String TOPIC = "user-to-graph";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendUserToGraph(User user) {
        try {
            String json = objectMapper.writeValueAsString(user);
            kafkaTemplate.send(TOPIC, user.getRole(), json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
