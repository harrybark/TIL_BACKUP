package me.whiteship.demojpa.post;

import me.whiteship.demojpa.MyRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, PostCustomRepository<Post>, MyRepository<Post, Long> {
}
