package com.example.functionalbackend.joke.service;

import com.example.functionalbackend.infrastructure.joke.dto.JokeDto;
import com.example.functionalbackend.infrastructure.joke.dto.TypeOfJoke;
import com.example.functionalbackend.joke.model.Joke;

import java.util.Arrays;
import java.util.List;

public interface JokeDtoSample {

    default JokeDto firstDto() {
        return JokeDto.builder()
                .id(1L)
                .type(TypeOfJoke.SINGLE.toString())
                .category("programming")
                .joke("FirstJoke")
                .build();
    }

    default JokeDto secondDto() {
        return JokeDto.builder()
                .id(2L)
                .type(TypeOfJoke.TWO_PART.toString())
                .category("political")
                .joke("SecondJoke")
                .build();
    }

//
//
//    default List<JokeDto> listOfTwoJokeDtos() {
//        return Arrays.asList(first(), second());
//    }

    default Joke first() {
        return new Joke(1L,
                "programming",
                com.example.functionalbackend.joke.dto.TypeOfJoke.SINGLE,
                "FirstJoke",
                33);
    }

    default Joke second() {
        return new Joke(2L,
                "political",
                com.example.functionalbackend.joke.dto.TypeOfJoke.TWO_PART,
                "SecondJoke",
                34);
    }

    default List<Joke> listOfTwoJokeDtos() {
        return Arrays.asList(first(), second());
    }
}
