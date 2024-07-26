package dev.danvega.post;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class PostClient {

    private final WebClient webClient;

    public PostClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Flux<Post> findAll() {
        return webClient.get()
                .uri("/posts")
                .retrieve()
                .bodyToFlux(Post.class);
    }

    public Mono<Post> findById(Integer id) {
        return webClient.get()
                .uri("/posts/{id}", id)
                .retrieve()
                .bodyToMono(Post.class);
    }

    public Mono<Post> create(Post post) {
        return webClient.post()
                .uri("/posts")
                .bodyValue(post)
                .retrieve()
                .bodyToMono(Post.class);
    }

    public Mono<Post> update(Integer id, Post post) {
        return webClient.put()
                .uri("/posts/{id}", id)
                .bodyValue(post)
                .retrieve()
                .bodyToMono(Post.class);
    }

    public Mono<Void> delete(Integer id) {
        return webClient.delete()
                .uri("/posts/{id}", id)
                .retrieve()
                .bodyToMono(Void.class);
    }

}
