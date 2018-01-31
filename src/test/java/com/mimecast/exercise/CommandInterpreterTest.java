package com.mimecast.exercise;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class CommandInterpreterTest {

    private CommandInterpreter interpreter = new CommandInterpreter(new UserRepository());

    @Test
    public void when_I_post_a_message_to_a_user_then_I_can_read_it_back() {
        assertEquals("", interpreter.command("Rita -> I love the weather today"));
        assertEquals("I love the weather today\n", interpreter.command("Rita"));

        assertEquals("", interpreter.command("Bob -> Damn! We lost!"));
        assertEquals("", interpreter.command("Bob -> Good game though."));
        assertEquals("Damn! We lost!\nGood game though.\n", interpreter.command("Bob"));
    }

    // name_is_case_insensitive()

    // whitespace_is_not_considered_except_in_message()

    // mutliline support in a command?

    // what characters to support in a name?

    // what should it throw for an invalid command
}
