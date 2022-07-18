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

        Team team = new Team();
        team.setName("TEAMA");
        em.persist(team);

        Member member = new Member();
        member.setUsername("Harry Park");
        member.setTeam(team);
        em.persist(member);

        em.flush();
        em.clear();

        Member findMember = em.find(Member.class, member.getId());
        Team findTeam = findMember.getTeam();
        System.out.println("findTeam : " + findTeam.getName());

        List<Member> members = findMember.getTeam().getMembers();
        for(Member mem : members) {
            System.out.println("m = " + mem.getUsername());
        }

        tx.commit();

    }
}
