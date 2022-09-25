package study.querydsl.entity;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.dto.MemberDto;
import study.querydsl.dto.QMemberDto;
import study.querydsl.dto.UserDto;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import java.util.List;

import static com.querydsl.core.types.dsl.Expressions.*;
import static com.querydsl.jpa.JPAExpressions.*;
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

    /***
     * 회원가 팀을 조인하면서, 팀 이름이 teamA인 팀만 조인, 회원은 모두 조회
     * JPQL : select m, t from Member m left join m.team t on t.name = 'teamA'
     * @throws Exception
     */
    @Test
    public void 조인대상_필터링() throws Exception {
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
     * 회원의 이름이 팀 이름과 같은 대상 외부 조인
     * @throws Exception
     */
    @Test
    public void 연관관계_없는_외부조인() throws Exception {
        // given
        em.persist(new Member("teamA"));
        em.persist(new Member("teamB"));

        em.flush();
        em.clear();

        // when
        List<Tuple> tupleList = queryFactory
                .select(member, team)
                .from(member)
                .leftJoin(team)
                .on(member.username.eq(team.name))
                .fetch();

        // then
        for (Tuple tuple : tupleList) {
            System.out.println("tuple = " + tuple);
        }
    }

    @PersistenceUnit
    EntityManagerFactory emf;

    @Test
    public void 패치조인_미적용() throws Exception {
        // given
        em.flush();
        em.clear();

        // when
        Member findMember = queryFactory
                .selectFrom(QMember.member)
                .where(QMember.member.username.eq("member1"))
                .fetchOne();

        // then
        assertThat(findMember.getUsername()).isEqualTo("member1");
        // loading / initialization 여부 확인
        boolean loaded = emf.getPersistenceUnitUtil().isLoaded(findMember.getTeam());
        assertThat(loaded).as("패치 조인 미적용").isFalse();
    }

    @Test
    public void 패치조인_적용() throws Exception {
        // given
        em.flush();
        em.clear();

        // when
        Member findMember = queryFactory
                .selectFrom(member)
                .join(member.team, team).fetchJoin()
                .where(QMember.member.username.eq("member1"))
                .fetchOne();

        // then
        assertThat(findMember.getUsername()).isEqualTo("member1");
        // loading / initialization 여부 확인
        boolean loaded = emf.getPersistenceUnitUtil().isLoaded(findMember.getTeam());
        assertThat(loaded).as("패치 조인 미적용").isTrue();
    }

    /***
     * 나이가 가장 많은 회원을 조회
     * @throws Exception
     */
    @Test
    public void 서브쿼리_eq사용() throws Exception {
        // given
        QMember memberSub = new QMember("memberSub");

        // when
        List<Member> result = queryFactory
                .selectFrom(member)
                .where(member.age.eq(
                        select(memberSub.age.max())
                                .from(memberSub)
                ))
                .fetch();

        // then
        assertThat(result).extracting("age")
                .containsExactly(40);

    }

    /***
     * 나이가 평균 이상인 회원을 조회
     * @throws Exception
     */
    @Test
    public void 서브쿼리_GOE사용() throws Exception {
        // given
        QMember memberSub = new QMember("memberSub");

        // when
        List<Member> result = queryFactory
                .selectFrom(member)
                .where(member.age.goe(
                        select(memberSub.age.avg())
                                .from(memberSub)
                ))
                .fetch();

        // then
        assertThat(result).extracting("age").as("평균 나이보다 많은 사람 조회")
                .containsExactly(30, 40);
    }

    /***
     * 나이가 평균 이상인 회원을 조회
     * @throws Exception
     */
    @Test
    public void 서브쿼리_IN사용() throws Exception {
        // given
        QMember memberSub = new QMember("memberSub");

        // when
        List<Member> result = queryFactory
                .selectFrom(member)
                .where(member.age.in(
                        select(memberSub.age)
                                .from(memberSub)
                                .where(memberSub.age.gt(10))
                ))
                .fetch();

        // then
        assertThat(result).extracting("age")
                .containsExactly(20, 30, 40);
    }

    @Test
    public void 스칼라서브쿼리() throws Exception {
        // given
        QMember memberSub = new QMember("memberSub");

        // when
        List<Tuple> result = queryFactory
                .select(member.username,
                        select(memberSub.age.avg())
                                .from(memberSub))
                .from(member)
                .fetch();

        // then
        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
        }
    }
    
    @Test
    public void CASE_구문_실행() throws Exception {
        // given

        // when
        List<String> fetch = queryFactory
                .select(member.age
                        .when(10).then("열살")
                        .when(20).then("스무살")
                        .otherwise("기타"))
                .from(member)
                .fetch();

        List<String> list = queryFactory
                .select(new CaseBuilder()
                        .when(member.age.between(0, 20)).then("0 ~ 20")
                        .when(member.age.between(21, 30)).then("21 ~ 30")
                        .otherwise("etc")
                )
                .from(member)
                .fetch();

        // then
        for (String s : fetch) {
            System.out.println("s = " + s);
        }

        System.out.println("=============== complicated case ===============");
        for (String s : list) {
            System.out.println("s = " + s);
        }
    }

    @Test
    public void 상수사용() throws Exception {
        // given

        // when
        List<Tuple> result = queryFactory
                .select(member.username, constant("A"))
                .from(member)
                .fetch();

        // then
        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
        }
    }

    @Test
    public void 문자더하기() throws Exception {
        // given

        // when
        List<String> result = queryFactory
                .select(member.username.concat("_").concat(member.age.stringValue()))
                .from(member)
                .where(member.username.eq("member1"))
                .fetch();

        // then
        for (String s : result) {
            System.out.println("s = " + s);
        }
    }

    @Test
    public void 프로젝션_단일대상() throws Exception {
        // given

        // when
        List<String> projectionOne = queryFactory
                .select(member.username)
                .from(member)
                .fetch();

        // then
        for (String s : projectionOne) {
            System.out.println("s = " + s);
        }
    }

    @Test
    public void 프로젝션_튜플() throws Exception {
        // given

        // when
        List<Tuple> result = queryFactory
                .select(member.username, member.age)
                .from(member)
                .fetch();

        // then
        for (Tuple tuple : result) {
            String username = tuple.get(member.username);
            Integer age = tuple.get(member.age);
            System.out.println("username = " + username);
            System.out.println("age = " + age);
        }
    }
    
    @Test
    public void 프로젝션_DTO_반환() throws Exception {
        // given
        
        // when

        // JPQL
        List<MemberDto> resultList = em.createQuery(
                        "select new study.querydsl.dto.MemberDto(m.username, m.age)" +
                                "from Member m", MemberDto.class)
                .getResultList();

        // Querydsl

        // 1.setter (Setter 필수)
        List<MemberDto> setter_dto = queryFactory
                .select(
                        Projections.bean(
                                MemberDto.class,
                                member.username,
                                member.age
                        )
                )
                .from(member)
                .fetch();

        // 2.field (Setter 없이 그냥 멤버 필드에 할당)
        List<MemberDto> fields_dto = queryFactory
                .select(
                        Projections.fields(
                                MemberDto.class,
                                member.username,
                                member.age
                        )
                )
                .from(member)
                .fetch();

        // 3. constructor ( Type이 맞아야함 )
        List<MemberDto> constructor_dto = queryFactory
                .select(
                        Projections.constructor(
                                MemberDto.class,
                                member.username,
                                member.age
                        )
                )
                .from(member)
                .fetch();

        // then
        for (MemberDto memberDto : setter_dto) {
            System.out.println("setter_dto = " + memberDto);
        }

        for (MemberDto memberDto : fields_dto) {
            System.out.println("fields_dto = " + memberDto);
        }

        for (MemberDto memberDto : constructor_dto) {
            System.out.println("constructor_dto = " + memberDto);
        }
    }
    
    @Test
    public void USER_DTO() throws Exception {
        // given
        QMember memberSub = new QMember("memberSub");

        // when
        List<UserDto> user_dto_projection = queryFactory
                .select(
                        Projections.fields(
                                UserDto.class,
                                member.username.as("name"),
                                // dto와 이름이 상이한 경우 아래 구문 사용
                                ExpressionUtils.as(JPAExpressions
                                        .select(memberSub.age.max())
                                        .from(memberSub), "age")
                                //member.age
                        )
                )
                .from(member)
                .fetch();
        // then
        for (UserDto memberDto : user_dto_projection) {
            System.out.println("user_dto_projection = " + memberDto);
        }
    }

    @Test
    public void 쿼리프로젝션() throws Exception {
        // given
        
        // when
        List<MemberDto> result = queryFactory
                .select(new QMemberDto(member.username, member.age))
                .from(member)
                .fetch();

        // then
        for (MemberDto memberDto : result) {
            System.out.println("memberDto : " + memberDto);
        }
    }
    
    @Test
    public void 동적쿼리_BooleanBuilder() throws Exception {
        // given
        String usernameParam = "member1";
        Integer ageParam     = 10;

        // when
        List<Member> result =  searchMemberBooleanBuilder(usernameParam, ageParam);

        // then
        assertThat(result.size()).isEqualTo(1);
    }

    private List<Member> searchMemberBooleanBuilder(String usernameParam, Integer ageParam) {

        BooleanBuilder builder = new BooleanBuilder();
        if ( usernameParam != null ) {
            builder.and(member.username.eq(usernameParam));
        }

        if ( ageParam != null ){
            builder.and(member.age.eq(ageParam));
        }

        return queryFactory
                .selectFrom(member)
                .where(builder)
                .fetch();
    }
    
    @Test
    public void 동적쿼리_whereParam() throws Exception {
        // given
        String usernameParam = "member1";
        Integer ageParam     = 10;

        // when
        List<Member> result =  searchMemberWhereParam(usernameParam, ageParam);

        // then
        assertThat(result.size()).isEqualTo(1);
    }

    private List<Member> searchMemberWhereParam(String usernameCond, Integer ageCond) {

        return queryFactory
                .selectFrom(member)
                //.where(usernameEq(usernameCond), ageEq(ageCond))
                .where(AllEq(usernameCond, ageCond))
                .fetch();
    }

    private BooleanExpression usernameEq(String usernameCond) {
            return usernameCond != null ? member.username.eq(usernameCond) : null;
    }

    private BooleanExpression ageEq(Integer ageCond) {
        return ageCond != null ? member.age.eq(ageCond) : null;
    }

    private BooleanExpression AllEq(String usernameCond, Integer ageCond) {
        return usernameEq(usernameCond).and(ageEq(ageCond));
    }

    @Test
    //@Commit
    public void 벌크_데이터수정() throws Exception {
        // given

        // when
        long bulkCount = queryFactory
                .update(member)
                .set(member.username, "비회원")
                .where(member.age.lt(28))
                .execute();

        em.flush();
        em.clear();

        // then
        List<Member> result = queryFactory
                .selectFrom(member)
                .fetch();

        for (Member member1 : result) {
            System.out.println("result = " + member1);
        }
    }

    @Test
    public void 벌크_더하기() throws Exception {
        // given

        // when
        long count = queryFactory
                .update(member)
                .set(member.age, member.age.add(1))
                .execute();

        // then

    }

    @Test
    public void 벌크_삭제() throws Exception {
        // given

        // when
        long count = queryFactory
                .delete(member)
                .where(member.age.gt(18))
                .execute();

        // then
    }

    @Test
    public void SQL_FUNCTION_호출() throws Exception {
        // given

        // when
        List<String> result = queryFactory
                .select(
                        // 사용중인 DB의 dialect에 등록되어 있는 것만 사용 가능함.
                        stringTemplate("function('replace', {0}, {1}, {2})"
                                , member.username
                                , "member"
                                , "M"))
                .from(member)
                .fetch();

        // then
        for (String s : result) {
            System.out.println("s = " + s);
        }
    }

    @Test
    public void SQL_FUNCTION_호출2() throws Exception {
        // given

        // when
        List<String> result = queryFactory
                .select(member.username)
                .from(member)
                /*
                .where(member.username.eq(
                        stringTemplate("function('lower', {0})", member.username
                )))
                */
                .where(member.username.eq(member.username.lower()))
                .fetch();
        // then
        for (String s : result) {
            System.out.println("s = " + s );
        }

    }
}