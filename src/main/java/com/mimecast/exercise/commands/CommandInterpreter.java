package com.mimecast.exercise.commands;

import static java.util.Arrays.asList;

import com.mimecast.exercise.users.MessageFormatter;
import com.mimecast.exercise.users.UserRepository;
import java.util.List;
import java.util.regex.Pattern;

public class CommandInterpreter {
    private static final Pattern POST_COMMAND = Pattern.compile("(?i)([A-Z0-9_]+)\\s->\\s(.*)");
    private static final Pattern NAME_COMMAND = Pattern.compile("(?i)([A-Z0-9_]+)");

    private final UserRepository userRepository;

    private final List<Command> commandPatterns;

    public CommandInterpreter(UserRepository userRepository, MessageFormatter messageFormatter) {
        this.userRepository = userRepository;
        commandPatterns = asList(
                new HelpCommand(),
                new PostCommand(userRepository),
                new ReadCommand(userRepository, messageFormatter),
                new WallCommand(userRepository, messageFormatter),
                new FollowsCommand(userRepository)
        );
    }

    public String command(String input) {
        Command command =
                commandPatterns.stream()
                    .filter(pattern -> pattern.matches(input))
                    .findFirst()
                    .orElseThrow(() -> new InvalidCommandException(input));

        return command.execute(input);
    }

}
