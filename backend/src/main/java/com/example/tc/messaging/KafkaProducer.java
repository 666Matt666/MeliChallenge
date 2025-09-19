package com.example.tc.messaging;

import com.example.tc.dto.UserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String topic;

    public KafkaProducer(KafkaTemplate<String, Object> kafkaTemplate, @Value("${topic.user.created}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    public void sendUserCreated(UserDTO user) {
        kafkaTemplate.send(topic, user.getId(), user);
    }
}