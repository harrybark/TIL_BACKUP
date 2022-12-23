package me.whiteship.demojpa.post;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static me.whiteship.demojpa.post.QPost.post;
import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
@Import(PostRepositoryTestConfig.class)
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    ApplicationContext applicationContext;

    @Test
    public void event() throws Exception {
        // given
        Post post = new Post();
        post.setTitle("Hibernate");
        PostPublishedEvent postPublishedEvent= new PostPublishedEvent(post);

        applicationContext.publishEvent(postPublishedEvent);
        // when

        // then

    }

    @Test
    public void 빈확인() throws Exception {
        // given
        Post post = new Post();
        post.setTitle("Hibernate");

        assertThat(postRepository.contains(post)).isFalse();
        // when
        postRepository.save(post.publish());

        assertThat(postRepository.contains(post)).isTrue();

        List<Post> myPost = postRepository.findMyPost();
        postRepository.delete(post);
        postRepository.flush();
        // then
        
    }
    
    @Test
    public void crud() throws Exception {
        // given
        Post p = new Post();
        p.setTitle("Hibernate");
        postRepository.save(p.publish());

        // when
        Predicate predicate = post.title.containsIgnoreCase("Hi");
        Optional<Post> one = postRepository.findOne(predicate);

        // then
        assertThat(one).isNotEmpty();
    }
}