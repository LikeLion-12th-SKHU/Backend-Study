## build.gradle 설정하기

```java
buildscript {
    ext {
        springBootVersion = '2.1.7.RELEASE'
    }
    repositories {
        mavenCentral()
        jcenter()
    }
    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:${springBootVersion}")
    }
}

apply plugin: 'java'
apply plugin: 'eclipse'
apply plugin: 'org.springframework.boot'
apply plugin: 'io.spring.dependency-management'

group 'com.jojoldu.book'
version '1.0-SNAPSHOT'
sourceCompatibility = 1.8

repositories {
    mavenCentral()
}

dependencies {
    compile('org.springframework.boot:spring-boot-starter-web')testCompile('org.springframework.boot:spring-boot-starter-test')
}
```
<br>

### ext는 전역변수 설정
`ext` 는 build.gradle의 전역변수를 설정해주는 것 <br>
`springBootVersion = '2.1.7.RELEASE'` 은 `spring-boot-gradle-plugin`라는 스프링부트 그레이들 플러그인의 2.1.7을 의존성으로 받겠다는 것이다.

### 플러그인 의존성 적용
```
apply plugin: 'java'
apply plugin: 'eclipse'
apply plugin: 'org.springframework.boot'
apply plugin: 'io.spring.dependency-management'
```

`io.spring.dependency-management` 플러그인은 스프링 부트의 의존성들을 관리해주는 플러그인으로 꼭 추가해아한다.
### 의존성을 어떤 원격저장소에서 받을지
`repositories` 는 각종 의존성(즉, 라이브러리) 들을 어떤 원격저장소에서 받을지 정하는 것이다.
<br>
`mavenCentral`은 많이 사용했지만 과정이 복잡하다.
그래서 라이브러리 업로드가 간단한 `jcenter`를 사용한다.
### 프로젝트 개발에 필요한 의존성을 선언
`dependencies` 는 프로젝트 개발에 필요한 의존성들을 선언하는 것이다.
<br>
## Gradle 버전 낮추기
### gradle-wrapper.properties
`distributionUrl=https\://services.gradle.org/distributions/gradle-4.10.2-bin.zip` 에서 <br>
`gradle-버전-bin.zip` 으로 해준다. 