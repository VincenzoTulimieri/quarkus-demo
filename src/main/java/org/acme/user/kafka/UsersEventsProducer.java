package org.acme.user.kafka;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
public class UsersEventsProducer {

    @Channel("user-events-out")
    Emitter<String> emitter;

    public void sendUserCreatedEvent(Long userId,String name, String email) {
        String message = """
        {
          "eventType": "USER_CREATED",
          "userId": %d,
          "name": "%s",
          "email": "%s"
        }
        """.formatted(userId, name, email);
        emitter.send(message);
    }

    public void sendUserUpdatedEvent(Long userId,String name, String email) {
        String message = """
        {
          "eventType": "USER_UPDATED",
          "userId": %d,
          "name": "%s",
          "email": "%s"
        }
        """.formatted(userId,name,email);
        emitter.send(message);
    }

    public void sendUserDeletedEvent(Long userId) {
        String message = """
        {
          "eventType": "USER_DELETED",
          "userId": %d
        }
        """.formatted(userId);
        emitter.send(message);
    }
}
