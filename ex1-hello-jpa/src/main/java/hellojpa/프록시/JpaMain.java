package hellojpa.프록시;

import hellojpa.Team;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PROXY");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();;
        try {
            Member member = new Member();
            member.setUsername("Harry Park");
            em.persist(member);

            Member refMember = em.getReference(Member.class, member.getId());
            System.out.println("refMember = " + refMember.getClass()); // Proxy

            // Proxy 인스턴스의 초기화 여부
            System.out.println("isLoaded = " + emf.getPersistenceUnitUtil().isLoaded(refMember));

            // 프록시 클래스 확인
            System.out.println("Proxy class = " + refMember.getClass());

            // 프록시 강제 초기화
            Hibernate.initialize(refMember);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }
}
