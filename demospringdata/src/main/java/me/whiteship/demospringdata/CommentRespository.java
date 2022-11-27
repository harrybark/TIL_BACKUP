package me.whiteship.demospringdata;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.List;
import java.util.stream.Stream;

@RepositoryDefinition(domainClass = Comment.class, idClass = Long.class)
public interface CommentRespository extends MyRepository<Comment, Long> {

    /*
    Comment save(Comment comment);

    List<Comment> findAll();
     */

    List<Comment> findByCommentContainsIgnoreCase(String keyword);
    List<Comment> findByCommentContainsIgnoreCaseAndLikeCountGreaterThan(String keyword, int likecount);

    List<Comment> findByCommentContainsIgnoreCaseOrderByLikeCountDesc(String keyword);
    Page<Comment> findByCommentContainsIgnoreCase(String keyword, Pageable pageable);

    Stream<Comment> findUsingStreamByCommentContainsIgnoreCase(String keyword, Pageable pageable);

}
