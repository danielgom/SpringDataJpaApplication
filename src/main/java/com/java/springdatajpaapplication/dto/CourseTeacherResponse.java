package com.java.springdatajpaapplication.dto;

import com.java.springdatajpaapplication.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseTeacherResponse {

    private Long courseId;
    private String title;
    private Integer credit;
    private List<Student> students;
}
