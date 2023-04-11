package com.example.functionalbackend.joke.exception;

import org.springframework.http.HttpStatus;

public record JokeErrorResponse(String Message, HttpStatus status) {
}
