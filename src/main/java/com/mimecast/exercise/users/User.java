package com.mimecast.exercise.users;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

public class User {
    private final String name;
    private final MessageFactory messageFactory;
    private final List<Message> messages = new ArrayList<>();
    private final Set<User> follows = new LinkedHashSet<>();

    public User(MessageFactory messageFactory, String name) {
        this.messageFactory = messageFactory;
        this.name = name;
    }

    public void post(String message) {
        messages.add(messageFactory.create(name, message));
    }

    public List<Message> messages() {
        return messages;
    }

    public void follow(User user) {
        follows.add(user);
    }

    public List<Message> wallMessages() {
        // TODO: Fix stability issue with using sorted.
        return Stream.concat(messages.stream(), follows.stream().flatMap(user -> user.messages().stream()))
                .sorted()
                .collect(toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
