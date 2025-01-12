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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;

@WebFluxTest
@Import({UserRouter.class, DataHandler.class})
class UserRouterTestChatGpt {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        Mockito.reset(userRepository);
    }

    @Test
    void testGetUserById() {
        User user = new User("John", "Doe", "johndoe");
        user.setId(1L);
        Mockito.when(userRepository.findById(1L)).thenReturn(Mono.just(user));

        webTestClient.get().uri("/user/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .isEqualTo(user);

        Mockito.verify(userRepository, Mockito.times(1)).findById(1L);
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

    @Test
    void testSaveData() {
        User user = new User("John", "Doe", "johndoe");
        Mockito.when(userRepository.save(any(User.class))).thenReturn(Mono.just(user));

        webTestClient.post().uri("/user")
                .bodyValue(user)
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .isEqualTo(user);

        Mockito.verify(userRepository, Mockito.times(1)).save(any(User.class));
    }
}
