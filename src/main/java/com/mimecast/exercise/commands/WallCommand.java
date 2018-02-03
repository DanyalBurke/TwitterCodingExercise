package com.mimecast.exercise.commands;

import com.mimecast.exercise.users.MessageFormatter;
import com.mimecast.exercise.users.UserRepository;
import java.util.List;
import java.util.stream.Collectors;

class WallCommand extends Command {
    private final UserRepository userRepository;
    private final MessageFormatter messageFormatter;

    public WallCommand(UserRepository userRepository, MessageFormatter messageFormatter) {
        super("(?i)\\s*([A-Z0-9_]+)\\s+wall\\s*");
        this.userRepository = userRepository;
        this.messageFormatter = messageFormatter;
    }
    @Override
    public String execute(List<String> args) {
        String name = args.get(0);
        return userRepository.get(name)
                .wallMessages()
                .stream()
                .map(msg -> messageFormatter.formatWithUser(msg))
                .collect(Collectors.joining(""));
    }
}
