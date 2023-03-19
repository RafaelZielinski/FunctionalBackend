package com.example.functionalbackend.infrastructure.joke.client;

import com.example.functionalbackend.infrastructure.joke.dto.JokeDto;

public interface SampleJokeDto {
    default JokeDto emptyJokes() {
        return JokeDto.builder().build();
    }

    default JokeDto jokeWithParameters(String category, String type, String joke, Long joke_id) {
        return JokeDto.builder()
                .category(category)
                .type(type)
                .joke(joke)
                .id(joke_id)
                .build();
    }
}
