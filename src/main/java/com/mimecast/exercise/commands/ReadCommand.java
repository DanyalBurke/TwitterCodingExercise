package com.mimecast.exercise.commands;

import com.mimecast.exercise.users.UserRepository;
import java.util.List;
import java.util.stream.Collectors;

class ReadCommand extends Command {
    private final UserRepository userRepository;

    public ReadCommand(UserRepository userRepository) {
        super("(?i)([A-Z0-9_]+)");
        this.userRepository = userRepository;
    }
    @Override
    public String execute(List<String> args) {
        String name = args.get(0);
        return userRepository.get(name)
                .messages()
                .stream()
                .map(msg -> msg.toString() + "\n")
                .collect(Collectors.joining(""));
    }
}
