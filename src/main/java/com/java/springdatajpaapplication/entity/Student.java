package com.java.springdatajpaapplication.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(
        name = "tbl_student",
        uniqueConstraints = @UniqueConstraint(
                name = "emailid_unique",
                columnNames = "email_address"
        )
)
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long studentId;

    private String firstName;
    private String lastName;

    @Column(name = "email_address",
            nullable = false
    )
    private String emailId;

    @Embedded
    private Guardian guardian;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(studentId, student.studentId) && Objects.equals(firstName, student.firstName) &&
                Objects.equals(lastName, student.lastName) && Objects.equals(emailId, student.emailId) &&
                Objects.equals(guardian, student.guardian);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, firstName, lastName, emailId, guardian);
    }
}
