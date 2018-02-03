package com.mimecast.exercise.commands;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.mimecast.exercise.commands.CommandInterpreter;
import com.mimecast.exercise.users.MessageFactory;
import com.mimecast.exercise.users.UserRepository;
import java.time.Clock;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class CommandInterpreterTest {
    private Clock clock = mock(Clock.class);

    private CommandInterpreter interpreter = new CommandInterpreter(new UserRepository(new MessageFactory(clock)));

    @Before
    public void setupClock() {
        when(clock.getZone()).thenReturn(Clock.systemUTC().getZone());

        Instant start = Instant.parse("2018-02-01T12:00:01.00Z");
        when(clock.instant())
                .thenReturn(start)
                .thenReturn(start.plusSeconds(1))
                .thenReturn(start.plusSeconds(2))
                .thenReturn(start.plusSeconds(3))
                .thenReturn(start.plusSeconds(4))
                .thenReturn(start.plusSeconds(5));
    }

    @Test
    public void when_I_post_a_message_to_a_user_then_I_can_read_it_back() {
        assertEquals("", interpreter.command("Rita -> I love the weather today"));
        assertEquals("I love the weather today\n", interpreter.command("Rita"));

        assertEquals("", interpreter.command("Bob -> Damn! We lost!"));
        assertEquals("", interpreter.command("Bob -> Good game though."));
        assertEquals("Damn! We lost!\nGood game though.\n", interpreter.command("Bob"));
    }

    @Test
    public void my_wall_shows_my_posts() {
        assertEquals("",
                interpreter.command("Sue -> I'm in Bradford today! Anyone want to have a coffee?"));

        assertEquals("Sue - I'm in Bradford today! Anyone want to have a coffee?\n",
                interpreter.command("Sue wall"));
    }

    @Test
    public void when_I_follow_someone_then_their_posts_show_up_in_my_wall_ordered_chronologically() {

        assertEquals("", interpreter.command("Rita -> I love the weather today"));
        assertEquals("",
                interpreter.command("Sue -> I'm in Bradford today! Anyone want to have a coffee?"));
        assertEquals("", interpreter.command("Bob -> Damn! We lost!"));
        assertEquals("", interpreter.command("Bob -> Good game though."));

        assertEquals("", interpreter.command("Sue follows Rita"));
        assertEquals("", interpreter.command("Sue follows Bob"));

        assertEquals("Bob - Good game though.\n" +
                "Bob - Damn! We lost!\n" +
                "Sue - I'm in Bradford today! Anyone want to have a coffee?\n" +
                "Rita - I love the weather today\n",
                interpreter.command("Sue wall"));
    }

    // name_is_case_insensitive()

    // whitespace_is_not_considered_except_in_message()

    // mutliline support in a command?

    // what characters to support in a name?

    // what should it throw for an invalid command

    // follows twice

    // two people can follow each other
}
