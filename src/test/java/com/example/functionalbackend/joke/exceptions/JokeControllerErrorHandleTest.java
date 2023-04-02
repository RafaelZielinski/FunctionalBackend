package com.example.functionalbackend.joke.exceptions;

import com.example.functionalbackend.joke.exception.JokeControllerErrorHandler;
import com.example.functionalbackend.joke.exception.JokeErrorResponse;
import com.example.functionalbackend.joke.exception.JokeNotFoundException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JokeControllerErrorHandleTest implements SampleJokeErrorResponse, SampleJokeNotFoundException{

    @Test
    public void should_return_correct_error_response() {
        JokeControllerErrorHandler jokeControllerErrorHandler = new JokeControllerErrorHandler();
        final JokeNotFoundException givenException = sampleJokeNotFoundException(100L);
        final JokeErrorResponse expectedResponse = sampleJokeErrorResponse();

        final JokeErrorResponse actualResponse = jokeControllerErrorHandler.jokeNotFound(givenException);

        assertThat(actualResponse).isEqualTo(expectedResponse);
    }
}
