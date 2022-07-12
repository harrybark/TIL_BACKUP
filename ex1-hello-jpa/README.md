# JPA에서 가장 중요한 2가지
1. 객체와 관계형 데이터베이스 매핑하기
2. 영속성 컨텍스트

## 영속성 컨텍스트
: Persistence Context
: JPA를 이해하는데 가장 중요한 용어
: "엔티티를 영구 저장하는 환경"이라는 의미
-> 엔티티를 DB에 저장하는 것이 아니라 영속성 컨텍스트에 저장한다는 의미(persist - 영속화)

* Entity Manager Factory, Entity Manager

- 영속성 컨텍스트는 논리적인 개념
- 엔티티 매니저를 통해 영속성 컨텍스트에 접근

## 엔티티의 생명주기
- 비영속(new/transient)
: 영속성 컨텍스트와 전혀 관계가 없는 새로운 상태
```java
Member member = new Member();
member.setId("member1");
member.setUserName("회원1");
// 객체만 생성된 상태(비영속 상태)
```

### - 영속(managed)
: 영속성 컨텍스트에 관리되는 상태
```java
EntityManager em = emf.createEntityManager();
em.getTransaction().begin();
em.persist(member); // persist메소드를 통한 영속화
```

### - 준영속(detached)
: 영속성 컨텍스트에 저장되었다가 분리된 상태
₩em.detached(member);₩

### - 삭제(removed)
: 삭제된 상태
`em.remove(member);`

## 영속성 컨텍스트의 이점
- 1차캐시
- 동일성(Identity) 보장
: 1차 캐시로 반복 가능한 읽기(repeatable read) 등급의 트랜잭션 격리 수준을 데이터베이스가 아닌 어플리케이션 차원에서 제공한다.
- 트랜잭션을 지원하는 쓰기지연(transactional write-behind)
: commit(); 을 하면 영속성 컨텍스트에서 flush()되며, 데이터베이스에 INSERT/UPDATE Query를 날린다.
- 변경 감지(dirty checking)
- 지연로딩(lazy Loading)

### 플러시
: 영속성 컨텍스트의 변경내용을 데이터베이스에 반영

#### 플러시 발생
- 변경 감지
- 쓰기 지연 SQL 저장소에 수정된 엔티티 등록
- 쓰기 지연 저장소의 쿼리를 데이터베이스 전송(등록, 수정, 삭제 쿼리)

#### 영속성 컨텍스트 플러시 방법
1. em.flush() - 직접 호출
2. transaction commit
3. JPQL 쿼리 실행

#### 플러시 특징
1. 영속성 컨텍스트를 비우지 않음
2. 영속성 컨텍스트의 변경내용을 데이터베이스에 동기화
3. 트랜잭션이라는 작업 단위가 중요함.(커밋 직전에만 동기화하면 됨.)

### 준영속
영속 상태의 엔티티가 영속성 컨텍스트에서 분리(detached)
영속성 컨텍스트가 제공하는 기능을 사용하지 못한다.
- 1차캐시, 동일성 보장, 쓰기지연, 지연로딩 등..

#### 준영속 상태로 만드는 방법
```java
// 영속 상태
Member member = em.find(Member.class, 150L);
member.setName("chage"); // Dirty Checking

// 준영속 상태(jpa에서 관리 안함)
// 특정 엔티티만 준영속 상태로 만드는 경우
em.detach(member);

// 영속성 컨텍스트 전체를 초기화 하는 경우
em.clear();

// 영속성 컨텍스트를 종료
em.close();

```


* * * 

## 앤티티와 매핑

### 객체와 테이블 매핑
- 객체와 테이블 매핑 : `@Entity`, `@Table`
`@Entity`
: JPA가 관리하는 엔티티
: JPA를 사용패서 테이블과 매핑할 클래스는 필수
* 주의 
JPA스펙상 기본 생성자는 있어야 함.(public or protected)
final class, enum, interface, inner class 사용불가
저장할 필드에 final 사용 금지 
#### 속성
- name 
JPA에서 사용할 엔티티 이름 지정
기본 값 : 클래스 이름
같은 클래스 이름이 없으면 가급적 기본값 사용

`@Table`
엔티티와 매핑할 테이블 지정

- 필드와 컬럼 매핑 : `@Column`
- 기본 키 매핑 : `@Id`
- 연관관계 매핑 : `@ManyToOne`n `@JoinColumn`

### 데이터베이스 스키마 자동 생성
DDL을 어플리케이션 실행 시점에 자동 생성
테이블 중심 -> 객체 중심
데이터베이스 방언을 활용해서 데이터베이스에 맞는 적절한 DDL 생성
운영에서는 사용X(개발 단계에서만 사용)

hibernate.hbm2ddl.auto 
- create : 기존 테이블 삭제 후 생성(drop + create)
- create-drop : 종료 시점에 drop
- update : 변경분만 반영(컬럼 삭제는 불가능)
- validate : 엔티티와 테이블이 정상 매핑되었는지만 확인
- none : 사용하지 않음

* 주의 
운영장비에는 절대 create, create-drop, update사용하면 안됨.
개발 초기에는 create / update
테스트 서버는 update / validate
스테이징과 운영 서버는 validate / none

DDL 생성 기능
: 런타임 중에 영향(JPA의 실행 로직에 영향)을 주는 것이 아니라 DB에 영향을 줌

### 필드와 컬럼 매핑
`@Column`
name : 필드와 매핑할 테이블의 컬럼이름
insertable, updateable : 등록, 변경가능 여부
nullable : null값의 허용 여부 
unique : 유니크 제약 조건
columnDefinition : 컬럼 정의를 직접하고 싶은 경우(varchar(100) default 'EMPTY')
length : 문자 길이 제약 조건, String 타입에만 사용
precision scale : BigDecimal, BigInteger 타입에서 사용.

`@Enumerated`
Enum타입 매핑할 때 사용
Default == ORDINAL
EnumType.ORDINAL : 순서를 저장(문자X)
EnumType.STRING  : 이름을 저장

`@Temporal`
java.util.Date, java.util.Calendar를 매핑할 때 사용
java8이후는 LocalDateTime을 지원하기 때문에 @Temporal을 사용하지 않아도 무방함.

`@Lob`
지정 가능한 속성은 별도로 없음.
매핑하는 타입이 문자면 CLOB
나머지는 BLOB

`@Transient`
필드와 매핑하지 않는 경우 사용






