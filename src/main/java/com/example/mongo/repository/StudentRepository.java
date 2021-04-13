package com.example.mongo.repository;

import com.example.mongo.model.Student;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends CrudRepository<Student, String> {

    List<Student> findAll(Sort sort);
    Optional<Student> findById(String id);
    List<Student> findByGender(Student.Gender gender, Sort sort);
}
