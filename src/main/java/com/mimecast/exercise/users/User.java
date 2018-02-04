package com.mimecast.exercise.users;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collections;
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
        if (!name.matches("^(?i)[A-Z0-9_]+$")) {
            throw new IllegalArgumentException("Name must only contain alphanumeric and underscore characters");
        }

        this.messageFactory = messageFactory;
        this.name = name;
    }

    public String name() { return name; }

    public void post(String message) {
        messages.add(messageFactory.create(name, message));
    }

    public List<Message> messages() {
        return stableSort(messages);
    }

    public void follow(User user) {
        if(!equals(user)) {
            follows.add(user);
        }
    }

    public boolean isFollowing(User user) {
        return follows.contains(user);
    }

    public List<Message> wallMessages() {
       return stableSort(Stream.concat(messages.stream(), follows.stream()
                               .flatMap(user -> user.messages().stream()))
                               .collect(toList()));
    }

    private <T extends Comparable<T>> List<T> stableSort(List<T> items) {
        List<T> sorted = new ArrayList<>(items);
        Collections.sort(sorted);
        return sorted;
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
