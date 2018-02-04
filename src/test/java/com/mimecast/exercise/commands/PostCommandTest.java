package com.mimecast.exercise.commands;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.mimecast.exercise.users.Message;
import com.mimecast.exercise.users.MessageFactory;
import com.mimecast.exercise.users.UserRepository;
import java.time.Clock;
import java.util.stream.Collectors;
import org.junit.Test;

public class PostCommandTest {
    private final UserRepository userRepository = new UserRepository(new MessageFactory(Clock.systemUTC()));
    private final PostCommand postCommand = new PostCommand(userRepository);

    @Test
    public void post_command_has_empty_output() {
        assertEquals(
                "",
                postCommand.execute("Rita -> I love the weather today"));
    }

    @Test
    public void post_command_posts_message() {
        postCommand.execute("Rita -> I love the weather today");

        assertEquals(
                asList("I love the weather today"),
                userRepository.get("Rita").messages()
                        .stream()
                        .map(Message::message)
                        .collect(Collectors.toList()));
    }

    @Test
    public void it_matches_post_commands() {
        assertTrue(postCommand.matches("Bob -> Good game though."));
        assertTrue(postCommand.matches("   Rita   ->   I love the weather today"));

        assertFalse(postCommand.matches("->"));
        assertFalse(postCommand.matches("Bob"));
        assertFalse(postCommand.matches("Bob follows Sue"));
        assertFalse(postCommand.matches("Bob wall"));
    }
}