package me.whiteship.demospringdata;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CommentRespositoryTest {

    @Autowired
    CommentRespository commentRespository;

    @Test
    public void crud() throws Exception {
        // given
        Comment comment = new Comment();
        comment.setComment("Hello Comment");
        commentRespository.save(comment);

        // when
        List<Comment> all = commentRespository.findAll();
        long count = commentRespository.count();

        Optional<Comment> optionalId = commentRespository.findById(100L);
        commentRespository.save(null);

        // then
        assertThat(all.size()).isEqualTo(1);
        assertThat(count).isEqualTo(1);

        assertThat(optionalId).isEmpty();
        Comment comment1 = optionalId.orElseThrow(IllegalArgumentException::new);

    }
    
    @Test
    public void 쿼리생성_확인() throws Exception {
        // given

        this.createComment(100, "Spring data jpa");
        this.createComment(55, "HIBERNATE SPRING");
        this.createComment(25, "JPA");

        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by( Sort.Direction.DESC, "likeCount"));

        // when
        List<Comment> res1 = commentRespository.findByCommentContainsIgnoreCase("Spring");
        List<Comment> res2 = commentRespository.findByCommentContainsIgnoreCaseAndLikeCountGreaterThan("Spring", 10);
        List<Comment> res3 = commentRespository.findByCommentContainsIgnoreCaseOrderByLikeCountDesc("Spring");
        Page<Comment> res4 = commentRespository.findByCommentContainsIgnoreCase("Spring", pageRequest);

        try(Stream<Comment> res5 = commentRespository.findUsingStreamByCommentContainsIgnoreCase("Spring", pageRequest)) {
            Comment firstComment = res5.findFirst().get();
            assertThat(firstComment.getLikeCount()).isEqualTo(100);
        }

        // then
        //assertThat(res1.size()).isEqualTo(1);
        //assertThat(res2.size()).isNotEqualTo(1);
        //assertThat(res3.size()).isEqualTo(2);
        //assertThat(res3).first().hasFieldOrPropertyWithValue("likeCount", 100);
        //assertThat(res3).first().hasFieldOrPropertyWithValue("comment", "Spring data jpa");

        assertThat(res4.getNumberOfElements()).isEqualTo(2);
        assertThat(res4.getTotalElements()).isEqualTo(2);
    }

    private void createComment(int likeCount, String title) {
        Comment comment = new Comment();
        comment.setComment(title);
        comment.setLikeCount(likeCount);
        commentRespository.save(comment);
    }
}