package com.mimecast.exercise.users;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

public class MessageTest {
    @Test
    public void when_sorting_messages_a_newer_message_comes_before_an_older_one() {
        Message m1 = new Message(LocalDateTime.parse("2018-02-01T12:00:00"), "U1", "M1");
        Message m2 = new Message(LocalDateTime.parse("2018-02-01T15:00:00"), "U2", "M2");
        Message m3 = new Message(LocalDateTime.parse("2018-02-28T12:00:00"), "U3", "M3");

        List<Message> messages = new ArrayList<>(asList(m1, m3, m2));

        Collections.sort(messages);

        assertEquals(asList(m3, m2, m1), messages);
    }
}