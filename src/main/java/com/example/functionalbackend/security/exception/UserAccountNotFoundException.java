package com.example.functionalbackend.security.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.FORBIDDEN)
public class UserAccountNotFoundException extends RuntimeException{

    public UserAccountNotFoundException(String message) {
        super(message);
    }
}
