package com.example.functionalbackend.joke.controller;


import com.example.functionalbackend.exceptions.ApiValidationErrorDto;
import com.example.functionalbackend.joke.exception.JokeErrorResponse;
import com.example.functionalbackend.joke.exception.JokePostErrorResponse;
import com.example.functionalbackend.joke.model.Joke;
import com.example.functionalbackend.joke.repository.JokeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles(profiles = "test")
@Sql(value = {"/scripts/initial_data_jokes.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
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
    void should_return_status_ok_when_get_jokes() throws Exception {
        //Given
        //When
        System.out.println(jokeRepository.findAll().size());
        final MvcResult mvcResult = mockMvc.perform(get("/jokes"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(5))
                .andReturn();

        //Then
    }

    @Test
    void should_return_status_ok_when_get_specific_jokes() throws Exception {
        //Given
        //When
        mockMvc.perform(get("/jokes/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.category").value("programming"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("SINGLE"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.joke").value("FirstJoke"))
                .andReturn();
        //Then
    }

    @Test
    void should_return_status_not_found_when_get_joke_with_not_found_id() throws Exception {
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

    @Test
    void should_return_status_ok_when_saved_joke() throws Exception {
        //Given
        jokeRepository.deleteAll();
        JSONObject jo = getRealJoke();
        //When
        final MvcResult mvcResult = mockMvc.perform(post("/jokes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jo.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.category").value("programming"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.joke").value("Test joke"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("SINGLE"))
                .andReturn();
        Joke actualJoke = jokeRepository.findJokeById0fJoke(15L).get();

        //Then
        assertThat(jokeRepository.findAll()).hasSize(1);
        assertThat(actualJoke.getJoke()).isEqualTo("Test joke");
        assertThat(actualJoke.getType().type).isEqualTo("single");
        assertThat(actualJoke.getId0fJoke()).isEqualTo(15L);
        assertThat(actualJoke.getCategory()).isEqualTo("programming");

    }

    @Test
    void should_return_status_conflict_when_joke_is_already_in_database() throws Exception {
        //Given
        JSONObject jo = getFakeJoke();
        JokePostErrorResponse expectedResponseBody = new JokePostErrorResponse(
                List.of("Joke with id [23] already exists"), HttpStatus.CONFLICT);
        String stringResponseBody = objectMapper.writeValueAsString(expectedResponseBody);
        //When
        MvcResult mvcResult = mockMvc.perform(post("/jokes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jo.toString()))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andReturn();
        //Then
        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo(stringResponseBody);
    }

    @Test
    void should_return_status_bad_request_when_miss_one_of_argument_in_body() throws Exception {
        //Given
        JSONObject jo = getUnvalidJoke();
        ApiValidationErrorDto expectedResponseBody = new ApiValidationErrorDto(
                List.of("Provide not null joke ", "Provide not empty joke"),
                HttpStatus.BAD_REQUEST);
        String stringResponseBody = objectMapper.writeValueAsString(expectedResponseBody);
        //When
        MvcResult mvcResult = mockMvc.perform(post("/jokes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jo.toString()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
        //Then
        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo(stringResponseBody);
    }

    private JSONObject getRealJoke() throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("category", "programming");
        jo.put("type", "SINGLE");
        jo.put("joke", "Test joke");
        jo.put("id", 15);
        return jo;
    }

    private JSONObject getUnvalidJoke() throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("category", "programming");
        jo.put("type", "SINGLE");
        jo.put("id", 99);
        return jo;
    }

    private JSONObject getFakeJoke() throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("category", "toys");
        jo.put("type", "SINGLE");
        jo.put("joke", "unordinary");
        jo.put("id", 23);
        return jo;
    }
}

