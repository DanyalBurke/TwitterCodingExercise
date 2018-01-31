package com.mimecast.exercise.commands;

import com.mimecast.exercise.users.UserRepository;
import java.util.List;

class PostCommand extends Command {

    private final UserRepository userRepository;

    public PostCommand(UserRepository userRepository) {
        super("(?i)([A-Z0-9_]+)\\s->\\s(.*)");
        this.userRepository = userRepository;
    }

    @Override
    public String execute(List<String> args) {
        String name = args.get(0);
        String message = args.get(1);
        userRepository.get(name).post(message);
        return "";
    }
}
