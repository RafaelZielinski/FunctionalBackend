package com.example.functionalbackend.joke.service;


import com.example.functionalbackend.joke.dto.JokeDto;
import com.example.functionalbackend.joke.exception.JokeNotFoundException;
import com.example.functionalbackend.joke.repository.JokeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JokeServiceTest implements JokeDtoSample {

    @Mock
    private JokeRepository jokeRepository;

    @Autowired
    @InjectMocks
    private JokeService under;

    @Test
    public void should_return_two_expected_jokes() {
        //given
        when(jokeRepository.findAll()).thenReturn(listOfTwoJokeDtos());
        //when
        final List<JokeDto> actualOffers = under.findAllOffers();
        //then
        assertThat(actualOffers).isNotNull().contains(firstDto(), secondDto());
    }

    @Test
    public void should_return_correct_joke_with_particular_id_number_one() {
        //given
        when(jokeRepository.findById(1L)).thenReturn(Optional.of(first()));
        //when
        final JokeDto actualJoke = under.findById(1L);
        //then
        assertThat(actualJoke).isEqualTo(firstDto());
    }

    @Test
    public void should_return_correct_joke_with_particular_id_number_two() {
        //given
        when(jokeRepository.findById(2L)).thenReturn(Optional.of(second()));
        //when
        final JokeDto actualJoke = under.findById(2L);
        //then
        assertThat(actualJoke).isEqualTo(secondDto());
    }

    @Test
    public void should_throw_joke_not_found_exception_when_n_offer_with_given_id() {
        //given
        //when
        Throwable thrown = catchThrowable(() -> under.findById(444L));
        //then
        assertThat(thrown).isInstanceOf(JokeNotFoundException.class)
                .hasMessage("Joke with id 444 not found");
    }
}
