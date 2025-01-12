package az.sanan.garibli.webfluxsprest.controllers;

import az.sanan.garibli.webfluxsprest.entities.User;
import az.sanan.garibli.webfluxsprest.handlers.DataHandler;
import az.sanan.garibli.webfluxsprest.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@WebFluxTest
@Import({UserRouter.class, DataHandler.class})
class UserRouterTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private UserRepository userRepository;

    User[] users;

    @BeforeEach
    void setUp() {
        Mockito.reset(userRepository);
        users = new User[]{
                new User("John", "Travolta", "john.travolta@gmail.com"),
                new User("Jane", "Smith", "jane.smith@gmail.com"),
                new User("Jessica", "Smith", "jessika.smith@gmail.com"),
                new User("Jack", "Travolta", "jack.travolta@gmail.com"),
                new User("Jill", "Paddington", "jill.paddington@gmail.com")};
    }

    @Test
    void testGetAllUsersMine() {
        User user1 = new User("John", "Doe", "johndoe");
        User user2 = new User("Jane", "Doe", "janedoe");

        Flux<User> userFlux = Flux.just(user1, user2);
        when(userRepository.findAll()).thenReturn(userFlux);
        DataHandler dataHandler = Mockito.mock(DataHandler.class);
        Mono<ServerResponse> serverResponseMono = ServerResponse
                .ok()
                .bodyValue(users);

        when(dataHandler.getAllUsers()).thenReturn(serverResponseMono);

        webTestClient.get().uri("/users")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$").isNotEmpty()
                .jsonPath("$[0].id").isEqualTo(users[0].getId());
    }


    @Test
    void testGetAllUsers() {
        User user1 = new User("John", "Doe", "johndoe");
        User user2 = new User("Jane", "Doe", "janedoe");
        Mockito.when(userRepository.findAll()).thenReturn(Flux.just(user1, user2));


        webTestClient.get().uri("/users")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(User.class)
                .hasSize(2);

        Mockito.verify(userRepository, Mockito.times(1)).findAll();
    }
}