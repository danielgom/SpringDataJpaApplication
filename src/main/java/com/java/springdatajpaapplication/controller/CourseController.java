package com.java.springdatajpaapplication.controller;

import com.java.springdatajpaapplication.dto.CourseRequest;
import com.java.springdatajpaapplication.dto.CourseResponse;
import com.java.springdatajpaapplication.dto.CourseTeacherResponse;
import com.java.springdatajpaapplication.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    //Only teacher can access
    @GetMapping(value = "/teacher/{firstName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<CourseTeacherResponse>> getAllCoursesByTeacherFirstName(@PathVariable String firstName) {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.getCoursesByTeacherFirstName(firstName));
    }

    // Any student can access
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<CourseResponse>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.getAllCourses());
    }

    // Any student can access
    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    @GetMapping(value = "/title/{title}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CourseResponse> getByTitle(@PathVariable String title) {
        return ResponseEntity.status(HttpStatus.OK).body((courseService.getCourseByTitle(title)));
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@Valid @RequestBody CourseRequest courseRequest) {
        courseService.createCourse(courseRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping(value = "/title/{title}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@Valid @RequestBody CourseRequest courseRequest,
                                             @PathVariable String title) {
        courseService.updateCourse(courseRequest, title);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping(value = "/title/{title}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> partialUpdate(@Valid @RequestBody CourseRequest courseRequest,
                                             @PathVariable String title) {
        courseService.updateCoursePartial(courseRequest, title);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping(value = "/title/{title}")
    public ResponseEntity<Void> delete(@PathVariable String title) {
        courseService.deleteCourseByTitle(title);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
