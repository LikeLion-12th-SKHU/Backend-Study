# 추가적인 공부
## Validation Message Customize
``` java
@Data
public class StudentEdit {
    int id;

    @NotEmpty @NotBlank
    @Size(min=8, max=12)
    String studentNo;

    @NotEmpty @NotBlank
    @Size(min=2, max=20)
    String name;

    @NotEmpty @NotBlank
    @Pattern(regexp="010-[0-9]{3,4}-[0-9]{4}",
             message="휴대폰 번호를 입력하세요 예:010-000-0000")
    String phone;

    @NotEmpty @Email
    String email;

    @NotEmpty @NotBlank
    @Pattern(regexp="남|여", message="남, 여 중 하나를 입력하세요.")
    String sex;

    @Min(value=1, message="학과를 선택하세요.")
    int departmentId;
}

```

`com.rommmu.model.StudentEdit`에 이와 같이 코드를 작성한 후 런서버하여 `http://localhost:8088/student/create`를 확인해보면 `StudentEdit` 어노테이션에 붙인 것과 같이 form validation 메세지가 뜬다. <br>
하지만 새로고침 할 때마다 form validation 메세지가 랜덤으로 뜨는게 불편해서 적절한 검증을 통해 에러메세지가 뜨는 것을 커스터마이징하고 싶다는 니즈가 생겼다. 따라서 **form validation의 우선순위를 정하여 검증이 실패하면 해당 에러메세지를 띄우고**, 검증에 **통과하면 다음 항목을 검증해** 에러를 확인하여 이에 맞는 메세지를 띄우는 식으로 코드를 작성하였다.

### `ValidationGroups` 클래스 생성
`com.rommmu` 하위에 `validation` 패키지를 만들어 spring form validation customize에 사용할 인터페이스를 모아주도록 한다. 클래스 안에 인터페이스를 모으는 이유는 유효성 검사 규칙을 구조화하고, 필드에 적용할 그룹을 선택적으로 지정할 수 있도록 도와주어 이를 통해 추후 유효성 검사 규칙이 추가되더라도 따로 파일을 생성하는 것이 아니라 `ValidationGroups`에 코드를 추가함으로써 코드의 유지보수성과 가독성을 개선하기 위해서이다.

```java
public class ValidationGroups {
    public interface NotEmptyGroup {};
    public interface NotBlankGroup {};
    public interface SizeGroup {};
    public interface PatternGroup {};
    public interface MinGroup {};
}
```
_Group으로 유효성 검사에 따라 인터페이스명을 지정해주었다.

### `ValidationSequence` 인터페이스 생성
```java
@GroupSequence({NotEmptyGroup.class, NotBlankGroup.class, SizeGroup.class, MinGroup.class, PatternGroup.class})
public interface ValidationSequence {
}
```
여기서 사용한 `@GroupSequence` 어노테이션은 유효성 검사 그룹의 순서를 지정할 때 사용한다. `@GroupSequence` 어노테이션을 사용하여 유효성 검사 그룹의 순서를 정의하면, 해당 순서로 유효성 검사가 실행된다. <br>
위 코드에서 유효성 검사의 순서는 NotEmpty -> NotBlank -> Size -> Min -> Pattern의 순서이다. (내가 느끼는 우선순위대로 작성한 것 ...)

### `model.StudentEdit` Model 수정
```java
@Data
public class StudentEdit {
    int id;

    @NotEmpty(groups = ValidationGroups.NotEmptyGroup.class)
    @NotBlank(groups = ValidationGroups.NotBlankGroup.class)
    @Size(min=8, max=12, groups = ValidationGroups.SizeGroup.class)
    String studentNo;

    @NotEmpty(groups = ValidationGroups.NotEmptyGroup.class)
    @NotBlank(groups = ValidationGroups.NotBlankGroup.class)
    @Size(min=2, max=20, groups = ValidationGroups.SizeGroup.class)
    String name;

    @NotEmpty(groups = ValidationGroups.NotEmptyGroup.class)
    @NotBlank(groups = ValidationGroups.NotBlankGroup.class)
    @Pattern(regexp="010-[0-9]{3,4}-[0-9]{4}",
            message="휴대폰 번호를 입력하세요 예:010-000-0000",
            groups = ValidationGroups.PatternGroup.class)
    String phone;

    @NotEmpty(groups = ValidationGroups.NotEmptyGroup.class)
    @Email
    String email;

    @NotEmpty(groups = ValidationGroups.NotEmptyGroup.class)
    @NotBlank(groups = ValidationGroups.NotBlankGroup.class)
    @Pattern(regexp="남|여", message="남, 여 중 하나를 입력하세요.",
            groups = ValidationGroups.PatternGroup.class)
    String sex;

    @Min(value=1, message="학과를 선택하세요.",
            groups = ValidationGroups.MinGroup.class)
    int departmentId;
}
```
유효성 검사에 사용되는 어노테이션의 그룹을 지정해준다. `groups`은 유효성 검사가 적용되는 그룹을 지정하는데 사용한다. 예를 들어, `@NotBlank` 어노테이션의 `groups` 속성을 `ValidationGroups.NotBlankGroup`으로 설정하여 `@GroupSequence`의 순서가 적용될 수 있도록 그룹을 명시해준다.

```java
    @NotEmpty(groups = ValidationGroups.NotEmptyGroup.class)
    @NotBlank(groups = ValidationGroups.NotBlankGroup.class)
    @Size(min=8, max=12, groups = ValidationGroups.SizeGroup.class)
    String studentNo;
```
즉, 이 경우에는 `@GroupSequence`의 우선순위에 따라 NotEmpty를 검사한 후 통과하면 NotBlank를 검사하고 통과하면 Pattern을 검사한다.

### `Controller` 클래스 수정
```java
@PostMapping("create")
    public String create(Model model,
                         @Validated(ValidationSequence.class) StudentEdit studentEdit, BindingResult bindingResult) {
        try {
            studentService.insert(studentEdit, bindingResult);
            return "redirect:list";
        }
        catch (Exception e) {
            model.addAttribute("departments", departmentService.findAll());
            bindingResult.rejectValue("", null, "등록할 수 없습니다.");
            return "student/edit";
        }
    }

@PostMapping(value="edit", params="cmd=save")
    public String edit(Model model,
                       @Validated(ValidationSequence.class) StudentEdit studentEdit, BindingResult bindingResult) {
        try {
            studentService.update(studentEdit, bindingResult);
            return "redirect:list";
        }
        catch (Exception e) {
            model.addAttribute("departments", departmentService.findAll());
            bindingResult.rejectValue("", null, "수정할 수 없습니다.");
            return "student/edit";
        }
    }
```

기존 유효성 검사를 위해서는 `@Valid` 를 사용했지만 아까 작성한 유효성 검사의 순서를 적용해주기 위해서 `@Validated` 어노테이션을 사용한다. 특히, **특정 필드에 대한 선행 조건을 만족한 경우에만 후행 검사를 수행하고 싶을 때, `@Validated`를** 사용하여 검사 순서를 지정한다.

[Spring Boot Validation 순서 정하기](https://dncjf64.tistory.com/302)

## 💡 `@Valid` vs `@Validated` 
Spring에서는 유효성 검증을 위해 `@Valid` 와 `@Validated` 어노테이션을 많이 사용한다. 두 어노테이션의 가장 큰 차이는 `@Valid` 어노테이션은 `javax.validation` 패키지에 속한다는 것이고, `@Validated` 어노테이션은 `org.springframework.validation` 패키지에 속한다는 것이다.

### `@Valid`
`@Valid`는 **`빈 검증기(Bean Validator)`를 이용해 객체의 제약 조건을 검증하도록 지시**하는 어노테이션이다. JSR 표준의 빈 검증 기술의 특징은 객체의 필드에 달린 어노테이션으로 편리하게 검증을 한다는 것이다. <br>
Spring에서는 일종의 어댑터인 `LocalValidatorFactoryBean`이 제약 조건 검증을 처리하는데, 이를 이용하려면 `LocalValidatorFactoryBean`을 빈으로 등록해야한다. SpringBoot에서 이를 사용하기 위해 `build.gradle`에 의존성을 추가해준다.

```java
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-validation'
}
```
검증에 오류가 있다면 `MethodArgumentNotValidException` 예외가 발생하게 되고, 디스패처 서블릿에 기본으로 등록된 예외 리졸버(Exception Resolver)인 `DefaultHandlerExceptionResolver`에 의해 400 BadRequest 에러가 발생한다. <br><br>
`@Valid`는 기본적으로 **컨트롤러에서만 동작**하며 기본적으로 다른 계층에서는 검증이 되지 않아, 다른 계층에서 파라미터를 검증하기 위해서는 `@Validated`를 사용해야한다.

### `@Validated`
입력 파라미터의 유효성 검증은 컨트롤러에서 최대한 처리하고 넘겨주는 것이 좋지만, 불가피하게 다른 곳에서 파라미터를 검증해야하는 경우, Spring에서는 이를 위해 **AOP 기반으로 메소드의 요청을 가로채서 유효성 검증을 진행해주는 `@Validated`** 어노테이션을 제공하고 있다. `@Validated`는 JSR 표준 기술이 아니며 Spring 프레임워크에서 제공하는 어노테이션이다.
> ✨ **AOP** <br>
> AOP(Aspect-Oriented Programming)는 애플리케이션의 핵심 로직과 관점(Aspect)으로 분리된 부가적인 로직을 모듈화하여 개발하는 프로그래밍 방법론이다. <br>
> Spring의 AOP는 프록시 기반의 AOP를 제공하는데 이는 즉, Spring은 대상 객체를 감싸는 프록시 객체를 생성하여 Aspect를 적용한다는 것이다. **이를 통해 대상 객체의 메서드 호출 전후 또는 예외 발생 시에 부가 기능을 삽입할 수 있습니다.**

유효성 검증에 실패하면 에러가 발생하는데, 로그를 확인해보면 이전의 MethodArgumentNotValidException 예외가 아닌 `ConstraintViolationException` 예외가 발생한다. <br>
`@Validated`는 `AOP 기반`으로 메소드 요청을 인터셉터하여 처리되는데, `@Validated`를 클래스 레벨에 선언하면 해당 클래스에 유효성 검증을 위한 AOP의 어드바이스 또는 인터셉터(`MethodValidationInterceptor`)가 등록된다. 그리고 **해당 클래스의 메소드들이 호출될 때 AOP의 포인트 컷으로써 요청을 가로채서 유효성 검증을 진행**한다. <br>
이러한 이유로 **`@Validated`를 사용하면 컨트롤러, 서비스, 레포지토리 등 계층에 무관하게 스프링 빈이라면 유효성 검증을 진행할 수 있다.**

[[Spring] @Valid와 @Validated를 이용한 유효성 검증의 동작 원리 및 사용법 예시 
](https://mangkyu.tistory.com/174) <br>
[@Valid @Validated 동작 원리 파헤치기](https://mangkyu.tistory.com/174)