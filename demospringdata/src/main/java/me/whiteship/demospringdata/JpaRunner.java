package me.whiteship.demospringdata;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
@Transactional
public class JpaRunner implements ApplicationRunner {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    PostRepository postRepository;

    @Autowired
    HarryPark harryPark;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("=================");
        System.out.println(harryPark.getName());
        System.out.println("=================");

        //selectByJPQL();
        //selectPostCriteria();
        //selectPostCriteria();
        useRepository(postRepository);

        //Session session = entityManager.unwrap(Session.class);
        //createPostMethod(session);
        //selectPostData(session);

        /*
        Account account = new Account();
        account.setUsername("greytomato2");
        account.setPassword("harry");

        Study study = new Study();
        study.setName("Spring Data JPA");

        account.addStudy(study);

        session.save(account);
        session.save(study);

        //entityManager.persist(account);
        Account loadAccount = session.load(Account.class, account.getId());
        loadAccount.setUsername("GreyTomato");
        System.out.println("=============================================");
        System.out.println(loadAccount.getUsername());
        System.out.println("=============================================");
        */
    }
    private void useRepository(PostRepository postRepository) {
        List<Post> all = postRepository.findAll();
        all.forEach(System.out::println);

    }
    private void selectPostNamedQuery() {
        List<Post> posts = entityManager.createNativeQuery("SELECT p FROM Post p", Post.class).getResultList();
        posts.forEach(System.out::println);
    }

    private void selectPostCriteria() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Post> query = builder.createQuery(Post.class);
        Root<Post> root = query.from(Post.class);
        query.select(root);

        List<Post> resultList = entityManager.createQuery(query).getResultList();
        resultList.forEach(System.out::println);
    }

    private void selectByJPQL() {
        String query = "SELECT p FROM Post AS p";
        TypedQuery<Post> postTypedQuery = (TypedQuery<Post>) entityManager.createQuery(query, Post.class);
        List<Post> resultList = postTypedQuery.getResultList();
        resultList.forEach(System.out::println);
    }

    private void selectPostData(Session session) {
        Post post = session.get(Post.class, 1L);
        //Comment comment = session.get(Comment.class, 2L);
        System.out.println("=============================================");
        System.out.println(post.getTitle());
        post.getComments().forEach(c -> System.out.println(c.getComment()));
        System.out.println("=============================================");
    }

    public void createPostMethod(Session session) {
        Post post = new Post();
        post.setTitle("Spring Data Jpa");

        Comment comment = new Comment();
        comment.setComment("study Jpa");
        post.addComment(comment);

        Comment comment1 = new Comment();
        comment1.setComment("study too!");
        post.addComment(comment1);

        session.save(post);
    }
}
