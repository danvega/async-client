package dev.danvega.post;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostClientTest {

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    private PostClient postClient;

    @BeforeEach
    void setUp() {
        postClient = new PostClient(webClient);
    }

    @Test
    void testFindAllPosts() {
        var hello = new Post(1, 1, "Hello, World", "This is my first post!");
        var goodbye = new Post(1, 2, "Goodbye, World!", "This is my last post");

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri("/posts")).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToFlux(Post.class)).thenReturn(Flux.just(hello, goodbye));

        Flux<Post> result = postClient.findAll();

        StepVerifier.create(result)
                .expectNext(hello)
                .expectNext(goodbye)
                .verifyComplete();
    }

}