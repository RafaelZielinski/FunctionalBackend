package com.example.functionalbackend.infrastructure.joke.client;

import com.example.functionalbackend.infrastructure.joke.dto.JokeDto;
import com.example.functionalbackend.infrastructure.joke.dto.MainJokeDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;

public interface SampleJokeResponse extends SampleJokeDto {

    default MainJokeDto responseWithOneJoke() {
        return MainJokeDto.builder().jokes(
                        Collections.singletonList(
                                emptyJokes())).build();
    }

    default MainJokeDto responseWithNoJokes() {
        return (MainJokeDto.builder().jokes(
                        Collections.emptyList()).build());
    }

    default MainJokeDto responseWithJokes(JokeDto... jokeDto) {
        return MainJokeDto.builder().jokes(
                        Arrays.stream(jokeDto).toList())
                .build();
    }

}
