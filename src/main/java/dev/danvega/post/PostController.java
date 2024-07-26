package dev.danvega.post;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostClient postClient;

    public PostController(PostClient postClient) {
        this.postClient = postClient;
    }

    @GetMapping("")
    public Flux<Post> findAll() {
        return  postClient.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Post> findById(@PathVariable Integer id) {
        return postClient.findById(id);
    }
}
