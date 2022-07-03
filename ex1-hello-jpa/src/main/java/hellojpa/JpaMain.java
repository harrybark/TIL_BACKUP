package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();;

        try {
            Member member = new Member();
            /*
            member.setId(1L);
            member.setName("Harry Park");
            em.persist(member);
            */

            /*
            Member findMember = em.find(Member.class, 1L);
            findMember.setName("Modified Name Harry");
            */
            List<Member> result = em.createQuery("select m from Member m ", Member.class)
                    .setFirstResult(0)
                    .setMaxResults(5)
                    .getResultList();
            for (Member findOne : result) {
                System.out.println("Member.name = " + findOne.getName());
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
