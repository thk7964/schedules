package com.example.schedules;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SchedulesApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchedulesApplication.class, args);
    }

}
