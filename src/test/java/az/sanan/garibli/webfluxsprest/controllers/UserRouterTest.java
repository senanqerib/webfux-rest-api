package az.sanan.garibli.webfluxsprest.controllers;

import az.sanan.garibli.webfluxsprest.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserRouterTest {

    @BeforeEach
    void setUp() {
        User[] users = new User[]{
                new User("John", "Travolta", "john.travolta@gmail.com"),
                new User("Jane", "Smith", "jane.smith@gmail.com"),
                new User("Jessica", "Smith", "jessika.smith@gmail.com"),
                new User("Jack", "Travolta", "jack.travolta@gmail.com"),
                new User("Jill", "Paddington", "jill.paddington@gmail.com")};
    }

    @Test
    void routes() {
    }
}