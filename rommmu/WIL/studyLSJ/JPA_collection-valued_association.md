## Collection-Valued association
### `@OneToMany`
학과(department) 한 개에 소속된 학생은 여러명이다. Department 객체에 소속 학생들 목록에 해당하는 속성을 구현할 수 있다.
```java
@OneToMany(mappedBy="department")
List<Student> students;
```
이때 Student 엔터티 클래스에 `@ManyToOne department` 속성이 구현되어 있어야 한다.

> 관계가 있는 두 엔터티 클래스 사이 관계 속성을 구현할때, 외래키가 있는 엔터티 클래스에 **`@ManyToOne` 속성을 먼저 구현한 후** 반대쪽 클래스에 **`@OneToMany` 속성**을 구현할 수 있다. <br> 즉, Single-Valued Association 구현은 필수이지만, Collection-Valued Association 구현은 선택이다.

