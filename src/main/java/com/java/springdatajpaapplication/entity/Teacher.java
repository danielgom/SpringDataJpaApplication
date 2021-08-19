package com.java.springdatajpaapplication.entity;

import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Teacher {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long teacherId;

    private String firstName;
    private String lastName;

    @OneToMany(mappedBy = "teacher", fetch = FetchType.EAGER)
    private List<Course> courses;

    @Override
    public String toString() {
        return "Teacher{" +
                "teacherId=" + teacherId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
