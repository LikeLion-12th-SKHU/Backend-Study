package com.rommmu.controller;

import com.rommmu.entity.Department;
import com.rommmu.entity.Student;
import com.rommmu.repository.DepartmentRepository;
import com.rommmu.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("student")
public class StudentController {

    @Autowired
    StudentRepository studentRepository;

//    @RequestMapping("list")
//    public String list(Model model) {
//        List<Student> students = studentRepository.findAll();
//        model.addAttribute("students", students);
//        return "student/list";
//    }

    @RequestMapping("test1")
    public String test1(Model model) {
        model.addAttribute("students",
                studentRepository.findByDepartmentProfessorsName("이몽룡"));
        return "student/list";
    }

    @RequestMapping("test2")
    public String test2(Model model) {
        model.addAttribute("students",
                studentRepository.findBySugangsLectureTitle("자료구조"));
        return "student/list";
    }
//
//    @RequestMapping("test3")
//    public String test3(Model model) {
//        model.addAttribute("students",
//                studentRepository.findByNameStartsWith("김"));
//        return "student/list";
//    }
//
//    @RequestMapping("test4")
//    public String test4(Model model) {
//        model.addAttribute("students",
//                studentRepository.findByDepartmentName("소프트웨어공학과"));
//        return "student/list";
//    }
//
//    @RequestMapping("test5")
//    public String test5(Model model) {
//        model.addAttribute("students",
//                studentRepository.findByDepartmentNameStartsWith("소프"));
//        return "student/list";
//    }
//
//    @RequestMapping("test6")
//    public String test6(Model model) {
//        model.addAttribute("students",
//                studentRepository.findByOrderByName());
//        return "student/list";
//    }
//
//    @RequestMapping("test7")
//    public String test7(Model model) {
//        model.addAttribute("students",
//                studentRepository.findByOrderByNameDesc());
//        return "student/list";
//    }
//
//    @RequestMapping("test8")
//    public String test8(Model model) {
//        model.addAttribute("students",
//                studentRepository.findByDepartmentIdOrderByNameDesc(1));
//        return "student/list";
//    }

//   Autowired DepartmentRepository departmentRepository;
//

//
//    @GetMapping("create")
//    public String create(Model model) {
//        Student student = new Student();
//        List<Department> departments = departmentRepository.findAll();
//        model.addAttribute("student", student);
//        model.addAttribute("departments", departments);
//        return "student/edit";
//    }
//
//    @PostMapping("create")
//    public String create(Model model, Student student) {
//        studentRepository.save(student);
//        return "redirect:list";
//    }
//
//    @GetMapping("edit")
//    public String edit(Model model, @RequestParam("id") int id) {
//        Student student = studentRepository.findById(id).get();
//        List<Department> departments = departmentRepository.findAll();
//        model.addAttribute("student", student);
//        model.addAttribute("departments", departments);
//        return "student/edit";
//    }
//
//    @PostMapping(value="edit", params="cmd=save")
//    public String edit(Model model, Student student) {
//        studentRepository.save(student);
//        return "redirect:list";
//    }
//
//    @PostMapping(value="edit", params="cmd=delete")
//    public String delete(Model model, @RequestParam("id") int id) {
//        studentRepository.deleteById(id);
//        return "redirect:list";
//    }
}
