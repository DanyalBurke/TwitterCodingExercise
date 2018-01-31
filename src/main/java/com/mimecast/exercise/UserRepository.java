package com.mimecast.exercise;

import java.util.concurrent.ConcurrentHashMap;

class UserRepository {
    private ConcurrentHashMap<String, User> users = new ConcurrentHashMap<String, User>();

    public User get(String name) {
        return users.computeIfAbsent(name, newName -> new User(newName));
    }
}
