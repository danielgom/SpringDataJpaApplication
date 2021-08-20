package com.java.springdatajpaapplication.repository;

import com.java.springdatajpaapplication.entity.Course;
import com.java.springdatajpaapplication.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Async
    CompletableFuture<Set<Course>> findByTeacher(Teacher teacher);

    @Async
    @Query(value = """
            SELECT c.course_id, c.credit, c.title, c.teacher_id, t.first_name, t.last_name\s
            FROM schooldb.course as c\s
            LEFT JOIN schooldb.teacher as t\s
            ON c.teacher_id = t.teacher_id""", nativeQuery = true)
    CompletableFuture<Set<Course>> findAllCourses();

    Optional<Course> getCourseByTitle(String title);

    @Modifying
    @Query(value = "UPDATE schooldb.course SET credit = :credit, title = :title, teacher_id = :teacherId WHERE course_id = :courseId", nativeQuery = true)
    void updateCourse(
            @Param("credit") Integer credit,
            @Param("title") String title,
            @Param("teacherId") Long teacherId,
            @Param("courseId") Long courseId
    );

    @Modifying
    @Query(value = "UPDATE schooldb.course SET credit = :credit, title = :title WHERE course_id = :courseId", nativeQuery = true)
    void updateCoursePartial(
            @Param("credit") Integer credit,
            @Param("title") String title,
            @Param("courseId") Long courseId
    );
}
