package com.mimecast.exercise.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import com.mimecast.exercise.users.MessageFactory;
import com.mimecast.exercise.users.UserRepository;
import org.junit.Test;

public class FollowsCommandTest {
    private final UserRepository userRepository = new UserRepository(mock(MessageFactory.class));
    private final FollowsCommand followsCommand = new FollowsCommand(userRepository);

    @Test
    public void follows_command_has_empty_output() {
        assertEquals("", followsCommand.execute("Sue follows Rita"));
    }

    @Test
    public void follows_command_causes_one_user_to_follow_another() {
        followsCommand.execute("Sue follows Rita");

        assertTrue(userRepository.get("Sue").isFollowing(userRepository.get("Rita")));
        assertFalse(userRepository.get("Rita").isFollowing(userRepository.get("Sue")));
    }

    @Test
    public void it_matches_follow_commands() {
        assertTrue(followsCommand.matches("Bob follows Rita"));
        assertTrue(followsCommand.matches("   Sue   follows   Rita   "));

        assertFalse(followsCommand.matches("Rita"));
        assertFalse(followsCommand.matches("Rita -> m"));
        assertFalse(followsCommand.matches("Rita follows"));
        assertFalse(followsCommand.matches("Rita follows Tim Brown"));
        assertFalse(followsCommand.matches("Rita says hello"));
    }
}