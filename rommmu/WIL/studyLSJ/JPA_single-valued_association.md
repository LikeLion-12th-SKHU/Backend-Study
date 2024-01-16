## Single-Valued Association
### `@ManyToOne`
```java
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;

    @ManyToOne
    @JoinColumn(name = "departmentId")
    Department department;
}
```
`@ManyToOne`
:: 학생 여러명이 학과 1개에 소속되어 있다 <br>

`@JoinColumn(name = "departmentId")`
:: 학생 테이블의 외래키는 departmentId 필드이다.<br>

`Department department`
:: 학생이 속한 학과는 1개이다 <br>

즉, 엔터티 클래스들 사이의 1 대 다 관계를 구현할 때, 외래키를 포함하는 엔터티 클래스에 `@ManyToOne` 어노테이션을 사용하여, 상대측 엔터티 객체 한 개를 구현한다. 그리고 외래키 속성은 생략한다. <br>

**departmentId 외래키가 student 테이블에 포함되어 있으므로** Student 클래스에 멤버 변수를 구현하고 departmentId 속성은 생략한다.