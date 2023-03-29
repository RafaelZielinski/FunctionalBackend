package com.example.functionalbackend;

import com.example.functionalbackend.config.JokeHttpClientConfig;
import com.example.functionalbackend.config.JokeHttpClientTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = {JokeHttpClientTestConfig.class})

class FunctionalBackendApplicationTests {

    @Test
    void contextLoads() {
    }

}
