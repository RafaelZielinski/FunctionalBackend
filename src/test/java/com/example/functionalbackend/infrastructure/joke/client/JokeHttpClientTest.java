package com.example.functionalbackend.infrastructure.joke.client;

import com.example.functionalbackend.infrastructure.joke.RemoteJokeClient;
import com.example.functionalbackend.infrastructure.joke.dto.MainJokeDto;
import com.example.functionalbackend.infrastructure.joke.dto.TypeOfJoke;
import org.jboss.jandex.Main;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

//unit tests for joke client
class JokeHttpClientTest implements SampleJokeResponse{

    RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
    private RemoteJokeClient under = new JokeHttpClient(restTemplate,
            "https://v2.jokeapi.dev/joke/Programming");

    @Test
    void should_return_one_element_size_list_of_jokes() {
        //Given
        MainJokeDto expected = responseWithOneJoke();
        when(restTemplate.getForObject(
                any(), any(), (Object) ArgumentMatchers.any()))
                .thenReturn(expected);
        //When
        final MainJokeDto actual = under.getJokes(TypeOfJoke.SINGLE, 10);
        //Then
        assertThat(actual.getJokes()).hasSize(1);
    }

    @Test
    void should_return_empty_list_of_jokes() {
        //Given
        MainJokeDto expected = responseWithJokes();
        when(restTemplate.getForObject(
                any(), any(), (Object) ArgumentMatchers.any()))
                .thenReturn(expected);
        //When
        final MainJokeDto actual = under.getJokes(TypeOfJoke.SINGLE, 10);
        //Then
        assertThat(actual.getJokes()).isEmpty();
    }
    @Test
    void should_return_two_size_list_of_jokes() {
        //Given
        MainJokeDto expected = responseWithJokes(emptyJokes(), emptyJokes());
        when(restTemplate.getForObject(
                any(), any(), (Object) ArgumentMatchers.any()))
                .thenReturn(expected);
        //When
        final MainJokeDto actual = under.getJokes(TypeOfJoke.SINGLE, 10);
        //Then
        assertThat(actual.getJokes()).hasSize(2);
    }
}
