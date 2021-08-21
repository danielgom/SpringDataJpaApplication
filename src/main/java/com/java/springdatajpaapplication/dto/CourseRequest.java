package com.java.springdatajpaapplication.dto;

import com.java.springdatajpaapplication.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseRequest {

    private Long courseId;
    private String title;
    private Integer credit;
    private String teacherName;
    private Student students;
}
