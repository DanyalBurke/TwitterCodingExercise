package com.mimecast.exercise.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.mimecast.exercise.users.MessageFactory;
import com.mimecast.exercise.users.MessageFormatter;
import com.mimecast.exercise.users.User;
import com.mimecast.exercise.users.UserRepository;
import java.time.Clock;
import java.time.Instant;
import org.junit.Test;

public class WallCommandTest {
    private final Clock clock = Clock.fixed(Instant.now(), Clock.systemUTC().getZone());
    private final UserRepository userRepository = new UserRepository(new MessageFactory(clock));
    private final WallCommand wallCommand = new WallCommand(userRepository, new MessageFormatter(clock));


    @Test
    public void read_command_reads_and_formats_messages() {
        User rita = userRepository.get("Rita");
        User sue = userRepository.get("Sue");
        rita.follow(sue);
        rita.post("Message1");
        sue.post("Message2");
        sue.post("Message3");

        assertEquals(
                "Rita - Message1 (0 seconds ago)\n" +
                "Sue - Message2 (0 seconds ago)\n" +
                "Sue - Message3 (0 seconds ago)\n",
                wallCommand.execute("Rita wall"));
    }

    @Test
    public void it_matches_wall_commands() {
        assertTrue(wallCommand.matches("Rita wall"));
        assertTrue(wallCommand.matches("  Bob  wall  "));

        assertFalse(wallCommand.matches("%Rita% wall"));
        assertFalse(wallCommand.matches("Bob follows Sue"));
        assertFalse(wallCommand.matches("Sue"));
        assertFalse(wallCommand.matches("Sue -> M"));
    }


}
