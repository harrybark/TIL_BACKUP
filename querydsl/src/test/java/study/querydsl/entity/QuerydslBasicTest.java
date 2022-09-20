package study.querydsl.entity;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static study.querydsl.entity.QMember.member;
import static study.querydsl.entity.QTeam.team;

@SpringBootTest
@Transactional
class QuerydslBasicTest {

    @Autowired
    EntityManager em;

    JPAQueryFactory queryFactory;

    @BeforeEach
    public void before() {
        queryFactory = new JPAQueryFactory(em);

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
    }
    
    @Test
    public void startJPQL() throws Exception {
        // given

        // when
        // member1 검색
        String qlString =
                "select m from Member m " +
                "where m.username = :username";
        Member findByJPQL = em.createQuery(qlString, Member.class)
                .setParameter("username", "member1")
                .getSingleResult();

        // then
        assertThat(findByJPQL.getUsername()).isEqualTo("member1");
    }
    
    @Test
    public void startQuerydsl() throws Exception {
        // given
        QMember m = new QMember("m");

        // when
        Member findMember = queryFactory
                .select(m)
                .from(m)
                .where(m.username.eq("membrer1"))
                .fetchOne();

        // then
        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    public void search() throws Exception {
        // given

        // when
        Member findMember = queryFactory.selectFrom(QMember.member)
                .where(QMember.member.username.eq("member1").and(QMember.member.age.eq(10)))
                .fetchOne();

        // then
        assertThat(findMember.getUsername()).isEqualTo("member1");

    }
    
    @Test
    public void resultFetch() throws Exception {
        // given
        
        // when
        List<Member> fetch = queryFactory
                .selectFrom(member)
                .fetch();

        Member fetchOne = queryFactory
                .selectFrom(QMember.member)
                .fetchOne();

        Member fetchFirst = queryFactory
                .selectFrom(QMember.member)
                .fetchFirst();
        // 위와 동일함
        //queryFactory.selectFrom(member).limit(1).fetchOne();

        QueryResults<Member> fetchResults = queryFactory
                .selectFrom(member)
                .fetchResults();

        long total = fetchResults.getTotal();
        List<Member> fetchList = fetchResults.getResults();

        long fetchCount = queryFactory
                .selectFrom(member)
                .fetchCount();
        
        // then
        
    }

    /***
     * 회원 정렬 순서
     * 1. 회원 나이 내림차순
     * 2. 회원 이름 올림차순
     * 단, 회원 이름이 없으면 마지막에 출력 (null last)
     * @throws Exception
     */
    @Test
    public void sort() throws Exception {
        // given

        em.persist(new Member(null, 100));
        em.persist(new Member("member5", 100));
        em.persist(new Member("member6", 100));

        em.flush();
        em.clear();
        // when
        List<Member> memberList = queryFactory
                .selectFrom(member)
                .where(member.age.eq(100))
                .orderBy(member.age.desc(), member.username.asc().nullsLast()
                )
                .fetch();

        // then
        assertThat(memberList.get(0).getUsername()).isEqualTo("member5");
        assertThat(memberList.get(1).getUsername()).isEqualTo("member6");
        assertThat(memberList.get(2).getUsername()).isNull();
    }

    @Test
    public void paging1() throws Exception {
        // given

        // when
        List<Member> fetch = queryFactory
                .selectFrom(member)
                .orderBy(member.username.desc())
                .offset(0)
                .limit(2)
                .fetch();

        QueryResults<Member> memberQueryResults = queryFactory
                .selectFrom(member)
                .orderBy(member.username.desc())
                .offset(0)
                .limit(2)
                .fetchResults();

        // then
        assertThat(fetch.size()).isEqualTo(2);

        assertThat(memberQueryResults.getTotal()).isEqualTo(4);
        assertThat(memberQueryResults.getLimit()).isEqualTo(2);
        assertThat(memberQueryResults.getOffset()).isEqualTo(1);
        assertThat(memberQueryResults.getResults().size()).isEqualTo(2);

    }

    @Test
    public void aggregation() throws Exception {
        // given

        // when
        List<Tuple> fetchAggregation = queryFactory
                .select(member.count(),
                        member.age.sum(),
                        member.age.avg(),
                        member.age.max(),
                        member.age.min()
                )
                .from(member)
                .fetch();

        Tuple tuple = fetchAggregation.get(0);

        // then
        assertThat(tuple.get(member.count())).isEqualTo(4);
        assertThat(tuple.get(member.age.sum())).isEqualTo(100);
        assertThat(tuple.get(member.age.avg())).isEqualTo(25);
        assertThat(tuple.get(member.age.max())).isEqualTo(40);
        assertThat(tuple.get(member.age.min())).isEqualTo(10);
    }

    /***
     * 팀의 이름과 각 팀의평균 연령을 구해라.
     * @throws Exception
     */
    @Test
    public void 그룹핑() throws Exception {
        // given

        // when
        List<Tuple> fetch = queryFactory
                .select(team.name,
                        member.age.avg()
                )
                .from(member)
                .join(member.team, team)
                .groupBy(team.name)
                .fetch();

        // then
        assertThat(fetch.get(0).get(team.name)).isEqualTo("teamA");
        assertThat(fetch.get(1).get(team.name)).isEqualTo("teamB");

        assertThat(fetch.get(0).get(member.age.avg())).isEqualTo(15);
        assertThat(fetch.get(1).get(member.age.avg())).isEqualTo(35);
    }

    @Test
    public void 조인() throws Exception {
        // given

        // when
        List<Member> result = queryFactory
                .selectFrom(member)
                .join(member.team, team)
                //.leftJoin(member.team, team)
                .where(team.name.eq("teamA"))
                .fetch();

        // then
        assertThat(result)
                .extracting("username")
                .containsExactly("member1", "member2");
    }

    @Test
    public void 세타조인() throws Exception {
        // given
        em.persist(new Member("teamA"));
        em.persist(new Member("teamB"));

        em.flush();
        em.clear();

        // when
        List<Member> memberList = queryFactory
                .select(member)
                .from(member, team)
                .where(member.username.eq(team.name))
                .fetch();
        // then
        assertThat(memberList)
                .extracting("username")
                .containsExactly("teamA", "teamB");
    }


    /***
     * 예) 회원과 팀을 조인하면서, 팀 이름이 teamA인 팀만 조인, 회원은 모두 조회
     * @throws Exception
     */
    @Test
    public void join_on_filtering() throws Exception {
        // given
        
        // when
        List<Tuple> result = queryFactory
                .select(member, team)
                .from(member)
                .leftJoin(member.team, team)
                .on(team.name.eq("teamA"))
                .fetch();

        // then
        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
        }
        
    }

    /***
     * 연관관계 없음
     * 회원의 이름이 팀 이름과 같은 대상 외부 조인
     * @throws Exception
     */
    @Test
    public void 조인_연관관계_없는경우() throws Exception {
        // given
        em.persist(new Member("teamA"));
        em.persist(new Member("teamB"));
        em.persist(new Member("teamC"));

        em.flush();
        em.clear();

        // when
        List<Tuple> result = queryFactory
                .select(member, team)
                .from(member)
                .leftJoin(team).on(member.username.eq(team.name))
                .fetch();

        // then
        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
        }
    }
}