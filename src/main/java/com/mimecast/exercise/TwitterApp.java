package com.mimecast.exercise;

import com.mimecast.exercise.commands.CommandInterpreter;
import com.mimecast.exercise.commands.InvalidCommandException;
import com.mimecast.exercise.users.MessageFactory;
import com.mimecast.exercise.users.MessageFormatter;
import com.mimecast.exercise.users.UserRepository;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.time.Clock;

public class TwitterApp {
    private final BufferedReader in;
    private final PrintStream out;

    public TwitterApp(Charset inputCharset, InputStream in, PrintStream out) {
        this.in = new BufferedReader(new InputStreamReader(in, inputCharset));
        this.out = out;
    }

    void run() throws IOException {
        MessageFactory factory = new MessageFactory(Clock.systemUTC());
        UserRepository repository = new UserRepository(factory);
        MessageFormatter messageFormatter = new MessageFormatter(Clock.systemUTC());
        CommandInterpreter interpreter = new CommandInterpreter(repository, messageFormatter);

        print("Welcome to Mimecast Twitter App. Type help for commands\n");

        print("> ");
        String input;
        while((input = in.readLine()) != null) {
            try {
                print(interpreter.command(input));
            }
            catch(InvalidCommandException e) {
                print("Invalid command. Type help for commands.\n");
            }
            print("> ");
        }
    }

    public void print(String message) {
        out.print(message);
        out.flush();
    }

    public static void main(String[] args) throws IOException {
        new TwitterApp(Charset.defaultCharset(), System.in, System.out).run();
    }
}
