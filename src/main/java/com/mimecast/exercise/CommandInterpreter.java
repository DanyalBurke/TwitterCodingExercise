package com.mimecast.exercise;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CommandInterpreter {
    private static final Pattern POST_COMMAND = Pattern.compile("(?i)([A-Z0-9_]+)\\s->\\s(.*)");
    private static final Pattern NAME_COMMAND = Pattern.compile("(?i)([A-Z0-9_]+)");

    private final UserRepository userRepository;

    public CommandInterpreter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String command(String command) {
        Matcher postMatcher = POST_COMMAND.matcher(command);
        Matcher nameMatcher = NAME_COMMAND.matcher(command);
        if(postMatcher.matches()) {
            String name = postMatcher.group(1);
            String message = postMatcher.group(2);

            userRepository.get(name).post(message);
            return "";
        }
        else if (nameMatcher.matches()) {
            String name = nameMatcher.group(1);
            return userRepository.get(name)
                    .messages()
                    .stream()
                    .map(msg -> msg.toString() + "\n")
                    .collect(Collectors.joining(""));
        }
        else {
            throw new IllegalArgumentException("Invalid command");
        }
    }
}
