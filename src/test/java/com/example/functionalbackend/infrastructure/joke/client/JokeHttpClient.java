package com.example.functionalbackend.infrastructure.joke.client;

import com.example.functionalbackend.infrastructure.joke.RemoteJokeClient;
import com.example.functionalbackend.infrastructure.joke.dto.JokeDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Component
@Log4j2
public class JokeHttpClient implements RemoteJokeClient {

    private static final String JOKE_URL = "https://v2.jokeapi.dev/joke/Programming?type=single&amount=10";
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<JokeDto> getJokes() {

        restTemplate.getForObject(JOKE_URL, String.class);
        return Collections.emptyList();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        final HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(headers);
//        try {
//            ResponseEntity<List<JokeDto> response = restTemplate.exchange(url, HttpMethod.GET,
//                    requestEntity, new ParameterizedTypeReference<List<JokeDto>>() {});
//            final List<JokeDto> body = response.getBody();
//            return (body != null) ? body : Collections.emptyList();
//        } catch (ResourceAccessException e) {
//            log.info("Error with downloaded jokes from api");
//            return Collections.emptyList();
//        }
//        return null;


    }
}
