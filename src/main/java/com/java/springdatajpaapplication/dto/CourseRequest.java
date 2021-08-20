package com.java.springdatajpaapplication.dto;

import com.java.springdatajpaapplication.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Null;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseRequest {

    @Null
    private Long courseId;
    private String title;
    private Integer credit;
    private String teacherName;
    @Null
    private Student students;

}
