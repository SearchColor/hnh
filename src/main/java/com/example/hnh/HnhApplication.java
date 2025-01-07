package com.example.hnh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HnhApplication {

    public static void main(String[] args) {
        SpringApplication.run(HnhApplication.class, args);
    }

}
