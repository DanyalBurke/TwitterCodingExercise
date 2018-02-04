package com.mimecast.exercise.users;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.Clock;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.Test;

public class UserTest {
    private final Clock clock = mock(Clock.class);
    private final MessageFactory messageFactory = new MessageFactory(clock);


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
    public void two_users_are_equal_if_they_have_the_same_name() {
        User rita1 = new User(messageFactory, "Rita");
        User rita2 = new User(messageFactory, "Rita");
        User bob = new User(messageFactory, "Bob");

        rita2.post("hello");
        rita2.follow(bob);

        assertTrue(rita1.equals(rita2));
        assertEquals(rita1.hashCode(), rita2.hashCode());

        assertFalse(rita1.equals(bob));
    }

    @Test
    public void a_user_can_post_messages_to_their_message_feed() {
        User rita = new User(messageFactory, "Rita");
        rita.post("Message1");
        rita.post("Message2");

        Set<String> messages = rita.messages()
                .stream()
                .map(Message::message)
                .collect(Collectors.toSet());

        assertEquals(
                new HashSet<>(asList("Message1", "Message2")),
                messages);
    }

    @Test
    public void a_users_posts_also_appear_on_their_wall() {
        User rita = new User(messageFactory, "Rita");
        rita.post("Message1");
        rita.post("Message2");

        Set<String> wallMessages = rita.wallMessages()
                .stream()
                .map(Message::message)
                .collect(Collectors.toSet());

        assertEquals(
                new HashSet<>(asList("Message1", "Message2")),
                wallMessages);
        assertTrue(wallMessages.contains("Message1"));
        assertTrue(wallMessages.contains("Message2"));
    }

    @Test
    public void can_test_if_a_user_is_following_someone() {
        User john = new User(messageFactory, "John");
        User rita = new User(messageFactory, "Rita");

        john.follow(rita);
        assertTrue(john.isFollowing(rita));
    }

    @Test
    public void when_a_user_follows_someone_they_see_that_someones_posts_on_their_wall() {
        User john = new User(messageFactory, "John");
        User rita = new User(messageFactory, "Rita");

        john.follow(rita);

        rita.post("Message1");
        john.post("Message2");

        Set<String> wallMessages = john.wallMessages()
                .stream()
                .map(Message::message)
                .collect(Collectors.toSet());

        assertEquals(
                new HashSet<>(asList("Message1", "Message2")),
                wallMessages);
    }

    @Test
    public void messages_are_newest_first() {
        User rita = new User(messageFactory, "Rita");
        rita.post("Message1");
        rita.post("Message2");
        rita.post("Message3");

        List<String> messages = rita.messages()
                .stream()
                .map(Message::message)
                .collect(Collectors.toList());

        assertEquals(
                asList("Message3", "Message2", "Message1"),
                messages
        );
    }

    @Test
    public void wall_messages_are_newest_first() {
        User john = new User(messageFactory, "John");
        User rita = new User(messageFactory, "Rita");
        User bob = new User(messageFactory, "Bob");

        john.follow(rita);
        john.follow(bob);

        rita.post("Message1");
        john.post("Message2");
        bob.post("Message3");

        List<String> wallMessages = john.wallMessages()
                .stream()
                .map(Message::message)
                .collect(Collectors.toList());

        assertEquals(
                asList("Message3", "Message2", "Message1"),
                wallMessages
        );
    }
}