package com.mimecast.exercise.commands;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.mimecast.exercise.users.MessageFactory;
import com.mimecast.exercise.users.MessageFormatter;
import com.mimecast.exercise.users.UserRepository;
import java.time.Clock;
import java.time.Instant;
import org.junit.Before;
import org.junit.Test;

public class CommandInterpreterTest {
    private final Clock clock = mock(Clock.class);

    private final CommandInterpreter interpreter =
            new CommandInterpreter(
                    new UserRepository(new MessageFactory(clock)),
                    new MessageFormatter(clock)
            );

    private Instant now = Instant.parse("2018-02-01T12:00:01.00Z");

    @Before
    public void setupClock() {
        when(clock.getZone()).thenReturn(Clock.systemUTC().getZone());

        Instant start = Instant.parse("2018-02-01T12:00:01.00Z");
        when(clock.instant()).thenAnswer((invocation) -> now);
    }

    private void tickOneSecond() {
        now = now.plusSeconds(1);
    }

    @Test
    public void when_I_post_a_message_to_a_user_then_I_can_read_it_back_newest_first() {
        assertEquals("", interpreter.command("Rita -> I love the weather today"));
        tickOneSecond();

        assertEquals("I love the weather today (1 second ago)\n", interpreter.command("Rita"));

        assertEquals("", interpreter.command("Bob -> Damn! We lost!"));
        tickOneSecond();

        assertEquals("", interpreter.command("Bob -> Good game though."));
        tickOneSecond();

        assertEquals(
                "Good game though. (1 second ago)\n" +
                "Damn! We lost! (2 seconds ago)\n",
                interpreter.command("Bob"));
    }

    @Test
    public void my_wall_shows_my_posts_newest_first() {
        assertEquals("",
                interpreter.command("Bob -> Damn! We lost!"));
        tickOneSecond();

        assertEquals("",
                interpreter.command("Bob -> Good game though."));
        tickOneSecond();

        assertEquals(
                "Bob - Good game though. (1 second ago)\n" +
                 "Bob - Damn! We lost! (2 seconds ago)\n" ,
                interpreter.command("Bob wall"));
    }

    @Test
    public void when_I_follow_someone_then_their_posts_show_up_in_my_wall_newest_first() {
        assertEquals("", interpreter.command("Rita -> I love the weather today"));
        tickOneSecond();

        assertEquals("",
                interpreter.command("Sue -> I'm in Bradford today! Anyone want to have a coffee?"));
        tickOneSecond();

        assertEquals("", interpreter.command("Bob -> Damn! We lost!"));
        tickOneSecond();

        assertEquals("", interpreter.command("Bob -> Good game though."));
        tickOneSecond();

        assertEquals("", interpreter.command("Sue follows Rita"));
        assertEquals("", interpreter.command("Sue follows Bob"));

        assertEquals("Bob - Good game though. (1 second ago)\n" +
                "Bob - Damn! We lost! (2 seconds ago)\n" +
                "Sue - I'm in Bradford today! Anyone want to have a coffee? (3 seconds ago)\n" +
                "Rita - I love the weather today (4 seconds ago)\n",
                interpreter.command("Sue wall"));
    }

    @Test(expected = InvalidCommandException.class)
    public void an_invalid_command_throws_InvalidCommandException() {
        interpreter.command("->");
    }

    @Test
    public void names_are_case_insensitive_when_referred_to() {
        assertEquals("",
                interpreter.command("Rita -> I love the weather today"));
        tickOneSecond();

        assertEquals(
                "I love the weather today (1 second ago)\n",
                interpreter.command("RITA"));
    }

    @Test
    public void a_user_can_follow_another_user_twice_and_the_result_is_the_same() {
        assertEquals("", interpreter.command("Sue follows Rita"));
        assertEquals("", interpreter.command("Sue follows Rita"));

        assertEquals("", interpreter.command("Rita -> I love the weather today"));
        tickOneSecond();

        assertEquals("Rita - I love the weather today (1 second ago)\n", interpreter.command("Sue wall"));
    }

    @Test
    public void two_user_can_follow_each_other() {
        assertEquals("", interpreter.command("Sue follows Rita"));
        assertEquals("", interpreter.command("Rita follows Sue"));

        assertEquals("", interpreter.command("Rita -> I love the weather today"));
        tickOneSecond();

        assertEquals("", interpreter.command("Sue -> I'm in Bradford today! Anyone want to have a coffee?"));
        tickOneSecond();

        assertEquals(
                "Sue - I'm in Bradford today! Anyone want to have a coffee? (1 second ago)\n" +
                "Rita - I love the weather today (2 seconds ago)\n",
                interpreter.command("Sue wall"));

        assertEquals(
                "Sue - I'm in Bradford today! Anyone want to have a coffee? (1 second ago)\n" +
                        "Rita - I love the weather today (2 seconds ago)\n",
                interpreter.command("Rita wall"));
    }

    @Test
    public void a_user_following_his_or_herself_is_a_no_op() {
        assertEquals("", interpreter.command("Rita -> I love the weather today"));
        tickOneSecond();

        assertEquals("", interpreter.command("Rita follows Rita"));

        assertEquals(
                "Rita - I love the weather today (1 second ago)\n",
                interpreter.command("Rita wall"));
    }

    @Test
    public void can_see_help() {
        assertEquals(HelpCommand.HELP_MESSAGE, interpreter.command("help"));
    }
}
