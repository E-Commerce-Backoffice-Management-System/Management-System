package com.managementSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagementSystemApplication.class, args);
    }

}
