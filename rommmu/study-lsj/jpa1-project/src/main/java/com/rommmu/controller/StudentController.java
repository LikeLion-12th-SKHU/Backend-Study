package com.rommmu.controller;

import com.rommmu.entity.Department;
import com.rommmu.entity.Student;
import com.rommmu.model.StudentEdit;
import com.rommmu.repository.DepartmentRepository;
import com.rommmu.repository.StudentRepository;
import com.rommmu.service.DepartmentService;
import com.rommmu.service.StudentService;
import com.rommmu.validation.ValidationSequence;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
                         @Validated(ValidationSequence.class) StudentEdit studentEdit, BindingResult bindingResult) {
        try {
            studentService.insert(studentEdit, bindingResult);
            return "redirect:list";
        }
        catch (Exception e) {
            model.addAttribute("departments", departmentService.findAll());
            bindingResult.rejectValue("", null, "등록할 수 없습니다.");
            return "student/edit";
        }
    }

    @GetMapping("edit")
    public String edit(Model model, int id) {
        StudentEdit studentEdit = studentService.findOne(id);
        List<Department> departments = departmentService.findAll();
        model.addAttribute("studentEdit", studentEdit);
        model.addAttribute("departments", departments);
        return "student/edit";
    }

    @PostMapping(value="edit", params="cmd=save")
    public String edit(Model model,
                       @Validated(ValidationSequence.class) StudentEdit studentEdit, BindingResult bindingResult) {
        try {
            studentService.update(studentEdit, bindingResult);
            return "redirect:list";
        }
        catch (Exception e) {
            model.addAttribute("departments", departmentService.findAll());
            bindingResult.rejectValue("", null, "수정할 수 없습니다.");
            return "student/edit";
        }
    }

    @PostMapping(value="edit", params="cmd=delete")
    public String delete(Model model,
                         StudentEdit studentEdit, BindingResult bindingResult) {
        try {
            studentService.delete(studentEdit.getId());
            return "redirect:list";
        }
        catch (Exception e) {
            model.addAttribute("departments", departmentService.findAll());
            bindingResult.rejectValue("", null, "삭제할 수 없습니다.");
            return "student/edit";
        }
    }
}
