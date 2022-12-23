package me.whiteship.demojpa.post;

import me.whiteship.demojpa.MyRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface PostRepository extends JpaRepository<Post, Long>,
                                        PostCustomRepository<Post>,
                                        MyRepository<Post, Long>,
                                        QuerydslPredicateExecutor<Post> {
}
