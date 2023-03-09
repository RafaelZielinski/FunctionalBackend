package com.example.functionalbackend.infrastructure.joke;

import com.example.functionalbackend.infrastructure.joke.dto.JokeDto;

import java.util.List;

public interface RemoteJokeClient {
    List<JokeDto> getJokes();
}
