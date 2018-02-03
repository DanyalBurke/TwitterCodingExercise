package com.mimecast.exercise.users;

import java.time.Clock;
import java.time.LocalDateTime;

public class MessageFactory {
    private final Clock clock;

    public MessageFactory(Clock clock) {
        this.clock = clock;
    }

    public Message create(String user, String message) {
        return new Message(LocalDateTime.now(clock), user, message);
    }
}
