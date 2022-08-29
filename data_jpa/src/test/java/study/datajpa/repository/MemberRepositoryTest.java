package study.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TeamRepository teamRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    public void testMember() throws Exception {
        // given
        Member member = new Member("HARRY");

        // when
        Member savedMember = memberRepository.save(member);

        Member findMember = memberRepository.findById(savedMember.getId()).orElseThrow(Exception::new);

        // then
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    public void 기본_CRUD() throws Exception {
        // given
        Member member1 = new Member("HARRY1");
        Member member2 = new Member("HARRY2");
        memberRepository.save(member1);
        memberRepository.save(member2);

        // when
        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();

        findMember1.setUsername("member!!!");

        List<Member> all = memberRepository.findAll();
        long count = memberRepository.count();

        memberRepository.delete(member1);
        memberRepository.delete(member2);

        long deletedCount = memberRepository.count();
        // then
        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);
        assertThat(all.size()).isEqualTo(2);
        assertThat(count).isEqualTo(2);
        assertThat(deletedCount).isEqualTo(0);
    }

    @Test
    public void findByUsernameAndAgeGreaterThan() throws Exception {
        // given
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);

        memberRepository.save(m1);
        memberRepository.save(m2);

        // when
        List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("AAA", 15);
        // then
        assertThat(result.get(0).getUsername()).isEqualTo("AAA");
        assertThat(result.get(0).getAge()).isEqualTo(20);
        assertThat(result.size()).isEqualTo(1);

    }

    @Test
    public void findHelloBy() {
        List<Member> helloBy = memberRepository.findHelloBy();
        for (Member member : helloBy) {
            System.out.println(helloBy);
        }
    }

    @Test
    public void 네임드쿼리_테스트() throws Exception {
        // given
        Member member = new Member("Harry", 20);
        memberRepository.save(member);

        // when
        List<Member> findMember = memberRepository.findByUsername("Harry");

        // then
        assertThat(findMember.get(0).getUsername()).isEqualTo("Harry");
    }

    @Test
    public void findMemberDto() throws Exception {
        // given

        Team t1 = new Team("TEAM A");
        teamRepository.save(t1);

        Member m1 = new Member("AAA", 10, t1);
        Member m2 = new Member("BBB", 20, t1) ;
        memberRepository.save(m1);
        memberRepository.save(m2);

        // when
        List<MemberDto> findMemberDto = memberRepository.findMemberDto();

        // then
        for (MemberDto memberDto : findMemberDto) {
            System.out.println("dto : " + memberDto);
        }
    }

    @Test
    public void findByNames() throws Exception {
        // given
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 20) ;
        memberRepository.save(m1);
        memberRepository.save(m2);

        // when
        List<Member> result = memberRepository.findByNames(Arrays.asList("AAA", "BBB"));
        // then
        for (Member member : result) {
            System.out.println("member : " + member);
        }
    }

    @Test
    public void 반환타입_테스트() throws Exception {
        // given
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 20) ;
        memberRepository.save(m1);
        memberRepository.save(m2);

        // when
        List<Member> findListResult = memberRepository.findListByUsername("AAA");

        Member findMember = memberRepository.findMemberByUsername("AAA");

        Optional<Member> findOptional = memberRepository.findOptionalByUsername("BBB");

        // then
        System.out.println(findOptional.get());

    }

    @Test
    public void findByPage() throws Exception {
        // given
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 10));

        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));
        // when
        Page<Member> memberPage = memberRepository.findByAge(10, pageRequest);

        Page<MemberDto> toMap = memberPage.map(member -> new MemberDto(member.getId(), member.getUsername(), member.getTeam().getName()));
        Slice<Member> slicePage = memberRepository.findSliceByAge(10, pageRequest);

        // then
        List<Member> content = memberPage.getContent();
        long totalElements = memberPage.getTotalElements();

        assertThat(content.size()).isEqualTo(3);
        assertThat(memberPage.getTotalElements()).isEqualTo(5);
        assertThat(memberPage.getNumber()).isEqualTo(0);
        assertThat(memberPage.getTotalPages()).isEqualTo(2);
        assertThat(memberPage.isFirst()).isTrue();
        assertThat(memberPage.hasNext()).isTrue();
    }
    
    @Test
    public void bulk_실행한다() throws Exception {
        // given
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 19));
        memberRepository.save(new Member("member3", 20));
        memberRepository.save(new Member("member4", 21));
        memberRepository.save(new Member("member5", 40));
        // when
        int resultCount = memberRepository.bulkAgePlus(20);
        /*
        영속성 컨텍스트에는 반영이 안되기 때문에 flush, clear를 해준다.
        em.flush();
        em.clear();
        */

        // then
        assertThat(resultCount).isEqualTo(3);
        
    }

    @Test
    public void findMemberLazy() throws Exception {
        // given
        //member1 -> teamA
        //member2 -> teamB

        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");

        teamRepository.save(teamA);
        teamRepository.save(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 10, teamB);
        memberRepository.save(member1);
        memberRepository.save(member2);

        em.flush();
        em.clear();

        // when
        List<Member> members = memberRepository.findAll();

        for (Member member : members) {
            System.out.println("member = " + member.getUsername());
            System.out.println("member.teamClass = " + member.getTeam().getClass());
            System.out.println("team = " + member.getTeam().getName());
        }

        System.out.println("----------------------------");
        List<Member> memberFetchJoin = memberRepository.findMemberFetchJoin();
        for (Member member : memberFetchJoin) {
            System.out.println("member = " + member.getUsername());
            System.out.println("member.teamClass = " + member.getTeam().getClass());
            System.out.println("team = " + member.getTeam().getName());
        }

        List<Member> memberGraphList
                = memberRepository.findEntityGraphByUsername("member1");
        for (Member member : memberGraphList) {
            System.out.println("member = " + member.getUsername());
            System.out.println("member.teamClass = " + member.getTeam().getClass());
            System.out.println("team = " + member.getTeam().getName());
        }
        // then
    }

    @Test
    public void queryHint() throws Exception {
        // given
        Member member1 = new Member("member1", 10);
        memberRepository.save(member1);
        em.flush();
        em.clear();

        // when
        Member findMember = memberRepository.findReadOnlyByUsername("member1");
        findMember.setUsername("member2");

        em.flush();

        // then

    }

    @Test
    public void custom_실행한다() throws Exception {
        // given
        List<Member> memberCustom = memberRepository.findMemberCustom();

        // when
        for (Member member : memberCustom) {
            System.out.println("member = " + member);
        }

        // then

    }

    @Test
    public void 명세_실행() throws Exception {
        // given
        Team teamA = new Team("teamA");
        em.persist(teamA);

        Member m1 = new Member("m1", 0, teamA);
        Member m2 = new Member("m2", 0, teamA);

        em.persist(m1);
        em.persist(m2);

        em.flush();
        em.clear();

        // when
        Specification<Member> spec = MemberSpec.uesrname("m1").and(MemberSpec.teamName("teamA"));
        List<Member> findAll = memberRepository.findAll(spec);

        // then
        Assertions.assertThat(findAll.size()).isEqualTo(1);
    }
}