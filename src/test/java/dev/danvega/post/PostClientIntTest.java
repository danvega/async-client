package dev.danvega.post;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostClientIntTest {

    @Autowired
    PostClient postClient;

    @Test
    void shouldFindAllPosts() {
        Flux<Post> posts = postClient.findAll();
        StepVerifier.create(posts)
                .expectNextCount(100)
                .verifyComplete();
    }

}
