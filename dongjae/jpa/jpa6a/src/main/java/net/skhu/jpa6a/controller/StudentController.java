package net.skhu.jpa6a.controller;

import lombok.RequiredArgsConstructor;
import net.skhu.jpa6a.entity.Department;
import net.skhu.jpa6a.entity.Student;
import net.skhu.jpa6a.repository.DepartmentRepository;
import net.skhu.jpa6a.repository.StudentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentRepository studentRepository;
    private final DepartmentRepository departmentRepository;

    @GetMapping("/list")
    public String list(Model model) {
        List<Student> students = studentRepository.findAll();
        model.addAttribute("students", students);
        return "student/list";
    }

    @GetMapping("/create")
    public String create(Model model) {
        Student student = new Student();
        List<Department> departments = departmentRepository.findAll();
        model.addAttribute("student", student);
        model.addAttribute("departments", departments);
        return "student/edit";
    }

    @PostMapping("/create")
    public String create(Model model, Student student) {
        studentRepository.save(student);
        return "redirect:list";
    }

    @GetMapping("/edit")
    public String edit(Model model, int id) {
        Student student = studentRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        List<Department> departments = departmentRepository.findAll();
        model.addAttribute("student", student);
        model.addAttribute("departments", departments);
        return "student/edit";
    }

    @PostMapping("/edit")
    public String edit(Model model, Student student) {
        studentRepository.save(student);
        return "redirect:list";
    }

    @GetMapping("delete")
    public String delete(Model model, int id) {
        studentRepository.deleteById(id);
        return "redirect:list";
    }

}
