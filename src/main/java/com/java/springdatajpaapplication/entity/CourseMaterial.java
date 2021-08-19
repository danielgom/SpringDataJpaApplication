package com.java.springdatajpaapplication.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long courseMaterialId;

    private String url;

    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            // We need a course in order to save the course material
            optional = false
    )
    @JoinColumn(
            name = "course_id",
            referencedColumnName = "courseId"
    )
    private Course course;

    @Override
    public String toString() {
        return "CourseMaterial{" +
                "courseMaterialId=" + courseMaterialId +
                ", url='" + url + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseMaterial that = (CourseMaterial) o;
        return Objects.equals(courseMaterialId, that.courseMaterialId) && Objects.equals(url, that.url) && Objects.equals(course, that.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseMaterialId, url, course);
    }
}
