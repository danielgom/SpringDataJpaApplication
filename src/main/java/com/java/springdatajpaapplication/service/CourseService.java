package com.java.springdatajpaapplication.service;

import com.java.springdatajpaapplication.dto.CourseRequest;
import com.java.springdatajpaapplication.dto.CourseResponse;
import com.java.springdatajpaapplication.dto.CourseTeacherResponse;
import com.java.springdatajpaapplication.entity.Course;

import java.util.List;

public interface CourseService {

    List<CourseResponse> getAllCourses();

    List<CourseTeacherResponse> getCoursesByTeacherFirstName(String firstName);

    CourseResponse getCourseByTitle(String title);

    void createCourse(CourseRequest courseRequest);

    void updateCourse(CourseRequest courseRequest);

    void updateCoursePartial(Course course);

    void deleteCourseByTitle(String title);

}
