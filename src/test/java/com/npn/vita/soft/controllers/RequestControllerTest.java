package com.npn.vita.soft.controllers;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.Base64Utils;

import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RequestControllerTest {

    @Autowired
    private WebTestClient webClient;

    @Test
    void createRequest() {
        String messageTemplate = "{ \"message\":\"%s\", \"header\":\"%s\"}";
        webClient.post().uri("/api/requests/add")
                .bodyValue(getRandomMessage(messageTemplate))
                .header(HttpHeaders.CONTENT_TYPE,"application/json;charset=UTF-8")
                .header(HttpHeaders.AUTHORIZATION, getAuthorisationHeader("user","user"))
                .exchange().expectStatus().isOk();

        webClient.post().uri("/api/requests/add")
                .bodyValue(getRandomMessage(messageTemplate))
                .header(HttpHeaders.CONTENT_TYPE,"application/json;charset=UTF-8")
                .header(HttpHeaders.AUTHORIZATION, getAuthorisationHeader("admin","admin"))
                .exchange().expectStatus().isForbidden();

        webClient.post().uri("/api/requests/add")
                .bodyValue(getRandomMessage(messageTemplate))
                .header(HttpHeaders.CONTENT_TYPE,"application/json;charset=UTF-8")
                .header(HttpHeaders.AUTHORIZATION, getAuthorisationHeader("operator","operator"))
                .exchange().expectStatus().isForbidden();
    }

    @Test
    void getUserRequest() {
        FluxExchangeResult<String> body = webClient.get().uri("/api/requests/getMyRequests")
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, getAuthorisationHeader("user","user"))
                .exchange().expectStatus().isOk().returnResult(String.class);

        if (body.getResponseBodyContent()!= null)
            System.out.println(new String(body.getResponseBodyContent()));

        webClient.get().uri("/api/requests/getMyRequests")
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, getAuthorisationHeader("admin","admin"))
                .exchange().expectStatus().isForbidden();

        webClient.get().uri("/api/requests/getMyRequests")
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, getAuthorisationHeader("operator","operator"))
                .exchange().expectStatus().isForbidden();

        webClient.get().uri("/api/requests/getMyRequests")
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, getAuthorisationHeader("user2","user"))
                .exchange().expectStatus().isOk().returnResult(String.class);
    }

    @Test
    void testGetUserRequest() {
        FluxExchangeResult<String> body = webClient.get().uri("/api/requests/1")
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, getAuthorisationHeader("user","user"))
                .exchange().expectStatus().isOk().returnResult(String.class);
        if (body.getResponseBodyContent()!= null)
            System.out.println(new String(body.getResponseBodyContent()));

        webClient.get().uri("/api/requests/1")
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, getAuthorisationHeader("admin","admin"))
                .exchange().expectStatus().isForbidden();

        webClient.get().uri("/api/requests/1")
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, getAuthorisationHeader("operator","operator"))
                .exchange().expectStatus().isForbidden();

        webClient.get().uri("/api/requests/1")
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, getAuthorisationHeader("user2","user"))
                .exchange().expectStatus().is4xxClientError();
    }

    @Test
    void updateRawRequest() {
        String message = "{ \"message\":\"updatedMessage\", \"header\":\"updatedHeader\"}";
        webClient.put().uri("/api/requests/1/updateRawRequest")
                .bodyValue(message)
                .header(HttpHeaders.CONTENT_TYPE,"application/json;charset=UTF-8")
                .header(HttpHeaders.AUTHORIZATION, getAuthorisationHeader("user","user"))
                .exchange().expectStatus().isOk();

        webClient.put().uri("/api/requests/1/updateRawRequest")
                .bodyValue(message)
                .header(HttpHeaders.CONTENT_TYPE,"application/json;charset=UTF-8")
                .header(HttpHeaders.AUTHORIZATION, getAuthorisationHeader("admin","admin"))
                .exchange().expectStatus().isForbidden();

        webClient.put().uri("/api/requests/1/updateRawRequest")
                .bodyValue(message)
                .header(HttpHeaders.CONTENT_TYPE,"application/json;charset=UTF-8")
                .header(HttpHeaders.AUTHORIZATION, getAuthorisationHeader("operator","operator"))
                .exchange().expectStatus().isForbidden();

        webClient.put().uri("/api/requests/1/updateRawRequest")
                .bodyValue(message)
                .header(HttpHeaders.CONTENT_TYPE,"application/json;charset=UTF-8")
                .header(HttpHeaders.AUTHORIZATION, getAuthorisationHeader("user2","user"))
                .exchange().expectStatus().is4xxClientError();
    }

    @Test
    void sendRequest() {
        webClient.put().uri("/api/requests/2/send")
                .header(HttpHeaders.CONTENT_TYPE,"application/json;charset=UTF-8")
                .header(HttpHeaders.AUTHORIZATION, getAuthorisationHeader("user","user"))
                .exchange().expectStatus().isOk();

        webClient.put().uri("/api/requests/2/send")
                .header(HttpHeaders.CONTENT_TYPE,"application/json;charset=UTF-8")
                .header(HttpHeaders.AUTHORIZATION, getAuthorisationHeader("admin","admin"))
                .exchange().expectStatus().isForbidden();

        webClient.put().uri("/api/requests/2/send")
                .header(HttpHeaders.CONTENT_TYPE,"application/json;charset=UTF-8")
                .header(HttpHeaders.AUTHORIZATION, getAuthorisationHeader("operator","operator"))
                .exchange().expectStatus().isForbidden();
    }

    @Test
    void acceptRequest() {
        webClient.put().uri("/api/requests/3/accept")
                .header(HttpHeaders.CONTENT_TYPE,"application/json;charset=UTF-8")
                .header(HttpHeaders.AUTHORIZATION, getAuthorisationHeader("operator","operator"))
                .exchange().expectStatus().isOk();

        webClient.put().uri("/api/requests/3/accept")
                .header(HttpHeaders.CONTENT_TYPE,"application/json;charset=UTF-8")
                .header(HttpHeaders.AUTHORIZATION, getAuthorisationHeader("admin","admin"))
                .exchange().expectStatus().isForbidden();

        webClient.put().uri("/api/requests/3/accept")
                .header(HttpHeaders.CONTENT_TYPE,"application/json;charset=UTF-8")
                .header(HttpHeaders.AUTHORIZATION, getAuthorisationHeader("user","user"))
                .exchange().expectStatus().isForbidden();
    }

    @Test
    void rejectRequest() {
        webClient.put().uri("/api/requests/4/reject")
                .header(HttpHeaders.CONTENT_TYPE,"application/json;charset=UTF-8")
                .header(HttpHeaders.AUTHORIZATION, getAuthorisationHeader("operator","operator"))
                .exchange().expectStatus().isOk();

        webClient.put().uri("/api/requests/4/reject")
                .header(HttpHeaders.CONTENT_TYPE,"application/json;charset=UTF-8")
                .header(HttpHeaders.AUTHORIZATION, getAuthorisationHeader("admin","admin"))
                .exchange().expectStatus().isForbidden();

        webClient.put().uri("/api/requests/4/reject")
                .header(HttpHeaders.CONTENT_TYPE,"application/json;charset=UTF-8")
                .header(HttpHeaders.AUTHORIZATION, getAuthorisationHeader("user","user"))
                .exchange().expectStatus().isForbidden();
    }

    @Test
    void getSendedRequest() {

        FluxExchangeResult<String> body = webClient.get().uri("/api/requests/getSendedRequests")
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, getAuthorisationHeader("operator","operator"))
                .exchange().expectStatus().isOk().returnResult(String.class);
        if (body.getResponseBodyContent()!= null)
            System.out.println(new String(body.getResponseBodyContent()));

        webClient.get().uri("/api/requests/getSendedRequests")
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, getAuthorisationHeader("user","user"))
                .exchange().expectStatus().isForbidden();

        webClient.get().uri("/api/requests/getSendedRequests")
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, getAuthorisationHeader("admin","admin"))
                .exchange().expectStatus().isForbidden();
    }

    private String getAuthorisationHeader(String userName, String password) {
        return "basic " + Base64Utils.encodeToString((userName + ":" + password).getBytes());
    }

    private String getRandomMessage(String messageTemplate) {
        return String.format(messageTemplate,createRandomString(55), createRandomString(15));
    }

    private String createRandomString(int length) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }

}