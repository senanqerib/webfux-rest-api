package az.sanan.garibli.webfluxsprest.flux.webclient;

import az.sanan.garibli.webfluxsprest.entities.User;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

public class FluxWebClient {

    public void getUsers() {
        WebClient.create()
                .get()
                .uri("http://localhost:8080/users")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(User[].class)
                .log()
                .doOnError(error -> System.err.println("Error occurred: " + error.getMessage()))
                .subscribe(System.out::println)

        ;
    }

    public static void main(String[] args) throws InterruptedException {
        FluxWebClient fluxWebClient = new FluxWebClient();
        fluxWebClient.getUsers();
    }
}
