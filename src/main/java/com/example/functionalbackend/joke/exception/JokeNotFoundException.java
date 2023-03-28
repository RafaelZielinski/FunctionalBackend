package com.example.functionalbackend.joke.exception;

import lombok.Getter;

@Getter
public class JokeNotFoundException extends RuntimeException {

    private final Long jokeId;

    public JokeNotFoundException(Long jokeId) {
        super(String.format("Joke with id %s not found", jokeId));
        this.jokeId = jokeId;
    }
}
