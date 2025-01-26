package az.sanan.garibli.webfluxsprest.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserRouterWebTest {

    @Autowired
    private WebTestClient testClient;

    @Test
    public void shouldReturnRecentTacos() {
        testClient.get().uri("/users")
                .accept(MediaType.APPLICATION_JSON).exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[?(@.id == '1')].name")
                .isEqualTo("Sara")
                .jsonPath("$[?(@.id == '3')].name")
                .isEqualTo("Sanan")
                .jsonPath("$[?(@.id == '4')].name")
                .isEqualTo("Leyla");
    }
}