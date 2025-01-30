package com.xandy.spring_rest;


import com.xandy.spring_rest.dto.SpotCreateDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/spot/spot-create.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/spot/spot-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class SpotIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public void createSpotStatus201() {
        testClient
                .post()
                .uri("api/v1/parking_spot")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@gmail.com", "123456"))
                .bodyValue(new SpotCreateDTO("A-05", "FREE"))
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().exists("Location");
    }
}
