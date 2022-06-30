package ru.wpz.lessons.lesson1.service;

import org.springframework.stereotype.Service;
import ru.wpz.lessons.lesson1.dao.StudentRepository;
import ru.wpz.lessons.lesson1.entity.Student;


import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    public List<Student> save(Student student){
        studentRepository.save(student) ;
        return studentRepository.findAll();
    }

    public void deleteById( Long id) {
        studentRepository.deleteById(id);
    }
}
