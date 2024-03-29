package com.example.functionalbackend.joke.service;

import com.example.functionalbackend.infrastructure.joke.dto.JokeDto;
import com.example.functionalbackend.joke.exception.JokeNotFoundException;
import com.example.functionalbackend.joke.mapper.JokeMapper;
import com.example.functionalbackend.joke.model.Joke;
import com.example.functionalbackend.joke.repository.JokeRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JokeService {

    private final JokeRepository jokeRepository;

    public List<JokeDto> findAllOffers() {
        return jokeRepository.findAll().stream().map(JokeMapper::mapToJokeDto).collect(Collectors.toList());
    }

    public JokeDto findById(Long id) {
        return jokeRepository.findById(id).stream()
                .findFirst()
                .map(JokeMapper::mapToJokeDto)
                .orElseThrow(() -> {
                    throw new JokeNotFoundException(id);
                });
    }

    public List<Joke> saveAll(List<Joke> jokes) {
        return jokeRepository
                .saveAll(
                        jokes.stream()
                                .filter(joke -> !jokeRepository.existsById0fJoke(joke.getId0fJoke()))
                                .collect(Collectors.toList()));
    }
}
