package me.whiteship.demojpa.post;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PostRepositoryTestConfig {
    /*
    @Bean
    public PostListener postListener() {
        return new PostListener();
    }
     */

    @Bean
    public ApplicationListener<PostPublishedEvent> postListner() {
        return postPublishedEvent -> {
            System.out.println("----------------------------");
            System.out.println(postPublishedEvent.getPost().getTitle() + " is published!");
            System.out.println("----------------------------");
        };
    }
}
