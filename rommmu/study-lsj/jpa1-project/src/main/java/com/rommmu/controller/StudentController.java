package com.rommmu.controller;

import com.rommmu.entity.Department;
import com.rommmu.entity.Student;
import com.rommmu.model.StudentEdit;
import com.rommmu.repository.DepartmentRepository;
import com.rommmu.repository.StudentRepository;
import com.rommmu.service.DepartmentService;
import com.rommmu.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("student")
public class StudentController {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    StudentService studentService;
    @Autowired
    DepartmentService departmentService;

//    @RequestMapping("test1")
//    public String test1(Model model) {
//        model.addAttribute("students",
//                studentRepository.findByDepartmentProfessorsName("이몽룡"));
//        return "student/list";
//    }
//
//    @RequestMapping("test2")
//    public String test2(Model model) {
//        model.addAttribute("students",
//                studentRepository.findBySugangsLectureTitle("자료구조"));
//        return "student/list";
//    }
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
    @GetMapping("list")
    public String list(Model model) {
        List<Student> students = studentService.findAll();
        model.addAttribute("students", students);
        return "student/list";
    }

    @GetMapping("create")
    public String create(Model model) {
        StudentEdit studentEdit = new StudentEdit();
        List<Department> departments = departmentService.findAll();
        model.addAttribute("studentEdit", studentEdit);
        model.addAttribute("departments", departments);
        return "student/edit";
    }

    @PostMapping("create")
    public String create(Model model,
                         @Valid StudentEdit studentEdit,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("departments", departmentService.findAll());
            return "student/edit";
        }
        Student student2 = studentService.findByStudentNo(studentEdit.getStudentNo());
        if (student2 != null) {
            bindingResult.rejectValue("studentNo", null, "학번이 중복됩니다.");
            model.addAttribute("departments", departmentService.findAll());
            return "student/edit";
        }
        studentService.insert(studentEdit);
        return "redirect:list";
    }

    @GetMapping("edit")
    public String edit(Model model, int id) {
        StudentEdit studentEdit = studentService.findOne(id);
        List<Department> departments = departmentService.findAll();
        model.addAttribute("studentEdit", studentEdit);
        model.addAttribute("departments", departments);
        return "student/edit";
    }

    @PostMapping("edit")
    public String edit(Model model,
                       @Valid StudentEdit studentEdit, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("departments", departmentService.findAll());
            return "student/edit";
        }
        Student student2 = studentService.findByStudentNo(studentEdit.getStudentNo());
        if (student2 != null && student2.getId() != studentEdit.getId()) {
            bindingResult.rejectValue("studentNo", null, "학번이 중복됩니다.");
            model.addAttribute("departments", departmentService.findAll());
            return "student/edit";
        }
        studentService.update(studentEdit);
        return "redirect:list";
    }

    @GetMapping("delete")
    public String delete(Model model, int id) {
        studentService.delete(id);
        return "redirect:list";
    }
}
