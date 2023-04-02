package com.example.functionalbackend.joke.controller;

import com.example.functionalbackend.infrastructure.joke.dto.JokeDto;
import com.example.functionalbackend.joke.service.JokeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/jokes")
@AllArgsConstructor
public class JokeController {

    private JokeService jokeService;

    @GetMapping
    public ResponseEntity<List<JokeDto>> findAllOffers() {
        return ResponseEntity.ok(jokeService.findAllOffers());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<JokeDto> findJokeById(@PathVariable Long id) {
        return ResponseEntity.ok(jokeService.findById(id));
    }
}
