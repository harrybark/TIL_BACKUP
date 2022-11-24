package me.whiteship.demospringdata;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Test
    @Rollback(value = false)
    @DisplayName("[CRUD_TEST]")
    public void PostRepository를_확인한다() throws Exception {
        // given
        Post post = new Post();
        post.setTitle("hello Springboot common");

        Comment comment = new Comment();

        assertNull(post.getId());
        // when
        Post newPost = postRepository.save(post);

        // then
        assertNotNull(newPost.getId());
        List<Post> posts = postRepository.findAll();
        assertEquals(1, posts.size());
        assertEquals(post, posts.get(0));

        Page<Post> listPage = postRepository.findAll(PageRequest.of(0, 10));
        assertEquals(listPage.getTotalElements(), 1);
        assertEquals(listPage.getNumber(), 0);
        assertEquals(listPage.getSize(), 10);
        assertEquals(listPage.getNumberOfElements(), 1);

        Page<Post> page = postRepository.findByTitleContains("Spring", PageRequest.of(0, 10));
        assertEquals(page.getTotalElements(), 1);
        assertEquals(page.getNumber(), 0);
        assertEquals(page.getSize(), 10);
        assertEquals(page.getNumberOfElements(), 1);

        long springCnt = postRepository.countByTitleContains("spring");
        assertEquals(springCnt, 1);
    }
}