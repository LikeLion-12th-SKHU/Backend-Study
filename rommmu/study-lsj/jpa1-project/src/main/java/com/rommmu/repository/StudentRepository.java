package com.rommmu.repository;

import com.rommmu.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer>{
    List<Student> findBySugangsLectureTitle(String title);
    List<Student> findByDepartmentProfessorsName(String name);
//    Student findByStudentNo(String studentNo);
//    // SELECT * FROM Person WHERE studentNo = #{studentNo}
//    List<Student> findByName(String name);
//    // SELECT * FROM Person WHERE name = #{name}
//    List<Student> findByNameStartsWith(String name);
//    // SELECT * FROM Person WHERE name LIKE '김%'
//    List<Student> findByDepartmentName(String name);
//    List<Student> findByDepartmentNameStartsWith(String name);
//
//    List<Student> findByOrderByName();
//    List<Student> findByOrderByNameDesc();
//    List<Student> findByDepartmentIdOrderByNameDesc(int id);
}
