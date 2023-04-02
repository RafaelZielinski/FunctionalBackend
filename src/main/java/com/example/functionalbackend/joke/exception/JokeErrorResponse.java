package com.example.functionalbackend.joke.exception;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class JokeErrorResponse {
    private final String Message;
    private final HttpStatus status;
}
