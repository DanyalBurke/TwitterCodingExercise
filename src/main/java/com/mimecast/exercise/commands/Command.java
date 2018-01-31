package com.mimecast.exercise.commands;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class Command {
    private final Pattern pattern;

    public Command(String pattern) {
        this.pattern = Pattern.compile(pattern);
    }

    public boolean matches(String input) {
        return pattern.matcher(input).matches();
    }

    public String execute(String input) {
        Matcher matcher = pattern.matcher(input);
        if(!matcher.matches()) {
            throw new IllegalArgumentException("Command does not match input");
        }
        List<String> arguments = IntStream
                .rangeClosed(1, matcher.groupCount())
                .boxed()
                .map(i -> matcher.group(i))
                .collect(Collectors.toList());
        return execute(arguments);
    }

    protected abstract String execute(List<String> arguments);
}
