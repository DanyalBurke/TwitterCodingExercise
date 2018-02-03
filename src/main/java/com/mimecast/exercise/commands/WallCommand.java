package com.mimecast.exercise.commands;

import com.mimecast.exercise.users.UserRepository;
import java.util.List;
import java.util.stream.Collectors;

class WallCommand extends Command {
    private final UserRepository userRepository;

    public WallCommand(UserRepository userRepository) {
        super("(?i)([A-Z0-9_]+) wall");
        this.userRepository = userRepository;
    }
    @Override
    public String execute(List<String> args) {
        String name = args.get(0);
        return userRepository.get(name)
                .wallMessages()
                .stream()
                .map(msg -> msg.user() + " - " + msg.message() + "\n")
                .collect(Collectors.joining(""));
    }
}
