package me.whiteship.demojpa.post;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;
    
    @Test
    public void 빈확인() throws Exception {
        // given
        Post post = new Post();
        post.setTitle("Hibernate");

        // when
        postRepository.save(post);
        List<Post> myPost = postRepository.findMyPost();
        postRepository.delete(post);
        postRepository.flush();
        // then
        
    }
}