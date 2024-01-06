## TDD 와 Unit Test
TDD : Test Driven Development 로 테스트가 주도하는 개발로 **Unit Test는 TDD의 과정 중 하나**이다. <br>
### TDD 과정
1. 항상 실패하는 테스트를 먼저 작성하고
2. 테스트가 통과하는 프로덕션 코드를 작성하고
3. 테스트가 통과하면 프로덕션 코드를 리팩터링한다
### Unit Test Code 의 이점
1. 개발 단계 초기에 문제를 발견할 수 있도록 도와준다
2. 개발자가 나중에 코드를 리팩처링 하거나 라이브러리 업그레이드 등에서 기존 기능이 올바르게 작동하는지 확인할 수 있다
3. 기능에 대한 불확실성을 감소할 수 있다
4. 단위 테스트 자체를 시스템에 대한 문서로 사용할 수 있다

❗️ TDD의 단위 테스트를 사용하면 개발 초기, 개발 이후 모두 도움이 된다.

## 메인 클래스 코드
주로 패키지 명은 웹 사이트 주소의 역순이다 -> `admin.rommmu.com` 이라면 `com.rommmu.admin` 이다 <br>
### Application
앞으로 만들 프로젝트의 메인 클래스가 된다. <br>
`@SpringBootApplication`이 있는 위치부터 설정을 읽어가기 때문에 항상 프로젝트의 최상단에 위치해야한다. <br>
이를 통해 스프링부트의 자동 설정, 스프링 bean의 읽기와 생성을 모두 자동으로 해준다.<br><br>
`SpringApplication.run`을 통해 내장 Web Application Server를 실행한다 (WAS). <br>
이를 통해 항상 서버에 **톰캣 설치 없이 스프링부트로 만들어진 Jar 파일**을 통해 실행하면 된다.
-> 이를 통해 언제 어디서나 같은 환경으로 스프링부트를 배포할 수 있도록 해준다. <br>

❗️프로젝트의 최상단에 위치한 애플리케이션 파일은 스프링부트를 자동으로 설정하고, 내장 WAS를 사용해 배포를 용이하게 할 수 있다.

## API 생성
### `@RestController`
컨트롤러를 JSON으로 반환하는 컨트롤러로 만들어준다. <br>
이 어노테이션을 통해 해당 코드가 Controller임을 나타내는 것이다.
### `@GetMapping`
HTTP Method GET 요청을 받을 수 있는 API <br>
즉, /hello 경로에 대해 GET 요청을 처리하는 hello() 메서드와 매핑해주는 것이다.

## Test 코드

## 추가적인 공부
### 액션 메서드
웹 브라우저가 어떤 URL을 요청하면, 그 요청된 URL에 해당하는 컨트롤러의 메서드가 자동으로 호출되게 되는데, 이렇게 웹 브라우저의 요청에 의해 자동으로 호출되는 컨트롤러의 메서드를 액션 메서드라고 한다. <br>
이번 장에서는
```java
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto(@RequestParam("name") String name, @RequestParam("amount") int amount) {
        return new HelloResponseDto(name, amount);
    }
}
```
와 같이 코드를 작성했는데, `@RequestMapping()` 어노테이션을 컨트롤러 클래스에 붙여주면 

```java
@RestController
@RequestMapping("hello")
public class HelloController {

    @GetMapping
    public String hello() {
        return "hello";
    }

    @GetMapping("dto")
    public HelloResponseDto helloDto(@RequestParam("name") String name, @RequestParam("amount") int amount) {
        return new HelloResponseDto(name, amount);
    }
}
```
로 작성할 수 있다. <br>
예를 들어, localhost:8080/hello 는 `@RequestMapping("hello")`를 통해 `localhost:8080/hello` 로 매핑되고 이후 `@GetMapping`에 의해 `hello()` 액션 메서드가 호출된다. <br>
localhost:8080/hello/dto 는 `@RequestMapping("hello")`를 통해 `localhost:8080/hello` 로 매핑되고 이후 `@GetMapping`에 의해 `hello()` 액션 메서드가 호출된다.
<hr>

### 디자인 패턴
디자인 패턴은 **프로그램을 설계할 때 발생한 문제점들을 객체간 상호관계등을 이용해 해결할 수 있도록 하나의 규약 형태**로 만들어 둔 것이다. <br>
싱글톤 패턴, 팩토리 패턴, 전략 패턴 등등 여러 디자인 패턴이 존재하는데 이 중 싱글톤 패턴에 대해 이야기 해보려고 한다.
### Singleton Pattern
소프트웨어 디자인 패턴 중 싱글톤 패턴을 따르는 클래스들은 생성자가 여러 차례 호출되더라도 실제로 생성되는 객체는 하나이고 최초 생성 이후에 호출된 생성자는 최초의 생성자가 생성한 객체를 리턴한다. <br>
하나의 클래스에 오직 하나의 인스턴스만 가지는 패턴으로 **하나의 클래스를 기반으로 단 하나의 인스턴스를 만들고 이를 기반으로 로직**을 만든다.
하나의 인스턴스를 만들어 놓고 해당 인스턴스를 다른 모듈들이 공유해 사용하기 때문에 **인스턴스 생성시 비용이 줄어들지만 의존성이 높아진다.** <br>
-> 이 싱글톤 패턴의 단점은 TDD의 단위테스트 시에 걸림돌이 된다는 것이다 <br>
### Spring Container
![Alt text](https://velog.velcdn.com/images%2Fsorzzzzy%2Fpost%2Fb84f6e21-96ba-4757-b979-b38a00b19aa4%2F%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202021-08-22%20%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE%209.03.15.png)
**스프링 컨테이너**는 싱글톤 패턴을 적용하지 않아도, **객체 인스턴스를 싱글톤**으로 관리한다.
그래서 스프링 컨테이너는 싱글톤 패턴의 문제점을 해결하면서, 객체 인스턴스를 싱글톤(1개만 생성)으로 관리한다. <br>
Spring Bean이 바로 싱글톤 패턴으로 관리되는 Bean이다. <br>

```java
package com.example.springboot.singleton;

public class SingletonExample {

    private static SingletonExample instance; 
    // 유일한 인스턴스 저장을 위한 정적 변수

    private SingletonExample() {
        // private 생성자를 통해 직접적인 인스턴스 생성을 막는다.
    }

    public static synchronized SingletonExample getInstance() {
        // 외부에서 SingletonExample 클래스의 인스턴스를 가져오는 메소드로
        if (instance == null) {
            instance = new SingletonExample();
            // instance == null, 즉 인스턴스가 존재하지 않을 경우 생성하고
        }
        return instance;
        // 인스턴스가 존재하는 경우에는 기존 인스턴스를 리턴한다
    }
}
```

## 😔 테스트 코드가 실행이 안 되던 이슈에 대하여 ...
맞닥뜨린 이슈는 `javafx.fxml does not exist` 였습니다 ...
스프링 프로젝트 자체를 JavaFx로 만든 것도 아닌데 갑자기 이런 이슈를 맞닥뜨려 챗GPT와 뤼튼과 구글링을 여럿 해보았으나 도저히 안 돼서 <br>
Mac m1에 깔려있던 자바 11버전을 삭제하고 다시 깔려고 시도함 -> 근데 brew가 어쩌고저쩌고 안된다고 오류가 뜸 <br>
:: 그래서 결론적으로는 17버전의 자바를 가지고 프로젝트를 다시 생성하고 java 버전과 gradle 버전을 낮춤 -> 됐다 ???? <br>
❗️ 아마도 .. 원래 깔려있던 자바에 문제가 있던 것이 아닐까라고 추정함