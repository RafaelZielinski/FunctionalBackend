package com.example.functionalbackend.security.controller;

import com.example.functionalbackend.FunctionalBackendApplication;
import com.example.functionalbackend.security.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = AuthenticationControllerIntegrationTest.TestConfig.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthenticationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void should_return_200_success_with_valid_credentials_to_registrate_and_token() throws Exception {
        //given
        String expectedToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyYWZla3ppZWxpbnNraUB" +
                "3cC5wbCIsImlhdCI6MTY3NzQyNjk5NiwiZXhwIjoxNjc3NDI4NDM" +
                "2fQ.TUoeJe8kmxgwUbJ0QOOCxSNjkWpwr9rPcqznYf3bRKg";
        //when
        userRepository.deleteAll();
        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validUser().toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(result1 -> jsonPath("$.token").value(expectedToken))
                .andReturn();
        //then
        assertThat(userRepository.findAll()).isNotNull();
    }

    @Test
    void should_return_403_forbidden_with_unvalid_credentials_to_registrate_and_token() throws Exception {
        //given
        //when
        //then
        userRepository.deleteAll();
        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(unvalidUser().toString()))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }


    private JSONObject validUser() throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("firstname", "Rafał");
        jo.put("lastname", "Zieliński");
        jo.put("email", "rafekzielinski@wp.pl");
        jo.put("password", "password");
        return jo;
    }

    private JSONObject unvalidUser() throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("firstname", "Raf");
        jo.put("lastname", "Ziel");
        jo.put("email", "rafekzielinski@wp.pl");
        jo.put("password", "password");
        return jo;
    }

    @Import(FunctionalBackendApplication.class)
    static class TestConfig {

    }
}
