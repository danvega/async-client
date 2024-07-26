package dev.danvega.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PostClientMockWebServerTest {

    private static MockWebServer mockWebServer;
    private PostClient postClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    static void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @BeforeEach
    void initialize() {
        String baseUrl = String.format("http://localhost:%s", mockWebServer.getPort());
        postClient = new PostClient(WebClient.builder().baseUrl(baseUrl).build());
    }

    @Test
    void testFindAllPosts() throws Exception {
        // Prepare test data
        var hello = new Post(1, 1, "Hello, World", "This is my first post!");
        var goodbye = new Post(1, 2, "Goodbye, World!", "This is my last post");
        String jsonResponse = objectMapper.writeValueAsString(new Post[]{hello, goodbye});

        // Mock the response
        mockWebServer.enqueue(new MockResponse()
                .setBody(jsonResponse)
                .addHeader("Content-Type", "application/json"));

        // Execute the method and verify
        StepVerifier.create(postClient.findAll())
                .expectNext(hello)
                .expectNext(goodbye)
                .verifyComplete();

        // Verify the request
        RecordedRequest recordedRequest = mockWebServer.takeRequest();
        assertEquals("GET", recordedRequest.getMethod());
        assertEquals("/posts", recordedRequest.getPath());
    }

}
