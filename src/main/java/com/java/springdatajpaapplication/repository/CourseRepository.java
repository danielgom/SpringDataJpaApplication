package com.java.springdatajpaapplication.repository;

import com.java.springdatajpaapplication.entity.Course;
import com.java.springdatajpaapplication.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Async
    CompletableFuture<List<Course>> findByTeacher(Teacher teacher);

    @Async
    @Query(value = "SELECT c.course_id, c.credit, c.title, c.teacher_id, t.first_name, t.last_name \n" +
            "FROM schooldb.course as c \n" +
            "LEFT JOIN schooldb.teacher as t \n" +
            "ON c.teacher_id = t.teacher_id", nativeQuery = true)
    CompletableFuture<List<Course>> findAllCourses();

    @Query(value = "SELECT c.course_id, c.credit, c.title, c.teacher_id, t.first_name, t.last_name \n" +
            "FROM schooldb.course as c LEFT JOIN schooldb.teacher as t ON c.teacher_id = t.teacher_id WHERE c.title = :title"
            ,nativeQuery = true)
    Optional<Course> getCourse(@Param("title") String title);

    @Async
    @Modifying
    @Query(value = "UPDATE schooldb.course c SET c.credit = :credit, c.title = :title WHERE c.course_id = :courseId", nativeQuery = true)
    void updateCourse(
            @Param("credit") Integer credit,
            @Param("title") String title,
            @Param("courseId") Long courseId
    );
}
