package com.rhinitis.projectrhinitis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ProjectRhinitisApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectRhinitisApplication.class, args);
    }

}
