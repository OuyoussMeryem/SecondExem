package ma.ensa.notificationservice.service;

import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    private static final String TOPIC = "command-created";
    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message) {
        kafkaTemplate.send(TOPIC, message);
    }
}
