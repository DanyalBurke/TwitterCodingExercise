package com.mimecast.exercise.users;

import java.util.concurrent.ConcurrentHashMap;

public class UserRepository {
    private final MessageFactory messageFactory;
    private final ConcurrentHashMap<String, User> users = new ConcurrentHashMap<String, User>();

    public UserRepository(MessageFactory messageFactory) {
        this.messageFactory = messageFactory;
    }

    public User get(String name) {
        return users.computeIfAbsent(name.toLowerCase(), newName -> new User(messageFactory, name));
    }
}
