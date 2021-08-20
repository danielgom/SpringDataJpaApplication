package com.java.springdatajpaapplication.service;

import com.java.springdatajpaapplication.dto.CourseRequest;
import com.java.springdatajpaapplication.dto.CourseResponse;
import com.java.springdatajpaapplication.dto.CourseTeacherResponse;

import java.util.Set;

public interface CourseService {

    Set<CourseResponse> getAllCourses();

    Set<CourseTeacherResponse> getCoursesByTeacherFirstName(String firstName);

    CourseResponse getCourseByTitle(String title);

    void createCourse(CourseRequest courseRequest);

    void updateCourse(CourseRequest courseRequest, String title);

    void updateCoursePartial(CourseRequest courseRequest, String title);

    void deleteCourseByTitle(String title);

}
