package com.java.springdatajpaapplication.dto;

import com.java.springdatajpaapplication.entity.Teacher;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseResponse {

    private Long courseId;
    private String title;
    private Integer credit;
    private Teacher teacher;
}
