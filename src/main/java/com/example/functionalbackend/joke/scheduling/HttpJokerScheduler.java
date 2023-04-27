package com.example.functionalbackend.joke.scheduling;

import com.example.functionalbackend.infrastructure.joke.RemoteJokeClient;
import com.example.functionalbackend.infrastructure.joke.dto.JokeDto;
import com.example.functionalbackend.infrastructure.joke.dto.MainJokeDto;
import com.example.functionalbackend.infrastructure.joke.dto.TypeOfJoke;
import com.example.functionalbackend.joke.mapper.JokeMapper;
import com.example.functionalbackend.joke.model.Joke;
import com.example.functionalbackend.joke.service.JokeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Component
public class HttpJokerScheduler {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private static final String STARTED_JOKES_FETCHING_MESSAGE = "Started jokes fetching {}";
    private static final String STOPPER_JOKES_FETCHING_MESSAGE = "Stopped jokes fetching {}";
    private static final String ADDED_JOKES_FETCHING_MESSAGE = "Added new jokes {}";

    private final RemoteJokeClient remoteJokeClient;
    private final JokeService jokeService;

    @Scheduled(fixedDelayString = "${delay.joke.scheduler}")
    public void getJokesFromHttpService() {
        log.info(STARTED_JOKES_FETCHING_MESSAGE);
        final MainJokeDto jokes = remoteJokeClient.getJokes(TypeOfJoke.SINGLE, 2);
        final List<Joke> addedJokes = jokeService.saveAll(jokes.getJokes()
                .stream().map(JokeMapper::mapToJoke).collect(Collectors.toList()));
        log.info(ADDED_JOKES_FETCHING_MESSAGE, addedJokes.size());
        log.info(STOPPER_JOKES_FETCHING_MESSAGE, dateFormat.format(new Date()));
    }
}
