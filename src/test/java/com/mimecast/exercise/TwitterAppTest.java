package com.mimecast.exercise;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.junit.Test;

public class TwitterAppTest {
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();

    @Test
    public void the_app_integrates_cli_with_the_interpreter() throws IOException {
        new TwitterApp(
                StandardCharsets.UTF_8,
                asByteStream("Rita -> I love the weather today\n" + "Rita\n"),
                new PrintStream(out))
                .run();

        assertEquals(
                "Welcome to Mimecast Twitter App. Type help for commands\n" +
                "> > I love the weather today (0 seconds ago)\n" +
                "> ",
                out.toString(StandardCharsets.UTF_8.name())
                   .replaceAll("[0-9]+ seconds?", "0 seconds"));
    }

    @Test
    public void invalid_command_results_in_error_message() throws IOException {
        new TwitterApp(
                StandardCharsets.UTF_8,
                asByteStream("%$!\n"),
                new PrintStream(out))
                .run();

        assertEquals(
                "Welcome to Mimecast Twitter App. Type help for commands\n" +
                        "> Invalid command. Type help for commands.\n" +
                        "> ",
                out.toString(StandardCharsets.UTF_8.name())
                        .replaceAll("[0-9]+ seconds?", "0 seconds"));
    }

    private InputStream asByteStream(String input) {
        return new ByteArrayInputStream(input.getBytes(Charset.defaultCharset()));
    }
}