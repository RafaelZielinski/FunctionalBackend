package com.example.functionalbackend.joke.exceptions;

import com.example.functionalbackend.joke.exception.JokeNotFoundException;

public interface SampleJokeNotFoundException {

    default JokeNotFoundException sampleJokeNotFoundException(Long id) {
        return new JokeNotFoundException(id);
    }
}
