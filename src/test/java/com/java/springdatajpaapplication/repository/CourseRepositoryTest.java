package com.java.springdatajpaapplication.repository;

import com.java.springdatajpaapplication.entity.Course;
import com.java.springdatajpaapplication.entity.Teacher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CourseRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;

    @Test
    void getCourses() {
        List<Course> all = courseRepository.findAll();
        System.out.println(all);
    }

    @Test
    void getCourse(){
        Optional<Course> byId = courseRepository.findById(6L);
        byId.ifPresent(System.out::println);
    }

    @Test
    void saveCourseWithTeacher() {

        Teacher teacher = Teacher.builder()
                .firstName("Pyloa")
                .lastName("noua")
                .build();

        Course py = Course.builder()
                .title("Py")
                .credit(150)
                .teacher(teacher)
                .build();

        courseRepository.save(py);
    }

    @Test
    void findAllPagination() {
        Pageable firstPage = PageRequest.of(0, 3);
        Pageable secondPage = PageRequest.of(1, 1);

        List<Course> collect = courseRepository.findAll(firstPage).get().collect(Collectors.toList());

        System.out.println(collect);
        System.out.println(courseRepository.findAll(firstPage).getTotalElements());
        System.out.println(courseRepository.findAll(firstPage).getTotalPages());

        assertEquals(3, collect.size());

    }

    @Test
    void getCoursesByTeacherName() {

        List<Course> mandala = courseRepository.getCoursesByTeacherFirstName("Mandala");
        System.out.println(mandala);

        assertEquals(2, mandala.size());
    }


}