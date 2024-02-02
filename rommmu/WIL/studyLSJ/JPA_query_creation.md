## query creation (쿼리 메서드 자동 구현)
```java
public interface StudentRepository extends JpaRepository<Student, Integer>  {

    List<Student> findByName(String name);
    Student       findByStudentNo(String studentNo);
    List<Student> findByNameStartsWith(String name);
    List<Student> findByDepartmentName(String name);
    List<Student> findByDepartmentNameStartsWith(String name);

    List<Student> findAllByOrderByName();
    List<Student> findAllByOrderByNameDesc();
    List<Student> findByDepartmentIdOrderByNameDesc(int id);
}
```
Spring Data JPA는 repository에 선언된 abstract 메서드들도 자동으로 구현해준다.

[쿼리메서드 이름 규칙의 예](https://o365skhu-my.sharepoint.com/:w:/r/personal/lsj_office_skhu_ac_kr/_layouts/15/Doc.aspx?sourcedoc=%7B7FD5B96F-5CB8-4683-B77E-175341CE7025%7D&file=04%20JPA%20query%20creation.docx&action=default&mobileredirect=true)