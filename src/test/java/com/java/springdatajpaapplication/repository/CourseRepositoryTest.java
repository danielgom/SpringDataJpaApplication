package com.java.springdatajpaapplication.repository;

import com.java.springdatajpaapplication.entity.Course;
import com.java.springdatajpaapplication.entity.Teacher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CourseRepositoryTest {

    /*
    @Autowired
    private CourseRepository courseRepository;

    @Test
    @DisplayName("Should get courses by teacher")
    void shouldGetCourseListByTeacher() {

        Teacher teacher = Teacher.builder()
                .teacherId(8L)
                .firstName("Oopit")
                .lastName("kilaue")
                .build();

        Course courseT1 = Course.builder().courseId(null).credit(100).title("MIC").teacher(teacher).build();

        courseRepository.save(courseT1);

        Set<Course> courses = courseRepository.findByTeacher(teacher).join();

        assertEquals(1, courses.size());

    }

     */

}