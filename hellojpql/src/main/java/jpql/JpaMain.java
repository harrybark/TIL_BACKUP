package jpql;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("Harry Park");
            member.setAge(30);
            em.persist(member);
            /*
            for ( int i = 1 ; i < 11 ; i+=1 ) {
                member = new Member();
                member.setUsername("Harry Park" + i);
                member.setAge(20 + i);
                member.setTeam(team);
                em.persist(member);
            }
            */

            em.flush();
            em.clear();

            // Type이 명확한 경우
            String queryString = "select m from Member m where m.username = :username";
            TypedQuery<Member> memberTypedQuery = em.createQuery(
                    queryString,
                    Member.class);
            memberTypedQuery.setParameter("username", "Harry Park");
            List<Member> memberList = memberTypedQuery.getResultList();

            // Type이 불명확한 경우
            Query memberQuery = em.createQuery(
                    "select m.username, m.age from Member m");
            System.out.println("------------------------------------------------------------------------");
            memberList.stream().forEach(member1 -> System.out.println(member1.getUsername()));

            Member member1 = memberList.get(0);
            member1.setAge(28);

            System.out.println("------------------------------------------------------------------------");

            em.flush();
            em.clear();

            // Object[]로 조회
            List<Object[]> resultList = em.createQuery("select m.username, m.age from Member m").getResultList();

            // new 로 조회
            List<MemberDto> resultList1 = em.createQuery(
                    "select new jpql.MemberDto(m.username, m.age) from Member m",
                        MemberDto.class)
                    .getResultList();

            em.flush();
            em.clear();
            System.out.println("------------------------------------------------------------------------");

            em.flush();
            em.clear();
            // 페이징
            List<Member> members = em.createQuery("select m from Member m order by m.age desc", Member.class)
                    .setFirstResult(1)
                    .setMaxResults(10)
                    .getResultList();

            System.out.println(members.size());
            System.out.println(members);

            System.out.println("------------------------------------------------------------------------");
            em.flush();
            em.clear();
            // 조인
            String query = "select m from Member m inner join m.team t order by m.age desc";
            List<Member> joinMember = em.createQuery(query , Member.class)
                    .getResultList();
            System.out.println(joinMember.size());
            System.out.println(joinMember);

            // left
            query = "select m from Member m left outer join m.team t order by m.age desc";

            // 세타 조인
            query = "select m from Member m, Team t where m.username = t.name order by m.age desc";
            List<Member> ceta = em.createQuery(query , Member.class)
                    .getResultList();
            System.out.println(ceta.size());
            System.out.println(ceta);

            // 1. Join 대상 필터링
            query = "select m from Member m left join m.team t on t.name = 'teamA'";
            List<Member> joinOn = em.createQuery(query , Member.class)
                    .getResultList();
            System.out.println(joinOn.size());
            System.out.println(joinOn);

            // 2. 연관관계 없은 엔티티 외부 조인
            query = "select m from Member m left join Team t on m.username = t.name";

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
