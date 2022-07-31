# 객체지향 쿼리언어(JPQL)

JPA는 다양한 쿼리 방법을 지원한다.
- JPQL
- JPA Criteria
- QueryDSL
- 네이티브 SQL
- Mybatis

1. JPQL
JPQL 등장 배경
- JPA를 사용하면 엔티티 객체를 중심으로 개발.
- 검색 할 때도 테이블이 아닌 엔티티 객체를 대상으로 검색
- 모든 데이터를 객체로 변환해서 검색하는 것은 불가능함

예) 가장 단순한 조회 방법
EntityManage.find() -> 객체 그래프 탐색
Q.특정 조건에 해당하는 대상 모두를 검색하고자 하는 경우에는 ? 

2. JPA Criteria
장점
- 문자가 아닌 자바코드로 JPQL을 작성할 수 있다.
- JPA 공식이고 JPQL 빌더 역할을 한다.
- 동적쿼리 작성에 상대적으로 유리함
- 문법 상 오류가 있으면 컴파일 단계에서 오류를 출력함
단점 
- 문법이 상대적으로 어려움
- SQL스럽지 않아 유지보수에 적합하지 않다.

-> QueryDSL 사용을 권장한다.

3. QueryDSL
장점
- 문자가 아닌 자바코드로 JPQL을 작성할 수 있다.
- JPA 공식이고 JPQL 빌더 역할을 한다.
- 컴파일 시점에 문법 오류를 찾을 수 있다.
- 동적 쿼리 작성 편함.
- 단순하고 쉽다.
- 실무 사용 권장

4. native SQL
- JPA가 제공하는 SQL을 직접 사용하는 기능
- JPQL로 해결 할 수 없는 특정 데이터베이스에 의존적인 기능

## JPQL 소개
- JPQL은 객체지향 쿼리 언어이다. 따라서 테이블을 대상으로 쿼리를 생성하는 것이 아니라 객체를 대상으로 쿼리를 생성한다.
- JPQL은 sql을 추상화해서 특정 데이터베이스에 sql을 의존하지 않는다.
- JPQL은 결국 sql로 변환한다.

## 문법
select m from Member as m where m.age > 18
- 엔티티와 속성은 대소문자 구분
- 키워드는 대소문자 구분 X
- 엔티티 이름 사용, 테이블 이름이 아님.
- 별칭은 (m), as 생략 가능하다.

### TypeQuery, Query
- TypeQuery : 반환타입이 명확할 때 사용
- Query : 반환 타입이 명확하지 않을 때 사용

결과 조회 API
- Collection인 경우 `getResultList()`
-> 결과가 하나 이상일 때, 리스트 반환
-> 결과가 없으면 빈 리스트 반환
- 단 건인 경우 `getSingleResult()`
-> 결과가 정확히 하나 나와야 함
결과가 없으면 : NoResultException
둘 이상이면 : NonUniqueResultException

### 파라미터 바인딩
```java
String queryString = "select m from Member m where m.username = :username";
TypedQuery<Member> memberTypedQuery = em.createQuery(
                    queryString,
                    Member.class);
memberTypedQuery.setParameter("username", "Harry Park");
List<Member> memberList = memberTypedQuery.getResultList();

// 순서로 하는 경우 ?1
```            

## 프로젝션
SELECT 절에 조회할 대상을 지정하는 것
프로젝션 대상 : 엔팉, 임베디드 타입, 스칼라 타입(숫자, 문자 등 기본 데이터 타입)

여러 값 조회
1. Query로 조회
2. Object[] 타입으로 조회

3. new 명령어로 조회

```java
// Object[]로 조회
List<Object[]> resultList = em.createQuery("select m.username, m.age from Member m").getResultList();

// new 로 조회
List<MemberDto> resultList1 = em.createQuery(
                    "select new jpql.MemberDto(m.username, m.age) from Member m"
                    , MemberDto.class)
                    .getResultList();
```

## 페이징
JPA는 페이징을 다음 두 API로 추상화한다.
- setFirstResult(int startPosition) : 조회 시작 위치 (0부터 시작)
- setMaxResults(int maxResult) : 조회할 데이터 수


## 조인
- 내부조인
- 외부조인
- 세타조인

ON절을 활용한 조인
1. 조인 대상 필터링
2. 연관관계 없는 대상을 외부 조인하는 경우(하이버네이트 5.1부터)

## 서브쿼리
지원함수
EXISTS, ALL, ANY, SOME
한계
JPA는 WHERE, HAVING 절에서만 서브쿼리 가능
하이버네이트는 select절에서도 가능
from 절의 서브쿼리는 현재 jpql에서 불가능

## JPQL 타입표현
- 문자 : 'HELLO', 'She''s'
- 숫자 : 10L, 10D, 10F
- 논리 : TRUE, FALSE
- ENUM : 패키지명.ENUM명
- 엔티티 타입 : TYPE(m) = Member ( 상속관계에서 사용 )

- EXISTS, IN
- IS NULL, IS NOT NULL
- AND, OR, NOT, LIKE
- =, >, >=, <, <=, <>
- BETWEEN

조건식
- 기본 case 식
select case when m.age <= 10 then '1'
			when m.age >= 60 then '2'
			else '3'
		end
  from Member m
- 단순 case 식
select case t.name
			when 'team A' then '1'
			when 'team B' then '2'
			else '3'
		end
  from Team m

- COALESCE : 하나씩 조회해서 Null이 아니면 반환
select coalesce(m.username, "이름 없는 회원") from Member m;

- NULLIF : 두 값이 같으면 null 반환, 다르면 첫 값 반환


기본함수

모두 지원하는 표준 함수 
CONCAT
SUBSTRING
TRIM
LOWER, UPPER
LENGTH
LOCATE
ABS, SQRT, MOD
SIZE, INDEX(JPA)

사용자 정의 함수
: 사용하는 DB 방언을 상속받고, 사용자 정의 함수를 등록
function(function_name, value)


## 경로 표현식
.(점)을 찍어 객체 그래프로 탐색하는 것

select m.username -> 상태필드
  from Member m 
  join m.team t   -> 단일 값 연관 필드
  join m.orders o -> 컬렉션 값 연관 필드
where t.name = '팀A';

상태필드 : 값을 저장하기 위한 필드
-> 경로 탐색의 끝, 탐색 X
연관필드 : 연관관계를 위한 필드
- 단일 값
: @ManyToOne, @OneToOne, 대상이 엔티티
-> 묵시적 내부 조인, 탐색 O
- 컬렉션 값 연관 필드
: @oneToMany, @ManyToMany, 대상이 컬렉션
-> 묵시적 내부 조인 발생, 탐색 X
-> from 절에서 명시적 조인을 통해 별칭을 얻으면 별칭을 통해 탐색 가능

가급적 명시적 조인 사용
-> 조인은 SQL 튜닝에 중요 포인트
-> 묵시적 조인은 조인이 일어나는 상황을 한눈에 파악하기 어려움

## JPQL - 페치 조인(fetch join)

JPQL에서 성능 최적화를 위해 제공하는 기능
연관된 엔티티나 컬렉션을 SQL 한 번에 함께 조회하는 기능
join fetch 명령어 사용

LAZY 설정을 해도 fetch가 먼저 적용됨

fetch join 과 distinct
1. sql에 distinct 추가 
2. application에 distinct 추가


fetch조인과 일반 조인의 차이
일반 조인 실행시 연관된 엔티티를 함께 조회하지 않음.

fetch join의 특징과 한계
- 페치 조인 대상은 별칭을 줄 수 없다.
-> 하이버네이트는 가능하지만 가능한 사용하지 말 것
- 둘 이상의 컬렉션은 패치 조인할 수 없다.
- 컬렉션을 패치조인하면 페이징 API를 사용할 수 없다.
-> 일대일, 다대일 같은 단일 값 연관 필드들은 페치 조인해도 페이징 가능하다.
-> 하이버네이트는 경고 로그를 남기고 메모리에서 페이징(매우 위험)

모든 것을 페치 조인으로 해결할 수는 없음
페치 조인은 객체 그래프를 유지할 때 사용하면 효과적
<STRONG>여러 테이블을 조인해서 엔티티가 가진 모양이 아닌 다른 결과를 내야하면, 페치 조인보다는 일반 조인을 사용하고 필요한 데이터들만 조회해서 DTO로 반환하는 것이 좋음</STRONG>

다형성 쿼리
select i from Item i
where type(i) in (Book, Movie)

TREAT
자바의 타입 캐스팅과 유사
상속 구조에서 부모 타입을 특정 자식 타입으로 다룰 떄 사용
FROM, WHERE, SELECT 사용

select i from Item i 
where treat(i as Book).author = 'kim'

엔티티 직접 사용
JPQL에서 엔티티를 직접 사용하면 SQL에서 해당 엔티티의 기본 키 값을 사용
select count(m.id) from Member m;
select count(m) from Member m; <- 엔티티를 직접 사용
select m from Member m where m = :member; <- 엔티티를 직접 사용

## Named 쿼리
- 정적 쿼리만 가능
- 미리 정의해서 이름을 부여해두고 사용
- 어노테이션, xml에 정의
- 어플리케이션 로딩 시점에 초기화 후 재사용
- 어플리케이션 로딩 시점에 쿼리를 검증 가능함.

## 벌크연산

JPA 변경 감지 기능으로 실행하려면 너무 많은 SQL 실행

쿼리 한 번으로 여러 테이블 로우 변경(엔티티)
-> executeUpdate() 
String query = "update Producrt p set p.price = p.price * 1.1 where p.stockAmount < :stockAmount";
em.createQuery(query).setParameter("stockAmount", 10).executeUpdate();

영속성 컨텍스트를 무시하고 데이터베이스에 직접 쿼리
-> 벌크 연산을 먼저 수행
-> 벌크 연산 후 영속성 컨텍스트 초기화
-> flush를 먼저 날리고 실행함

-> db에만 반영하는 것이기 때문에 clear하고 새로 가지고 와야함.




