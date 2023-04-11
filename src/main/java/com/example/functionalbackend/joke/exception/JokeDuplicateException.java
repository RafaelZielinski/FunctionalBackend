package com.example.functionalbackend.joke.exception;

import lombok.Getter;

@Getter
public class JokeDuplicateException extends RuntimeException {

    private final Long jokeId;

    public JokeDuplicateException(Long jokeId) {
        super(String.format("Joke with this id [%s] already exists", jokeId));
        this.jokeId = jokeId;
    }
}
