package com.example.functionalbackend.exceptions;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class ApiValidationErrorDto {

    private final List<String> messages;
    private final HttpStatus status;
}
