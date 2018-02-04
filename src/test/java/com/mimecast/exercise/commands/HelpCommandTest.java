package com.mimecast.exercise.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class HelpCommandTest {
    private final HelpCommand helpCommand = new HelpCommand();

    @Test
    public void help_command_shows_help() {
        assertEquals(HelpCommand.HELP_MESSAGE, helpCommand.execute("help"));
    }

    @Test
    public void it_matches_help_command() {
        assertTrue(helpCommand.matches("help"));
        assertTrue(helpCommand.matches("  help  "));

        assertFalse(helpCommand.matches("sue"));
        assertFalse(helpCommand.matches("sue wall"));
        assertFalse(helpCommand.matches("sue -> m1"));
    }
}