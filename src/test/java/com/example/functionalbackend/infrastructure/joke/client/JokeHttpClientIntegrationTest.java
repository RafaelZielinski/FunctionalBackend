package com.example.functionalbackend.infrastructure.joke.client;

import com.example.functionalbackend.config.JokeHttpClientTestConfig;
import com.example.functionalbackend.infrastructure.joke.RemoteJokeClient;
import com.example.functionalbackend.infrastructure.joke.dto.JokeDto;

import com.example.functionalbackend.infrastructure.joke.dto.TypeOfJoke;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.http.Fault;
import org.assertj.core.api.BDDAssertions;
import org.json.JSONException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.TestSocketUtils;
import wiremock.org.apache.http.HttpStatus;

import java.util.Arrays;
import java.util.Collections;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.assertj.core.api.BDDAssertions.then;

class JokeHttpClientIntegrationTest implements SampleJokeDto {

    int port = TestSocketUtils.findAvailableTcpPort();

    WireMockServer wireMockServer;

    RemoteJokeClient remoteJokeClient = new JokeHttpClientTestConfig().remoteJokeClient("http://localhost:" +
            port + "/jokes", 1000, 1000);

    @BeforeEach
    void setUp() {
        wireMockServer = new WireMockServer(options().port(port));
        wireMockServer.start();
        WireMock.configureFor(port);
    }

    @AfterEach
    void tearDown() {
        wireMockServer.stop();
    }

    @Test
    void should_return_two_jokes() throws JSONException {
        WireMock.stubFor(WireMock.get("/jokes?type=SINGLE&amount=2")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withHeader("Content-Type", "application/json")
                        .withBody(bodyWithTwoJokesJson())));



                then(remoteJokeClient.getJokes(TypeOfJoke.SINGLE, 2).getJokes())
                        .containsExactlyInAnyOrderElementsOf(Arrays.asList(firstJoke(), secondJoke()));
    }

    @Test
    void should_fail_with_connection_reset_by_peer() {
        WireMock.stubFor(WireMock.get("/jokes?type=SINGLE&amount=2")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withHeader("Content-Type", "application/json")
                        .withFault(Fault.CONNECTION_RESET_BY_PEER)));

        then(remoteJokeClient.getJokes(TypeOfJoke.SINGLE, 2).getJokes()).isNull();
    }

    @Test
    void should_fail_with_empty_response() {
        WireMock.stubFor(WireMock.get("/jokes?type=SINGLE&amount=2")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withHeader("Content-Type", "application/json")
                        .withFault(Fault.EMPTY_RESPONSE)));

        then(remoteJokeClient.getJokes(TypeOfJoke.SINGLE, 2).getJokes()).isNull();
    }

    @Test
    void should_fail_with_malformed() {
        WireMock.stubFor(WireMock.get("/jokes?type=SINGLE&amount=2")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.SC_OK)
                .withHeader("Content-Type", "application/json")
                .withFault(Fault.MALFORMED_RESPONSE_CHUNK)));

        then(remoteJokeClient.getJokes(TypeOfJoke.SINGLE, 2).getJokes()).isNull();
    }

    @Test
    void should_fail_with_random() {
        WireMock.stubFor(WireMock.get("/jokes?type=SINGLE&amount=2")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withHeader("Content-Type", "application/json")
                        .withFault(Fault.RANDOM_DATA_THEN_CLOSE)));

        then(remoteJokeClient.getJokes(TypeOfJoke.SINGLE, 2).getJokes()).isNull();
    }

    @Test
    void should_return_one_joke() {
        WireMock.stubFor(WireMock.get("/jokes?type=SINGLE&amount=1")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withHeader("Content-Type", "application/json")
                        .withBody(bodyWithOneJokes())));

        then(remoteJokeClient.getJokes(TypeOfJoke.SINGLE, 1).getJokes()).containsExactlyInAnyOrderElementsOf(
                Collections.singleton(firstJoke()));
    }

    @Test
    void should_return_zero_joke_when_status_is_ok() {
        WireMock.stubFor(WireMock.get("/jokes?type=SINGLE&amount=0")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withHeader("Content-Type", "application/json")
                        .withBody(bodyWithZeroJokes())));


        then(remoteJokeClient.getJokes(TypeOfJoke.SINGLE, 0)).isNotNull();
        then(remoteJokeClient.getJokes(TypeOfJoke.SINGLE, 0).getJokes()).isNull();
    }

    @Test
    void should_return_response_unauthorized_status_exception_when_http_service_returning_unauthorized_status() {
        WireMock.stubFor(WireMock.get("/jokes?type=SINGLE&amount=2")
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(HttpStatus.SC_UNAUTHORIZED)));

        BDDAssertions.thenThrownBy(() ->
                remoteJokeClient.getJokes(TypeOfJoke.SINGLE, 2)).hasMessage("401 UNAUTHORIZED \"You don't have access to get API\"");
    }

    @Test
    void should_return_response_not_found_status_exception_when_http_service_returning_not_found_status() {
        WireMock.stubFor(WireMock.get("/jokes?type=SINGLE&amount=2")
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(HttpStatus.SC_NOT_FOUND)));

        BDDAssertions.thenThrownBy(() ->
                remoteJokeClient.getJokes(TypeOfJoke.SINGLE, 2)).hasMessage("404 NOT_FOUND \"URL not found or may be wrong\"");
    }

    @Test
    void should_return_zero_job_offers_when_response_delay_is_1500_miles() throws JSONException {
//        final String uri = "http://localhost:" + port + "/offers";
//        RemoteOfferClient remoteOfferClient = new OfferHttpClientTestConfig().remoteOfferTestClient(uri, 1000, 1000);
        WireMock.stubFor(WireMock.get("/jokes?type=SINGLE&amount=2")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withHeader("Content-Type", "application/json")
                        .withBody(bodyWithTwoJokesJson())
                        .withFixedDelay(1500)));

        then(remoteJokeClient.getJokes(TypeOfJoke.SINGLE, 2).getJokes()).isNull();
    }

    private String bodyWithOneJokes() {

        return "{\"error\":false,\"amount\":2,\"jokes\":[{\"category\":\"Programming\",\"type\":\"single\"," +
                "\"joke\":\"\\\"We messed up the keming again guys.\\\"\",\"id\":1}]}";
    }


    private String bodyWithTwoJokesJson() throws JSONException {

       return "{\"error\":false,\"amount\":2,\"jokes\":[{\"category\":\"Programming\",\"type\":\"single\"," +
               "\"joke\":\"\\\"We messed up the keming again guys.\\\"\",\"id\":1}," +
               "{\"category\":\"Programming\",\"type\":\"single\",\"joke\":\"Your mama's so FAT she can't save files bigger than 4GB.\",\"id\":2}]}";
    }
    private JokeDto firstJoke() {
        return jokeWithParameters("Programming", "single", "\"We messed up the keming again guys.\"", 1L);
    }

    private JokeDto secondJoke() {
        return jokeWithParameters("Programming", "single", "Your mama's so FAT she can't save files bigger than 4GB.",
                2L);
    }

    private String bodyWithZeroJokes() {
        return "[]";
    }


}
