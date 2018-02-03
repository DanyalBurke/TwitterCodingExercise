package com.mimecast.exercise.users;

import static com.mimecast.exercise.users.CompareLocalDateTime.describeTimeBetween;

import java.time.Clock;
import java.time.LocalDateTime;

public class MessageFormatter {
    private final Clock clock;

    public MessageFormatter(Clock clock) {
        this.clock = clock;
    }

    public String format(Message message) {
        return String.format("%s (%s)\n",
                message.message(),
                describeTimeBetween(message.postTime(), LocalDateTime.now(clock)));
    }

    public String formatWithUser(Message message) {
        return String.format("%s - %s",
                message.user(),
                format(message));
    }
}
