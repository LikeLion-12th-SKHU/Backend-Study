package com.example.jpa1.controller;

import com.example.jpa1.domain.department.Department;
import com.example.jpa1.domain.department.DepartmentRepository;
import com.example.jpa1.domain.student.Student;
import com.example.jpa1.domain.student.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("student")  // "/student" 경로에 대한 요청을 처리하는 핸들러 메소드에 적용하는 것
public class StudentController {

    // 컨트롤러에 의존성 주입
    // StudentRepository, DepartmentRepository의 인스턴스 자동 생성, 컨트롤러에서 사용 => 데이터베이스와의 상호작용 수행
    @Autowired StudentRepository studentRepository;
    @Autowired DepartmentRepository departmentRepository;

    //조회
    @RequestMapping("list")
    public String list(Model model) {   // Model 객체를 매개변수로 받아
        List<Student> students = studentRepository.findAll();   // 모든 학생 목록 조회
        model.addAttribute("students", students);   // model에 저장
        return "student/list";  //view 반환
    }
    // 생성
    @GetMapping("create")
    public String create(Model model) {
        Student student = new Student();    // 새로운 학생을 생성하기 위한 폼
        List<Department> departments = departmentRepository.findAll();  // 모든 학과 조회
        model.addAttribute("student", student);
        model.addAttribute("departments", departments);
        return "student/edit";  // 학생 생성 폼
    }
    @PostMapping("create")
    public String create(Model model, Student student) {    // Model객체와 Student객체 받기
        studentRepository.save(student);    // 학생 정보 저장
        return "redirect:list"; // 리다이렉트
    }
    //수정
    @GetMapping("edit")
    public String edit(Model model, @RequestParam("id") int id) {   // 특정 학생 정보 조회
        Student student = studentRepository.findById(id).get();
        List<Department> departments = departmentRepository.findAll();
        model.addAttribute("student", student);
        model.addAttribute("departments", departments);
        return "student/edit";  // 학생 수정 폼 보여줌
    }
    @PostMapping(value="edit", params="cmd=save")
    public String edit(Model model, Student student) {
        studentRepository.save(student);
        return "redirect:list";
    }
    //삭제
    @PostMapping(value="edit", params="cmd=delete")
    public String delete(Model model, @RequestParam("id") int id) {
        studentRepository.deleteById(id);
        return "redirect:list";
    }

}
