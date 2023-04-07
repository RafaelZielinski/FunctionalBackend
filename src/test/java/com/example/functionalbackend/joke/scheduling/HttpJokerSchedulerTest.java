package com.example.functionalbackend.joke.scheduling;

import com.example.functionalbackend.FunctionalBackendApplication;
import com.example.functionalbackend.infrastructure.joke.RemoteJokeClient;
import com.example.functionalbackend.infrastructure.joke.dto.TypeOfJoke;
import com.example.functionalbackend.joke.repository.JokeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.time.Duration;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = HttpJokerSchedulerTest.TestConfig.class)
@ActiveProfiles("schedulertest")
class HttpJokerSchedulerTest {

    @SpyBean
    RemoteJokeClient jokeClient;

    @Test
    public void should_run_http_client_jokes_fetching_exactly_two_times(@Autowired JokeRepository repository) {
        await()
                .atMost(Duration.ofSeconds(10))
                .untilAsserted(() -> verify(jokeClient, times(2)).getJokes(TypeOfJoke.SINGLE, 2));
    }


    @Import(FunctionalBackendApplication.class)
    static class TestConfig {

    }

}
