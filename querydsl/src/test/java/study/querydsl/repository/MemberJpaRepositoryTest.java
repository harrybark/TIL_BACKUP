package study.querydsl.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.dto.MemberSearchCondition;
import study.querydsl.dto.MemberTeamDto;
import study.querydsl.entity.Member;
import study.querydsl.entity.Team;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberJpaRepositoryTest {

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Autowired
    EntityManager em;

    @Test
    public void 기본테스트() throws Exception {
        // given
        Member member = new Member("member1", 10);
        memberJpaRepository.save(member);

        // when
        Member findMember = memberJpaRepository.findById(member.getId()).get();

        // then
        assertThat(findMember).isEqualTo(member);

        List<Member> result = memberJpaRepository.findAll();
        assertThat(result).containsExactly(member);

        List<Member> findMemberByusername = memberJpaRepository.findByUsername("member1");
        assertThat(findMemberByusername).containsExactly(member);
    }

    @Test
    public void Querydsl_조회() throws Exception {
        // given
        Member member = new Member("member1", 10);
        memberJpaRepository.save(member);

        // when
        Member findMember = memberJpaRepository.findById(member.getId()).get();

        // then
        assertThat(findMember).isEqualTo(member);

        List<Member> result = memberJpaRepository.findAll_Querydsl();
        assertThat(result).containsExactly(member);

        List<Member> findMemberByusername = memberJpaRepository.findByUsername_Querydsl("member1");
        assertThat(findMemberByusername).containsExactly(member);
    }

    @Test
    public void 검색테스트() throws Exception {
        // given
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
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

        // when
        MemberSearchCondition condition = new MemberSearchCondition();
        condition.setAgeGoe(35);
        condition.setAgeLoe(40);
        condition.setTeamName("teamB");

        List<MemberTeamDto> result = memberJpaRepository.searchByBuilder(condition);

        // then
        assertThat(result).extracting("username")
                .containsExactly("member4");
    }
}