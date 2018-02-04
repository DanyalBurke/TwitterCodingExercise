package com.mimecast.exercise.users;

import java.time.LocalDateTime;

public class Message implements Comparable<Message> {
    private final LocalDateTime postTime;
    private final String user;
    private final String message;

    Message(LocalDateTime postTime, String user, String message) {
        this.postTime = postTime;
        this.user = user;
        this.message = message;
    }

    public LocalDateTime postTime() {
        return postTime;
    }

    public String message() { return message; }


    public String user() { return user; }

    @Override
    public int compareTo(Message other) {
        return other.postTime().compareTo(this.postTime);
    }

    @Override
    public String toString() {
        return "Message[" + postTime + "," + user + "," + message + "]";
    }
}
