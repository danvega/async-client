package dev.danvega.post;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostControllerIntTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    void shouldReturnAllPosts() {
        this.webTestClient
                .get()
                .uri("/posts") // the base URL is already configured for us
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.length()").isEqualTo(100)
                .jsonPath("$[0].userId").isNotEmpty()
                .jsonPath("$[0].id").isNotEmpty()
                .jsonPath("$[0].title").isNotEmpty()
                .jsonPath("$[0].body").isNotEmpty();
    }

    @Test
    void shouldReturnSinglePost() {
        this.webTestClient
                .get()
                .uri("/posts/1") // the base URL is already configured for us
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.userId").isNotEmpty()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.title").isNotEmpty()
                .jsonPath("$.body").isNotEmpty();
    }

}
