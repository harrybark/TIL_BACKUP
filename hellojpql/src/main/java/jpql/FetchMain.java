package jpql;

import javax.persistence.*;
import java.util.List;

public class FetchMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Team teamA = new Team();
            teamA.setName("팀A");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("팀B");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("Harry Park1");
            member1.setAge(30);
            member1.setTeam(teamA);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("Harry Park2");
            member2.setAge(30);
            member2.setTeam(teamA);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("Harry Park2");
            member3.setAge(30);
            member3.setTeam(teamB);
            em.persist(member3);

            em.flush();
            em.clear();

            String query = "select m from Member m join fetch m.team";
            List<Member> memberList = em.createQuery(query, Member.class).getResultList();

            for (Member m : memberList) {
                System.out.println("member : " + m.getUsername() + ", " + m.getTeam().getName());
            }

            // 컬렉션 fetch
            String query2 = "select distinct t from Team t join fetch t.members where t.name = '팀A'";
            List<Team> res = em.createQuery(query2, Team.class).getResultList();

            for (Team m : res) {
                System.out.println("member : " + m.getName() + ", " + m.getMembers().size());
                for (Member s : m.getMembers()) {
                    System.out.println("-> member : " + s.getUsername() + ", " + s.getAge());
                }
            }

            em.flush();
            em.clear();

            // 기본 키
            String enQuery;
            //enQuery = "select m from Member m where m = :member";
            // 외래 키
            enQuery = "select m from Member m where m.team = :team";
            List<Member> resultList = em.createQuery(enQuery, Member.class)
                    //.setParameter("member", member1)
                    .setParameter("team", teamA)
                    .getResultList();

            System.out.println("findMember = " + resultList);

            //
            em.flush();
            em.clear();
            List<Member> findByUsername = em.createNamedQuery("Member.findByUsername", Member.class)
                    .setParameter("username", "Harry Park1")
                    .getResultList();

            System.out.println(findByUsername);
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
