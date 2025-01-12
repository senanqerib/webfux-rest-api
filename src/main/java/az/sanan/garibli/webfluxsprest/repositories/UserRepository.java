package az.sanan.garibli.webfluxsprest.repositories;

import az.sanan.garibli.webfluxsprest.entities.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface UserRepository extends ReactiveCrudRepository<User, Long> {

}