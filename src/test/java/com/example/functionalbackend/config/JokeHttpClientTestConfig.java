package com.example.functionalbackend.config;

import com.example.functionalbackend.infrastructure.joke.RemoteJokeClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class JokeHttpClientTestConfig extends JokeHttpClientConfig {

    public RemoteJokeClient remoteJokeClient(String uri, int connectionTimeout, int readTimeout) {
        final RestTemplate restTemplate = restTemplate(connectionTimeout, readTimeout, restTemplateResponseErrorHandler());
        return remoteJokeClient(restTemplate, uri);
    }
}
