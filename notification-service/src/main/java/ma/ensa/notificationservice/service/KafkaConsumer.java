package ma.ensa.notificationservice.service;

import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = "command-created", groupId = "notification-group")
    public void listen(String message) {
        System.out.println("Received message: " + message);

        // Logique pour traiter la notification (exemple : envoyer un email ou afficher une notification)
        sendNotificationToUser(message);
    }

    private void sendNotificationToUser(String message) {
        // Implémentation pour envoyer la notification (par ex. via email, websocket, etc.)
        System.out.println("Notification sent to user: " + message);
    }
}
