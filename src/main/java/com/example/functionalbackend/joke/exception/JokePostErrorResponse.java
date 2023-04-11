package com.example.functionalbackend.joke.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public record JokePostErrorResponse(List<String> messages,
                                    HttpStatus status) {
}
