package com.mimecast.exercise.commands;

import static java.util.Arrays.asList;

import com.mimecast.exercise.users.MessageFormatter;
import com.mimecast.exercise.users.UserRepository;
import java.util.List;

public class CommandInterpreter {
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
