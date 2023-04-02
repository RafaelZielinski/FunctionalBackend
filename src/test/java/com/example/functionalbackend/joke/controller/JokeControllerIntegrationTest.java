package com.example.functionalbackend.joke.controller;

import com.example.functionalbackend.infrastructure.joke.client.SampleJokeDto;
import com.example.functionalbackend.infrastructure.joke.dto.JokeDto;
import com.example.functionalbackend.joke.exception.JokeControllerErrorHandler;
import com.example.functionalbackend.joke.exception.JokeErrorResponse;
import com.example.functionalbackend.joke.repository.JokeRepository;
import com.example.functionalbackend.joke.service.JokeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.istack.NotNull;
import jakarta.validation.constraints.NotEmpty;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles(profiles = "test")
@Sql({"/scripts/initial_data_jokes.sql"})
class JokeControllerIntegrationTest {

    @Autowired
    JokeController jokeController;

    @Autowired
    JokeRepository jokeRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void should_return_status_ok_when_get_offers() throws Exception {
        //Given
        //When
        final MvcResult mvcResult = mockMvc.perform(get("/jokes"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(5))
                .andReturn();
        //Then
    }

    @Test
    void should_return_status_ok_when_get_specific_offer() throws Exception {
        //Given
        //When
        final MvcResult mvcResult = mockMvc.perform(get("/jokes/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.category").value("programming"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("SINGLE"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.joke").value("FirstJoke"))
                .andReturn();
        //Then
    }

    @Test
    void should_return_status_not_found_when_get_offer_with_not_found_id() throws Exception {
        //Given
        JokeErrorResponse jokeErrorResponse = new JokeErrorResponse("Joke with id 444 not found", HttpStatus.NOT_FOUND);
        String expectedResponseBody = objectMapper.writeValueAsString(jokeErrorResponse);
        //When
        final MvcResult mvcResult = mockMvc.perform(get("/jokes/444"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();
        //Then
        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo(expectedResponseBody);
    }
}

