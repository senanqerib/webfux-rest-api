package az.sanan.garibli.webfluxsprest.controllers;

import az.sanan.garibli.webfluxsprest.handlers.DataHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class UserRouter {

    @Bean
    public RouterFunction<ServerResponse> routes(DataHandler handler) {
        return route()
                .GET("/user/{id}", handler::getUserById)
                .build()
                .andRoute(POST("user"), handler::saveData)
                .andRoute(GET("users"), users -> handler.getAllUsers());

    }
}
