package org.acme.user.kafka;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
public class UsersEventsProducer {

    @Channel("user-events-out")
    Emitter<String> emitter;

    public void sendUserCreatedEvent(long userId,String name, String email) {
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
}
