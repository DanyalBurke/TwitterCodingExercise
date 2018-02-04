package com.mimecast.exercise.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.mimecast.exercise.users.MessageFactory;
import com.mimecast.exercise.users.MessageFormatter;
import com.mimecast.exercise.users.UserRepository;
import java.time.Clock;
import java.time.Instant;
import org.junit.Test;

public class ReadCommandTest {
    private final Clock clock = Clock.fixed(Instant.now(), Clock.systemUTC().getZone());
    private final UserRepository userRepository = new UserRepository(new MessageFactory(clock));
    private final ReadCommand readCommand = new ReadCommand(userRepository, new MessageFormatter(clock));

    @Test
    public void read_command_reads_and_formats_messages() {
        userRepository.get("Rita").post("Message1");
        userRepository.get("Rita").post("Message2");

        assertEquals(
                "Message1 (0 seconds ago)\n" +
                "Message2 (0 seconds ago)\n",
                readCommand.execute("Rita"));
    }

    @Test
    public void it_matches_read_commands() {
        assertTrue(readCommand.matches("Rita"));
        assertTrue(readCommand.matches("  Bob  "));

        assertFalse(readCommand.matches("Bob follows Sue"));
        assertFalse(readCommand.matches("Sue wall"));
        assertFalse(readCommand.matches("Sue -> M"));
    }

}