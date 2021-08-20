package com.java.springdatajpaapplication.controller;

import com.java.springdatajpaapplication.dto.CourseRequest;
import com.java.springdatajpaapplication.dto.CourseResponse;
import com.java.springdatajpaapplication.dto.CourseTeacherResponse;
import com.java.springdatajpaapplication.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @PostMapping(value = "/")
    public ResponseEntity<Void> createCourse(@Valid @RequestBody CourseRequest courseRequest) {
        courseService.createCourse(courseRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping(value = "/")
    public ResponseEntity<Void> updateCourse(@Valid @RequestBody CourseRequest courseRequest) {
        courseService.updateCourse(courseRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
