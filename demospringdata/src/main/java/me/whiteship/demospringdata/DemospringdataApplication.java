package me.whiteship.demospringdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@Import(HarryRegistrator.class)
/*
@EnableJpaRepositories(queryLookupStrategy = QueryLookupStrategy.Key.CREATE_IF_NOT_FOUND)
@EnableJpaRepositories(queryLookupStrategy = QueryLookupStrategy.Key.CREATE)
@EnableJpaRepositories(queryLookupStrategy = QueryLookupStrategy.Key.USE_DECLARED_QUERY)
*/
//@EnableAsync
public class DemospringdataApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemospringdataApplication.class, args);
    }
}
