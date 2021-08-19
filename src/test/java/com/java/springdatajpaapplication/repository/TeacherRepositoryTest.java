package com.java.springdatajpaapplication.repository;

import com.java.springdatajpaapplication.entity.Course;
import com.java.springdatajpaapplication.entity.CourseMaterial;
import com.java.springdatajpaapplication.entity.Teacher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TeacherRepositoryTest {

    @Autowired
    private TeacherRepository teacherRepository;

    @Test
    void saveTeacher() {

        Course dbaCourse = Course.builder()
                .credit(100)
                .title("ASZ")
                .build();

        Course javaCourse = Course.builder()
                .credit(100)
                .title("VIM")
                .build();

        Teacher teacher = Teacher.builder()
                .firstName("Oopit")
                .lastName("kilaue")
                //.courses(List.of(dbaCourse, javaCourse))
                .build();

        teacherRepository.save(teacher);
    }

    @Test
    void getTeacher() {

        Optional<Teacher> byId = teacherRepository.findById(5L);

        byId.ifPresent(System.out::println);
    }


}