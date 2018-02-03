package com.mimecast.exercise.commands;

import com.mimecast.exercise.users.UserRepository;
import java.util.List;

public class FollowsCommand extends Command {
    private final UserRepository userRepository;

    public FollowsCommand(UserRepository userRepository) {
        super("(?i)\\s*([A-Z0-9_]+)\\s+follows\\s+([A-Z0-9_]+)\\s*");
        this.userRepository = userRepository;
    }

    @Override
    protected String execute(List<String> arguments) {
        String follower = arguments.get(0);
        String followed = arguments.get(1);

        userRepository
                .get(follower)
                .follow(userRepository.get(followed));

        return "";
    }
}
