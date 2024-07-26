# Async Clients

I received the following question from a YouTube subscriber and I thought it was a good one to answer: 

<blockquote>
Could you provide a video covering asynchronous requests using WebClient or another library that can offer this? And if possible, how to do unit tests as well, as that would be very helpful!
</blockquote>

## Getting Started 

To create a new Spring Boot project head over to start.spring.io. Create a new project with the Spring WebFlux Dependency

## Post Client 

The `PostClient` contains all of the logic for communicating with a public API, [JsonPlaceholderService](https://jsonplaceholder.typicode.com/). Normally in this class I would just have Spring autowire in the DefaultWebClientBuilder 

```java
@Component
public class PostClient {

    private final WebClient webClient;

    public PostClient(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("(https://jsonplaceholder.typicode.com/")
                .build();
    }

}
```

This makes it harder to test because you're hardcoding the baseUrl here and it makes it impossible to override. A better solution is to have the `PostClient` accept
a `WebClient`. This way you can configure a bean of type WebClient in a configuration class and you can pass one in for tests. 

```java
@Bean
WebClient webClient(WebClient.Builder builder) {
    return builder.baseUrl("https://jsonplaceholder.typicode.com").build();
}
```

```java
@Component
public class PostClient {

    private final WebClient webClient;

    public PostClient(WebClient webClient) {
        this.webClient = webClient;
    }

}
```

## Writing Tests against the Post Client

These are the different tests I have written for the `PostClient` and what they are used for. 

### PostClientTest

This is a true unit test but given the nature of the WebClient's fluent API this can become a little bit tricky to mock out.

### PostClientMockWebServerTest

A better approach is to start a local HTTP server and mock the HTTP responses from the remote system. 

Thanks to [Phillip Riecks](https://x.com/rieckpil) for his wondering [Testing Resources](https://rieckpil.de/spring-webclient-for-restful-communication-setup-and-examples/). 

### PostClientIntTest

Use a live web server on a random port and call the real service.

### PostControllerIntTest

If you have a controller that calls the post client you can use the `WebTestClient`. 

The `WebTestClient` is a client for testing web servers that uses WebClient internally to perform requests while also providing a fluent API to verify responses. This client can connect to any server over HTTP, or to a WebFlux application via mock request and response objects.


