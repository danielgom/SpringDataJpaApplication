package com.java.springdatajpaapplication.repository;

import com.java.springdatajpaapplication.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query(value = "SELECT * FROM schooldb.course c LEFT JOIN schooldb.teacher t ON c.teacher_id = t.teacher_id WHERE t.first_name='Mandala'", nativeQuery = true)
    List<Course> getCoursesByTeacherFirstName(String firstName);
}
