package az.sanan.garibli.webfluxsprest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
public class WebfluxSpRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebfluxSpRestApplication.class, args);
    }

}
