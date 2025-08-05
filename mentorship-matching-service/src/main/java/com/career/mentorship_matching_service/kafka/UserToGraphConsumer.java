package com.career.mentorship_matching_service.kafka;

import com.career.mentorship_matching_service.model.User;
import com.career.mentorship_matching_service.service.command.UserCommandService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserToGraphConsumer {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserCommandService userCommandService;

    @KafkaListener(topics = "user-to-graph", groupId = "mentorship-group")
    public void consumeUserData(ConsumerRecord<String, String> userData) throws JsonProcessingException {
        User newUser = objectMapper.readValue(userData.value(), User.class);
        String userId = userData.key();

        if (newUser.getFirstName() != null) {
            userCommandService.saveOrUpdateUser(newUser);
        }
    }
}
