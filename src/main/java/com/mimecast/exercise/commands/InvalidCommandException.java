package com.mimecast.exercise.commands;

public class InvalidCommandException extends RuntimeException {

    public InvalidCommandException(String input) {
        super("Invalid command " + input);
    }
}
