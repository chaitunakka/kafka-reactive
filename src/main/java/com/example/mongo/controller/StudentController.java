package com.example.mongo.controller;

import com.example.mongo.model.Student;
import com.example.mongo.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

//@RestController
@RequiredArgsConstructor
//@RequestMapping("/students")
public class StudentController {

    private final StudentRepository studentRepository;

    @GetMapping
    public List<Student> getStudents() {
        Sort sort = Sort.sort(Student.class).by(Student::getName).descending();
        return studentRepository.findAll(sort);
    }

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable String id) throws Exception {
        return studentRepository.findById(id).orElseThrow(Exception::new);
    }

    @GetMapping("/gender/{gender}")
    public List<Student> getStudentByGender(@PathVariable String gender) {
        Sort sort = Sort.sort(Student.class).by(Student::getGender).ascending();
        return studentRepository.findByGender(Student.Gender.valueOf(gender), sort);
    }
}
