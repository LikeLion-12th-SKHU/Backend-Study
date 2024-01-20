## JPA
> 개발자는 sql에 종속적인 개발이 아닌 객체지향적인 프로그래밍을 하면 JPA가 이를 관계형 데이터베이스에 맞게 SQL을 대신 생성해서 실행해준다.
### Spring Data JPA
Spring Data JPA가 등장한 이유는 `구현체 교체의 용이성` 과 `저장소 교체의 용이성` 이다. <br>
**구현체 교체의 용이성**은 다른 구현체로 쉽게 교체하기 위함이다. **저장소 교체의 용이성**은 관계형 데이터베이스 외에 다른 저장소로 쉽게 교체하기 위함이다.
### Spring Data JPA 적용하기
<details>
<summary> build.gradle</summary>

``` java
dependencies {
    compile('org.springframework.boot:spring-boot-starter-web')
    compile('org.projectlombok:lombok')
    compile('org.springframework.boot:spring-boot-starter-data-jpa')
    compile('com.h2database:h2')
    testCompile('org.springframework.boot:spring-boot-starter-test')
}
```

</details>

`build.gradle` 에 `spring-boot-starter-data-jpa` 와 `h2` 를 추가해준다. <br>
`spring-boot-starter-data-jpa` 는 스프링부트용 Spring Data JPA 추상화 라이브러리로, 스프링부트 버전에 맞춰 자동으로 JPA 관련 라이브러리 버전을 관리해주는 것이다. `h2`는 인메모리형 관계형 데이터베이스로, 별도의 설치 없이 메모리에서 실행되기 때문에 애플리케이션을 재시작할 때마다 초기화된다는 특징이 있다.

### [Domain](https://github.com/Rommmu/Backend-Study-12th/blob/main/rommmu/WIL/week03/domain.md)
### [Controller](https://github.com/Rommmu/Backend-Study-12th/blob/main/rommmu/WIL/week03/controller.md)
### [Dto](https://github.com/Rommmu/Backend-Study-12th/blob/main/rommmu/WIL/week03/dto.md)
### [Service](https://github.com/Rommmu/Backend-Study-12th/blob/main/rommmu/WIL/week03/service.md)
### [application.properties](https://github.com/Rommmu/Backend-Study-12th/blob/main/rommmu/WIL/week03/application.properties.md)

# 추가적인 공부
## `@Controller` vs `@RestController`
> `@RestController`은 `@Controller`와 `@ResponseBody`의 조합한 것이라고 이해하면된다.
<br>

`@Controller`의 역할은 Model 객체를 만들어 데이터를 담고 View를 찾는 것이고, `@RestController`는 단순히 객체만을 반환하고 객체 데이터는 `JSON`(또는 XML 형식)으로 `HTTP 응답`에 담아서 전송하는 것이다.

[@Controller와 @RestController의 과정](https://mangkyu.tistory.com/49)

## `@RequestParam` vs `@PathVariable`
> `@RequestParam`은 쿼리스트링으로 url 요청을 할 때 사용한다.

`@PathVariable`은 값을 하나만 받아올 수 있으므로, 쿼리스트링 등을 이용한 여러 개 데이터를 받아올 때는 `@RequestParam`을 사용한다. 따라서 `@PathVariable`은 어떤 요청이든 하나만 사용할 수 있기 때문에  쿼리스트링이 있는 uri에는 안쓰고 주로 post 요청에 많이 사용한다고 한다. <br>

[@RequestParam vs @PathVariable](https://velog.io/@dongscholes/JavaSpringBoot-RequestParam-vs-PathVariable-%EC%93%B0%EC%9E%84%EC%83%88-%EC%82%AC%EC%9A%A9%EB%B2%95-%EC%B0%A8%EC%9D%B4%EC%A0%90)

## `@OneToMany` vs `@ManyToOne`
### 단방향 vs 양방향 연결관계
`@OneToMany`<br>
**단방향(unidirectional)** 연결관계는 **상대 엔티티에 `@ManyToOne`이 없는 경우**, **양방향(bidirectional)** 연결관계는 **상대 엔티티에 `@ManyToOne`이 있는 경우**이다. <br><br>
`@ManyToOne`<br>
**단방향(unidirectional)** 연결관계는 **상대 엔티티에 `@OneToMany`이 없는 경우**, **양방향(bidirectional)** 연결관계는 **상대 엔티티에 `@OneToMany`이 있는 경우**이다. <br>
> 단방향이든 양방향이든 `@OneToMany` 어노테이션을 달고 있는 엔티티가 부모 엔티티가 된다. 즉, FK를 가진 엔티티가 자식 엔티티이다.
> 
### `mappedBy`
연관관계에는 ownership 즉, 주인이라는 개념이 있다.
주인은 연관관계의 관리 주체이며, 이 연관관계를 관리해야할 책임이 있다. <br>
부모와 자식 엔터티는 의존도를 통해 정해지며, 주인 엔터티는 DB에 저장되는 FK를 어떤 엔터티가 CRUD를 담당할지 정하는 과정에서 정해지는 것이다. **즉, 주인 엔터티와 부모 엔터티는 다른 개념이다.** <br>
양방향 매핑을 적용할 때, Student 객체 뿐만 아니라 Department 객체도 students 필드를 통해서 STUDENT 테이블에 접근이 가능하기 때문에, 혼란이 생길 수 있다. 이 경우 Student 객체와 Department 객체가 서로 STUDENT 테이블의 데이터 값을 변경하려 한다면 데이터 무결성을 해칠 가능성이 존재해 위험하다. 따라서 이 경우 두 객체중 하나의 객체만 테이블을 관리할 수 있도록 정하는 것이 `mappedBy` 옵션이다. <br>
```java
public class Department {
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "department")
    List<Student> students;
}
```
이 경우 Department 객체에 `mappedBy`가 적용되어 있는데, 이 경우 Department 객체는 STUDENT 테이블을 관리할 수 없고 Student 객체만이 권한을 받고 **주인이 아닌 쪽은 읽기(조회)만 가능해진다.** <br>
**즉, `mappedBy`가 정의되지 않은 객체가 주인(Owner)가 되는 것이다.**
<br>
일반적으로 외래키를 가진 객체를 주인으로 정의하는 것이 좋다.<br>

[mappedBy가 필요한 이유](https://velog.io/@wogh126/JPA-%EC%96%91%EB%B0%A9%ED%96%A5-%EB%A7%A4%ED%95%91%EC%97%90%EC%84%9C-MappedBy%EA%B0%80-%ED%95%84%EC%9A%94%ED%95%9C-%EC%9D%B4%EC%9C%A0) <br>
[JPA 연관관계 관리하기](https://velog.io/@goniieee/JPA-OneToMany-ManyToOne%EC%9C%BC%EB%A1%9C-%EC%97%B0%EA%B4%80%EA%B4%80%EA%B3%84-%EA%B4%80%EB%A6%AC%ED%95%98%EA%B8%B0)