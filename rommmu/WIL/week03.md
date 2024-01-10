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