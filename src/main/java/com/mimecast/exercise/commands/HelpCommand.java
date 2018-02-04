package com.mimecast.exercise.commands;

import java.util.List;

public class HelpCommand extends Command {

    public static final String HELP_MESSAGE =
            "Post a message for a user:\n" +
            "Rita -> message\n" +
            "Bob -> message\n\n" +
            "Read messages for a user:\n" +
            "Rita\n\n" +
            "One user follows another user:\n" +
            "Rita follows Bob\n\n" +
            "Show messages on a user's wall:\n" +
            "Rita wall\n";

    public HelpCommand() {
        super("(?i)\\s*help\\s*");
    }

    @Override
    protected String execute(List<String> arguments) {
        return HELP_MESSAGE;
    }
}
