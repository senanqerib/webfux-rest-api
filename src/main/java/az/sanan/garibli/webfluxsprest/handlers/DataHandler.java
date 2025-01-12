package az.sanan.garibli.webfluxsprest.handlers;

import az.sanan.garibli.webfluxsprest.entities.User;
import az.sanan.garibli.webfluxsprest.repositories.UserRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class DataHandler {
    private final UserRepository userRepository;

    public DataHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<ServerResponse> getUserById(ServerRequest request) {
        // Extract the 'id' from the request (if needed, e.g., from path variable)
        Long id = Long.parseLong(request.pathVariable("id"));

        // Call the repository to fetch data reactively
        return userRepository.findById(id)
                .flatMap(data -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(data)) // Return the data in the response
                .switchIfEmpty(ServerResponse.notFound().build())
                .log(); // Handle not-found case
    }

    public Mono<ServerResponse> getAllUsers() {
        return ServerResponse.ok()
                .body(userRepository.findAll().take(3), User.class);
    }

    public Mono<ServerResponse> saveData(ServerRequest request) {
        // Extract the User object reactively from the request body
        return request.bodyToMono(User.class)
                .flatMap(userRepository::save) // Save the user reactively
                .flatMap(savedUser -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(savedUser)) // Return the saved user in the response
                .onErrorResume(e -> ServerResponse.badRequest()
                        .bodyValue("Failed to save user: " + e.getMessage()))
                .log(); // Handle errors gracefully
    }

}
