package com.example.functionalbackend.infrastructure.joke.client;

import com.example.functionalbackend.infrastructure.joke.RemoteJokeClient;
import com.example.functionalbackend.infrastructure.joke.dto.MainJokeDto;
import com.example.functionalbackend.infrastructure.joke.dto.TypeOfJoke;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.logging.Log;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@AllArgsConstructor
@Log4j2
public class JokeHttpClient implements RemoteJokeClient {

    private final RestTemplate restTemplate;
    private final String URL;

    @Override
    public MainJokeDto getJokes(TypeOfJoke type, int amountOfJokes) {
        try {
            MainJokeDto forObject = restTemplate.getForObject(
                    URL + "?type={type}&amount={amount}",
                    MainJokeDto.class, type, amountOfJokes);
            return (forObject != null) ? forObject : MainJokeDto.builder().build();
        } catch (RestClientException | IllegalArgumentException e) {
            log.error("Something wrong with client from jokes " + e.getMessage() + " " + e.getClass());
            return MainJokeDto.builder().build();
        }
    }
}
