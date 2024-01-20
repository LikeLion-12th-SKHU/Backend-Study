## 1. Rest Controller
Spring MVC 프레임워크에서 REST API를 구현할 때, 컨트롤러에 `@RestController` 어노테이션을 붙어야 한다. `@RestController`의 액션 메서드는 데이터를 클라이언트에 전송하기 때문에 view가 필요없다. 따라서 뷰의 이름을 리턴할 필요가 없고 클라이언트에 전송하기 위한 데이터를 리턴한다.
### `@ResponseBody`
컨트롤러의 액션 메서드 앞에 `@ResponseBody`를 붙이면 이 액션 메서드가 리턴하는 Java 객체는 자동으로 JSON 포맷으로 변환되어 클라이언트에 전송된다. <br>
컨트롤러 앞에 `@ResponseBody` 어노테이션을 붙이면 `@ResponseBody`를 붙일 필요가 없다.
### `@RequestBody`
서버가 클라이언트에 전송하는 데이터와, 클라이언트가 서버에 전달하는 `request parameter` 데이터도 JSON 포맷인 것이 좋다. 이렇게 JSON 포맷으로 전송된 `request parameter` 데이터를 받을 액션 메서드의 파라미터 변수에는 `@RequestBody`를 붙여주어야 한다. **`request parameter` 데이터가 JSON 포맷이 아니라면 `@RequestBody`를 붙이지 않아야 한다.**

## 2. Request Method
클라이언트가 서버에 request를 전달하는 방식을 request method 라고 한다.
- `GET` 서버에 데이터를 요청할 때
- `POST` 서버에 저장할 새 데이터를 전송할 때
- `PUT` 서버의 기존 데이터를 수정하기 위해 전송할 때
- `DELETE` 서버의 데이터 삭제를 요청할 때

## 3. REST API
### `@PathVariable`
요청된 URL이 아래와 같다면 <br>
http://localhost:8080/jpa10/api/student/3
<br>
URL에 들어있는 id 값인 3을 받기 위한 액션 메서드는 다음과 같이 구현한다.

```java
@RequestMapping("api/student/{id}") public Student student(@PathVariable("id") int id)
```
### 액션 메서드 URL
`@RequestMapping("url1")` 어노테이션이 컨트롤러 클래스에 붙어있고, `@RequestMapping("url2")` 어노테이션이 액션 메서드에 붙어있다면 액션 메서드를 호출하기위한 URL은 "url1/url2" 이다.
```java
@Controller
@RequestMapping("student")
public class StudentController { @RequestMapping("list")
public String list(...) { 
    ...
    }
}
```
## 4. JPA Entity 클래스
### `@Entity`
JPA Entity 클래스 앞에 `@Entity`를 붙여야 한다.
### `@Id`
기본키에 해당하는 멤버 변수에 `@Id` 어노테이션을 붙여야 한다.
### `@GeneratedValue(strategy = GenerationType.IDENTITY)`
기본키가 Auto Increment 필드이거나 Idenity 필드인 경우에 이 어노테이션을 붙여야 한다.
## 5. JPA Repository
```java
package com.rommmu.repository;

import com.rommmu.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
}
```
DepartmentRepository 인터페이스를 선언하면, `Department` (엔티티 클래스 이름) `Integer` (primary key 타입)

- `departmentRepository.save(department)` <br> 
Department 객체를 department 테이블에 저장한다. Department 객체의 기본키 속성값이 `0이면 INSERT`, `0이 아니면 UPDATE`.
- `departmentRepository.saveAll(departmentList)` <br> 
Department 객체 목록을 department 테이블에 저장한다.
- `departmentRepository.delete(department)` <br>
Department 객체의 기본키 값과 일치하는 레코드가 삭제된다.
- `departmentRepository.deleteById(id)` <br>
기본키 값과 일치하는 레코드가 삭제된다.
- `departmentRepository.deleteAll(departmentList)` <br>
departmentList 목록에 들어있는 Department 객체 목록을 테이블에서 삭제한다.
- `departmentRepository.deleteAll()` <br>
department 테이블의 모든 레코드들을 삭제한다.
- `departmentRepository.count()` <br>
department 테이블의 전체 레코드 수를 리턴한다.
- `departmentRepository.existsById(id)` <br>
department 테이블에서 id에 해당하는 레코드가 있는지 true/false를 리턴한다.