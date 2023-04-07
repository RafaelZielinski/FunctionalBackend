package com.example.functionalbackend.joke.repository;

import com.example.functionalbackend.joke.model.Joke;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JokeRepository extends JpaRepository<Joke, Long> {

    Boolean existsById0fJoke(Long idOfJoke);
}
