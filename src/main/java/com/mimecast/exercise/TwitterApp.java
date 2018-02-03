package com.mimecast.exercise;

import com.mimecast.exercise.commands.CommandInterpreter;
import com.mimecast.exercise.commands.InvalidCommandException;
import com.mimecast.exercise.users.MessageFactory;
import com.mimecast.exercise.users.MessageFormatter;
import com.mimecast.exercise.users.UserRepository;
import java.time.Clock;
import java.util.Scanner;

public class TwitterApp {
    public static void main(String[] args) {
        MessageFactory factory = new MessageFactory(Clock.systemUTC());
        UserRepository repository = new UserRepository(factory);
        MessageFormatter messageFormatter = new MessageFormatter(Clock.systemUTC());
        CommandInterpreter interpreter = new CommandInterpreter(repository, messageFormatter);

        print("Welcome to Mimecast Twitter App. Type help for commands\n");

        Scanner scanner = new Scanner(System.in);
        print("> ");
        while(scanner.hasNextLine()) {
            String input = scanner.nextLine();
            try {
                print(interpreter.command(input));
            }
            catch(InvalidCommandException e) {
                print("Invalid command. Type help for commands.\n");
            }
            print("> ");
        }
    }

    public static void print(String message) {
        System.out.print(message);
        System.out.flush();
    }
}
