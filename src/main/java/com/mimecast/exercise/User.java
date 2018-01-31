package com.mimecast.exercise;

import java.util.ArrayList;
import java.util.List;

class User {
    private final List<Message> messages = new ArrayList<Message>();

    public User(String newName) {
    }

    public void post(String message) {
        messages.add(new Message(message));
    }

    public List<Message> messages() {
        return messages;
    }
}
