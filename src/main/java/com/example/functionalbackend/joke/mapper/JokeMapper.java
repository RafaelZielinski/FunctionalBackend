package com.example.functionalbackend.joke.mapper;

import com.example.functionalbackend.infrastructure.joke.dto.JokeDto;
import com.example.functionalbackend.joke.dto.TypeOfJoke;
import com.example.functionalbackend.joke.model.Joke;

public class JokeMapper {

    public static JokeDto mapToOfferDto(Joke joke) {
        return JokeDto.builder()
                .id(joke.getId())
                .category(joke.getCategory())
                .type(TypeOfJoke.valueOf(joke.getType().type).toString())
                .joke(joke.getJoke())
                .build();
    }
}
