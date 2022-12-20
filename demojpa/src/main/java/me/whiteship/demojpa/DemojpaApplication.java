package me.whiteship.demojpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryImplementationPostfix = "Impl", repositoryBaseClass = MyRepositoryImpl.class)
public class DemojpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemojpaApplication.class, args);
    }

}
