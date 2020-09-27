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

import static org.junit.jupiter.api.Assertions.fail;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    @Autowired
    private WebTestClient webClient;


    @Test
    void getUsers() {
        try {
            FluxExchangeResult<String> body = webClient.get().uri("/api/users")
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, getAuthorisationHeader("admin","admin"))
                        .exchange().expectStatus().isOk().returnResult(String.class);
            System.out.println(new String(body.getResponseBodyContent()));
            webClient.get().uri("/api/users")
                    .accept(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, getAuthorisationHeader("user","user"))
                    .exchange().expectStatus().isForbidden();

            webClient.get().uri("/api/users")
                    .accept(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, getAuthorisationHeader("operator","operator"))
                    .exchange().expectStatus().isForbidden();
        } catch (Exception exception) {
            exception.printStackTrace();
            fail();
        }
    }

    @Test
    void setOperatorRole() {
        try {
            webClient.put().uri("/api/users/1/addOperatorRole")
                    .header(HttpHeaders.AUTHORIZATION, getAuthorisationHeader("admin","admin"))
                    .exchange().expectStatus().isOk();

            webClient.put().uri("/api/users/1/addOperatorRole")
                    .header(HttpHeaders.AUTHORIZATION, getAuthorisationHeader("user","user"))
                    .exchange().expectStatus().isForbidden();

            webClient.put().uri("/api/users/1/addOperatorRole")
                    .header(HttpHeaders.AUTHORIZATION, getAuthorisationHeader("operator","operator"))
                    .exchange().expectStatus().isForbidden();

        } catch (Exception exception) {
            exception.printStackTrace();
            fail();
        }
    }

    @Test
    void removeOperatorRole() {
        try {
            webClient.put().uri("/api/users/1/addOperatorRole")
                    .header(HttpHeaders.AUTHORIZATION, getAuthorisationHeader("admin","admin"))
                    .exchange().expectStatus().isOk();

            webClient.put().uri("/api/users/1/removeOperatorRole")
                    .header(HttpHeaders.AUTHORIZATION, getAuthorisationHeader("admin","admin"))
                    .exchange().expectStatus().isOk();

            webClient.put().uri("/api/users/1/removeOperatorRole")
                    .header(HttpHeaders.AUTHORIZATION, getAuthorisationHeader("user","user"))
                    .exchange().expectStatus().isForbidden();

            webClient.put().uri("/api/users/1/removeOperatorRole")
                    .header(HttpHeaders.AUTHORIZATION, getAuthorisationHeader("operator","operator"))
                    .exchange().expectStatus().isForbidden();

            webClient.put().uri("/api/users/1/removeOperatorRole")
                    .header(HttpHeaders.AUTHORIZATION, getAuthorisationHeader("admin","admin"))
                    .exchange().expectStatus().isOk();


        } catch (Exception exception) {
            exception.printStackTrace();
            fail();
        }

    }

    private String getAuthorisationHeader(String userName, String password) {
        return "basic " + Base64Utils.encodeToString((userName + ":" + password).getBytes());
    }

}