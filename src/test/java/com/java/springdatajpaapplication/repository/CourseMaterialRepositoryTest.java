package com.java.springdatajpaapplication.repository;

import com.java.springdatajpaapplication.entity.Course;
import com.java.springdatajpaapplication.entity.CourseMaterial;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CourseMaterialRepositoryTest {

    @Autowired
    private CourseMaterialRepository courseMaterialRepository;

    @Test
    void saveCourseMaterial() {

        Course course = Course.builder()
                .credit(100)
                .title("DSA")
                .build();

        CourseMaterial courseMaterial = CourseMaterial.builder()
                .course(course)
                .url("www.google.com")
                .build();

        courseMaterialRepository.save(courseMaterial);
    }

    @Test
    void getAllCourseMaterials() {
        List<CourseMaterial> courseMaterials = courseMaterialRepository.findAll();

        System.out.println(courseMaterials);
        assertAll(() -> assertEquals(2, courseMaterials.size()));
    }
}