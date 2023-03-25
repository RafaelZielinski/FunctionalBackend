package com.example.functionalbackend.joke.repository;

import com.example.functionalbackend.joke.model.Joke;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JokeRepository extends JpaRepository<Joke, Long> {
}
