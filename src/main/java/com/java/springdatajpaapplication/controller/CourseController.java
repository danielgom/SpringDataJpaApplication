package com.java.springdatajpaapplication.controller;

import com.java.springdatajpaapplication.dto.CourseResponse;
import com.java.springdatajpaapplication.dto.CourseTeacherResponse;
import com.java.springdatajpaapplication.entity.Course;
import com.java.springdatajpaapplication.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    //Only teacher can access
    @GetMapping(value = "/teacher/{firstName}", produces = "application/json")
    public ResponseEntity<List<CourseTeacherResponse>> getAllCoursesByTeacherFirstName(@PathVariable String firstName) {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.getCoursesByTeacherFirstName(firstName));
    }

    // Any student can access
    @GetMapping(value = "/")
    public ResponseEntity<List<CourseResponse>> getAllCourses() {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.getAllCourses());
    }

    // Any student can access
    @GetMapping(value = "/title/{title}")
    public ResponseEntity<CourseResponse> getCourseByTitle(@PathVariable String title) {
        return ResponseEntity.status(HttpStatus.OK).body((courseService.getCourseByTitle(title)));
    }


}
