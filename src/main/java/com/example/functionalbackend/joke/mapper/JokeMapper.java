package com.example.functionalbackend.joke.mapper;

import com.example.functionalbackend.infrastructure.joke.dto.JokeDto;
import com.example.functionalbackend.joke.dto.TypeOfJoke;
import com.example.functionalbackend.joke.model.Joke;
import lombok.extern.log4j.Log4j2;
import java.util.Arrays;

public class JokeMapper {

    public static JokeDto mapToOfferDto(Joke joke) {
        return JokeDto.builder()
                .id(joke.getId())
                .category(joke.getCategory())
                .type(isMember(joke.getType().name()) ? joke.getType().toString() : TypeOfJoke.SINGLE.toString())
                .joke(joke.getJoke())
                .build();
    }

    static public boolean isMember(String aName) {
        final boolean match = Arrays.stream(TypeOfJoke.values()).anyMatch(element -> element.equals(TypeOfJoke.valueOf(aName)));
        return match;
    }
}
