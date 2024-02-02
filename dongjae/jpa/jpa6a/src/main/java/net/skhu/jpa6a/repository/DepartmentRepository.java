package net.skhu.jpa6a.repository;

import net.skhu.jpa6a.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
}
