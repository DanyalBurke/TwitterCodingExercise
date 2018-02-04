package com.mimecast.exercise.users;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;

public class MessageFormatterTest {
    private final Clock clock = mock(Clock.class);
    private final MessageFormatter messageFormatter = new MessageFormatter(clock);

    @Before
    public void setupClock() {
        when(clock.getZone()).thenReturn(Clock.systemUTC().getZone());

        Instant start = Instant.parse("2018-02-01T12:00:01.00Z");
        when(clock.instant())
                .thenReturn(start.plusSeconds(1))
                .thenReturn(start.plusSeconds(2))
                .thenReturn(start.plusSeconds(3))
                .thenReturn(start.plusSeconds(4))
                .thenReturn(start.plusSeconds(5));
    }

    @Test
    public void it_formats_a_message_and_how_long_ago_it_was() {
        Message message = new Message(LocalDateTime.now(clock), "Rita", "Message");
        assertEquals(
                "Message (1 second ago)\n",
                messageFormatter.format(message));
    }

    @Test

    public void it_formats_a_message_with_user_and_how_long_ago_it_was() {
        Message message = new Message(LocalDateTime.now(clock), "Rita", "Message");
        assertEquals(
                "Rita - Message (1 second ago)\n",
                messageFormatter.formatWithUser(message));
    }
}