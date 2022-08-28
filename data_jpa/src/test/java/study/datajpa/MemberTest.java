package study.datajpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;
import study.datajpa.repository.MemberRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;
    @Test
    public void 엔티티_실행한다() throws Exception {
        // given
        Team teamA = new Team("team A");
        Team teamB = new Team("team B");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        em.flush();
        em.clear();

        // when
        List<Member> members = em.createQuery("SELECT m FROM Member m", Member.class).getResultList();

        for (Member member : members) {
            System.out.println("member = " + member);
            System.out.println("-> member.team = " + member.getTeam());
        }

        // then

    }

    @Test
    public void JpaEventBaseEntity() throws Exception {
        // given
        Member member = new Member("member1");
        memberRepository.save(member);

        Thread.sleep(100);
        member.setUsername("member2");
        
        em.flush(); // @PreUpdate
        em.clear();
        
        // when
        Optional<Member> findMember = memberRepository.findById(member.getId());

        // then
        System.out.println("findMember.createdDate = " + findMember.get().getCreatedDate());
        System.out.println("findMember.createdBy = " + findMember.get().getCreatedBy());
        //System.out.println("findMember.updatedDate = " + findMember.get().getUpdatedDate());
        System.out.println("findMember.lastModifiedDate = " + findMember.get().getLastModifiedDate());
        System.out.println("findMember.lastModifiedBy = " + findMember.get().getLastModifiedBy());

    }
}