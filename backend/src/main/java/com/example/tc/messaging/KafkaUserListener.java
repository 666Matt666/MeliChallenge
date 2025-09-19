package com.example.tc.messaging;

//import com.example.tc.event.UserCreatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaUserListener {

    private final KafkaProducer kafkaProducer;

    public KafkaUserListener(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

   // @EventListener
    //public void handleUserCreatedEvent(UserCreatedEvent event) {
    //    kafkaProducer.sendUserCreated(event.getUser());
    //}
}