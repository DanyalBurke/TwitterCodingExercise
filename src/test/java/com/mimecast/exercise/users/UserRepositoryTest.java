package com.mimecast.exercise.users;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;

import org.junit.Test;

public class UserRepositoryTest {

    private MessageFactory messageFactory = mock(MessageFactory.class);
    private final UserRepository userRepository = new UserRepository(messageFactory);

    @Test
    public void getting_the_same_name_always_returns_the_same_user() {
        User rita1 = userRepository.get("Rita");
        User rita2 = userRepository.get("Rita");

        assertSame(rita1, rita2);
    }

    @Test
    public void getting_a_user_is_case_insensitive_and_first_capitalization_wins() {
        User rita1 = userRepository.get("Rita");
        User rita2 = userRepository.get("rita");

        assertSame(rita1, rita2);
        assertEquals("Rita", rita1.name());
        assertEquals("Rita", rita2.name());
    }
}