## JPA 프로그래밍 : 엔티티 맵핑
### @Entity
- “엔티티”는 객체 세상에서 부르는 이름
- 보통 클래스와 같은 이름을 사영하기 때문에 값을 변경하지 않음
- 엔티티의 이름은 JQL에서 쓰임

### @Table
- “릴레이션” 세상에서 부르는 이름
- @Entity의 이름이 기본 값
- 테이블의 이름은 SQL에서 쓰임

### @Id
- 엔티티의 주키를 맵핑할 때 사용
- 자바의 모든 primitive 타입과 그 랩퍼 타입을 사용할 수 있음.
- Date랑 BigDecimal, BigInteger도 가능
- 복합키를 만드는 방법도 있지만 논외..

### @GeneratedValue
- 주키의 생성 방법을 맵핑하는 어노테이션
- 생성 전략과 생성기를 설정 할 수 있다.
- 기본 전략은 AUTO : 사용하는 `db`에 따라 전략 선택
- TABLE, SEQUENCE, IDENTITY 중 하나

### @Column
- unique
- nullable
- length
- columnDefinition
- ……

### @Temporal
- 현재 JPA 2.1까지는 Date와 Calendar만 지원
- JPA 2.2 부터는 LocalDateTime 등에도 지원함.

### @Transient
- 컬럼으로 맵핑하고 싶지 않은 멤버에 사용

## JPA 프로그래밍 : Value 타입 맵핑

### 엔티티 타입과 Value 타입 구분
- 식별자가 있어야 하는가
- 독립적으로 존재해야 하는가
### Value타입 종류
- 기본타입(String, Date, Boolean. .. )
- Composite Value
- Collection Value
### Composite Value 타입 맵핑
- @Embadable
- @Embadded
- @AttributeOverides
- @AttributeOveride
- 

## JPA 프로그래밍 : 관계 맵핑
### 1:N 맵핑
: 관계에는 항상 두 엔티티가 존재
- 둘 중 하나는 그 관계의 주인
- 나머지는 종속된 관계
- 해당 관계의 반대쪽 레퍼런스를 가지고 있는 쪽이 주인
- 
### 단방향 관계에서의 주인은 명확함
- 관계를 정의한 쪽이 그 관계의 주인

### 단방향 @ManyToOne
- 기본값은 FK

### 단방향 @OneToMany
- 기본값은 조인 테이블 생성

### 양방향
- FK를 가지고 있는 쪽이 오너 따라서 기본값은 @ManyToOne 가지고 있는 쪽이 주인
- 주인이 아닌 쪽에서 mappedBy 사용
- @ManyToOne 이쪽이 주인
- @OneToMany(mappedBy)
- 주인한테 관계를 설정해야 DB에 반영

### 엔티티 상태와 Cascade
: 엔티티의 상태 변화를 전파시키는 옵션

#### Entity의 상태
- Transient : JPA가 모르는 상태
- Persistent : JPA가 관리중인 상태(1차 캐시, Dirty Checking, Write Behind, ..)
- Detached : JPA가 더이상 관리하지 않는 상태
```java
Session.evict();
Session.clear();
Session.close();
/* detached 후 다시 영속화 */
Session.update();
Session.merge();
Session.saveOrUpdate();
```
- Removed  : JPA가 관리하긴 하지만 삭제하기로 한 상태 

###  Fetch
: 연관 관계에 있는 엔티티의 연관 관계 정보를 어떻게 가지고 올 것인가..?
`Eager` or `Lazy`
- `@OneToMany`는 기본값 `Lazy`
- `@ManyToOne`는 기본값 `Eager` 

### Query
#### JPQL (HQL) 
- Java Persistence Query Language / Hibernate Query Language 
- DB 테이블이 아닌, 엔티티 객체 모델 기반으로 쿼리 작성
- JPA 또는 하이버네이트가 해당 쿼리를 SQL로 변환해서 실행한다.


#### Criteria
- 타입 세이프 쿼리

#### Native Query
- SQL 쿼리 실행하기

## JPA 원리
### 소개 및 원리

`JpaRepository<Entity, Id>` 인터페이스
: 매직 인터페이스
: `@Repository`가 없어도 `Bean`으로 등록됨

`@EnableJpaRepositories` 
: 매직의 시작
- 시작은 @import(JpaRepositoriesRegistrar.class)
- 핵심 ImportBeanDefinitionRegistrar 인터페이스 


