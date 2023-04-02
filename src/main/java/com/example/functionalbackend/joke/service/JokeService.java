package com.example.functionalbackend.joke.service;

import com.example.functionalbackend.infrastructure.joke.dto.JokeDto;
import com.example.functionalbackend.joke.exception.JokeNotFoundException;
import com.example.functionalbackend.joke.mapper.JokeMapper;
import com.example.functionalbackend.joke.repository.JokeRepository;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class JokeService {

    private final JokeRepository jokeRepository;

    public List<JokeDto> findAllOffers() {
        return jokeRepository.findAll().stream().map(JokeMapper::mapToOfferDto).collect(Collectors.toList());
    }

    public JokeDto findById(Long id) {
        return jokeRepository.findById(id).stream()
                .findFirst()
                .map(JokeMapper::mapToOfferDto)
                .orElseThrow(() -> {
                    throw new JokeNotFoundException(id);
                });
    }
}
