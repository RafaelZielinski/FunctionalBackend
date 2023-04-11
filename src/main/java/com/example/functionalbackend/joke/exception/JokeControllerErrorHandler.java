package com.example.functionalbackend.joke.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collections;

@ControllerAdvice
@Slf4j
public class JokeControllerErrorHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(JokeNotFoundException.class)
    @ResponseBody
    public JokeErrorResponse jokeNotFound(JokeNotFoundException exception){
        final String message = String.format("Joke with id %s not found", exception.getJokeId());
        log.info(message);
        return new JokeErrorResponse(message, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(JokeDuplicateException.class)
    @ResponseBody
    public JokePostErrorResponse jokeDuplicate(JokeDuplicateException e) {
        final String message = String.format("Joke with id [%s] already exists", e.getJokeId());
        log.info(message);
        return new JokePostErrorResponse(Collections.singletonList(message), HttpStatus.CONFLICT);
    }
}
