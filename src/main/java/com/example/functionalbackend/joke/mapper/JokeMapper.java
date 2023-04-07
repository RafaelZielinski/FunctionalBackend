package com.example.functionalbackend.joke.mapper;

import com.example.functionalbackend.infrastructure.joke.dto.JokeDto;
import com.example.functionalbackend.joke.dto.TypeOfJoke;
import com.example.functionalbackend.joke.model.Joke;
import java.util.Arrays;
import java.util.Locale;

public class JokeMapper {

    public static JokeDto mapToJokeDto(Joke joke) {
        return JokeDto.builder()
                .id(joke.getId())
                .category(joke.getCategory())
                .type(isMember(joke.getType().name()) ? joke.getType().toString() : TypeOfJoke.SINGLE.toString())
                .joke(joke.getJoke())
                .build();
    }

    public static boolean isMember(String aName) {
        if(Arrays.stream(TypeOfJoke.values()).anyMatch(element -> element.toString().equals(aName))) return true;
        else return false;
//        return Arrays.stream(TypeOfJoke.values()).anyMatch(element -> element.equals(TypeOfJoke.valueOf(aName)));
    }

    public static Joke mapToJoke(JokeDto jokeDto) {
        final Joke joke = new Joke();
        joke.setJoke(jokeDto.getJoke());
        joke.setId0fJoke(jokeDto.getId());
        joke.setCategory(jokeDto.getCategory());
        joke.setType(isMember(jokeDto.getType()) ? TypeOfJoke.valueOf(jokeDto.getType()) : TypeOfJoke.SINGLE);
        return joke;
    }


}
