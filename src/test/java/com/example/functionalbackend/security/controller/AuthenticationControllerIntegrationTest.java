package com.example.functionalbackend.security.controller;

import com.example.functionalbackend.FunctionalBackendApplication;
import com.example.functionalbackend.config.JokeHttpClientConfig;
import com.example.functionalbackend.security.model.Role;
import com.example.functionalbackend.security.model.User;
import com.example.functionalbackend.security.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {AuthenticationControllerIntegrationTest.TestConfig.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles({"test"})
class AuthenticationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void should_return_200_success_with_valid_credentials_to_registrate_and_token() throws Exception {
        //given
        String expectedToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyYWZla3ppZWxpbnNraUB" +
                "3cC5wbCIsImlhdCI6MTY3NzQyNjk5NiwiZXhwIjoxNjc3NDI4NDM" +
                "2fQ.TUoeJe8kmxgwUbJ0QOOCxSNjkWpwr9rPcqznYf3bRKg";
        //when
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
    void should_return_400_bad_Request_with_unvalid_credentials_to_registrate_and_token() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(unvalidUser().toString()))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void should_return_403_forbidden_with_present_account_in_database() throws Exception {
        //given
        userRepository.save(user());
        //when
        //then
        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validUser().toString()))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    void should_login_with_present_account_in_database() throws Exception {
        //given
        String expectedToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyYWZla3ppZWxpbnNraUB" +
                "3cC5wbCIsImlhdCI6MTY3NzQyNjk5NiwiZXhwIjoxNjc3NDI4NDM" +
                "2fQ.TUoeJe8kmxgwUbJ0QOOCxSNjkWpwr9rPcqznYf3bRKg";
        userRepository.save(user());
        //when
        //then
        mockMvc.perform(post("/api/v1/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validUserLogin().toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void should_no_logged_with_no_present_account_in_database() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(post("/api/v1/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validUserLogin().toString()))
                .andDo(print())
                .andExpect(status().isForbidden())
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
    private JSONObject validUserLogin() throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("email", "rafekzielinski@wp.pl");
        jo.put("password", "password");
        return jo;
    }
    private User user() {
        return User.builder()
                .firstname("Rafał")
                .lastname("Zieliński")
                .email("rafekzielinski@wp.pl")
                .password(passwordEncoder.encode("password"))
                .role(Role.USER)
                .build();
    }

    @Configuration
    @Import(FunctionalBackendApplication.class)
    static class TestConfig {

    }
}
