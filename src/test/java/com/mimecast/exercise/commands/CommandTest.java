package com.mimecast.exercise.commands;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class CommandTest {

    private final List<String> argumentCaptures = new ArrayList<>();

    private final Command command = spy(new Command("([abc]+)=([0-9]+)") {
        @Override
        protected String execute(List<String> arguments) {
            return "Result";
        }
    });

    @Test
    public void a_command_matches_input_according_to_the_supplied_regex() {
        assertTrue(command.matches("baacc=123"));
        assertFalse(command.matches("eddff=..."));
    }

    @Test
    public void a_command_executes_with_arguments_captured_in_the_regex() {
        command.execute("baacc=123");

        ArgumentCaptor<List<String>> executeArguments = ArgumentCaptor.forClass(List.class);

        verify(command).execute(eq(asList("baacc", "123")));
    }

    @Test
    public void executing_a_command_returns_what_the_subclass_returns() {
        assertEquals("Result", command.execute("baacc=123"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void executing_a_command_that_doesnt_match_throws_iae() {
        command.execute("eddff=...");
    }
}