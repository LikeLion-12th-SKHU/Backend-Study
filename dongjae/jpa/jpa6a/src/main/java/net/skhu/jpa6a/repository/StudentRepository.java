package net.skhu.jpa6a.repository;

import net.skhu.jpa6a.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Optional<Student> findByStudentNo(String studentNo);
}
