package dev.danvega;

import dev.danvega.post.Post;
import dev.danvega.post.PostClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	WebClient webClient(WebClient.Builder builder) {
		return builder.baseUrl("https://jsonplaceholder.typicode.com").build();
	}

	@Bean
	CommandLineRunner commandLineRunner(PostClient postClient) {
		return args -> {
			Flux<Post> posts = postClient.findAll();
			posts.subscribe(System.out::println);
		};
	}
}
