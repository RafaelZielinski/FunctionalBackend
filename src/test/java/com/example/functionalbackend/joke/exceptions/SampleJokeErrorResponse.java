package com.example.functionalbackend.joke.exceptions;

import com.example.functionalbackend.joke.exception.JokeErrorResponse;
import org.springframework.http.HttpStatus;

public interface SampleJokeErrorResponse {

    default JokeErrorResponse sampleJokeErrorResponse() {
        return new JokeErrorResponse("Joke with id 100 not found", HttpStatus.NOT_FOUND);
    }
}
