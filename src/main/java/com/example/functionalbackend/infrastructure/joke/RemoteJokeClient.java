package com.example.functionalbackend.infrastructure.joke;

import com.example.functionalbackend.infrastructure.joke.dto.MainJokeDto;
import com.example.functionalbackend.infrastructure.joke.dto.TypeOfJoke;

public interface RemoteJokeClient {
    MainJokeDto getJokes(TypeOfJoke type, int amountOfJokes);
}
