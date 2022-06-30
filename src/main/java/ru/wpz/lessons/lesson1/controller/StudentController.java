package ru.wpz.lessons.lesson1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.wpz.lessons.lesson1.entity.Student;
import ru.wpz.lessons.lesson1.service.StudentService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/")
    public String getAllStudents(Model model) {
        List<Student> students =  studentService.getAll();
        model.addAttribute("students", students);
        return "student";
    }

    @GetMapping("/add")
    public String getStudentAddFrom(Model model) {
        model.addAttribute("student", new Student());
        return "add_student";
    }

    @PostMapping("/add")
    @Transactional
    public String saveStudent(@Valid Student student, Model model) {
        studentService.save(student);
        List<Student> students =  studentService.getAll();
        model.addAttribute("students", students);
        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id, Model model) {
        studentService.deleteById(id);
        List<Student> students =  studentService.getAll();
        model.addAttribute("students", students);
        return "redirect:/";
    }
}
