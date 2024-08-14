package ru.mf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableJpaAuditing
public class C3poApplication {
    public static void main(String[] args) {
        SpringApplication.run(C3poApplication.class, args);
    }

}