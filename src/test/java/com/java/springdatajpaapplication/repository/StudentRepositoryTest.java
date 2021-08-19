package com.java.springdatajpaapplication.repository;

import com.java.springdatajpaapplication.entity.Guardian;
import com.java.springdatajpaapplication.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Only use this whenever we want to hit the database directly, otherwise use @DataJpaTest
@SpringBootTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    void testSaveStudent() {
        Student student = Student.builder()
                .emailId("daniel.gomez@testing.com")
                .firstName("Daniel")
                .lastName("Gomez")
                .build();

        studentRepository.save(student);
    }

    @Test
    void TestSaveStudentWithGuardianDetails() {

        Guardian guardian = Guardian.builder()
                .email("guard.daniel@test.com")
                .name("Dany")
                .mobile("54394988502")
                .build();

        Student student = Student.builder()
                .emailId("daniel.go@testint.com")
                .firstName("Daniel")
                .lastName("Alvarez")
                .guardian(guardian)
                .build();

        studentRepository.save(student);
    }

    @Test
    void TestFindStudentByFirstName() {
        List<Student> students = studentRepository.findByFirstName("Daniel");

        assertAll(() -> assertEquals(2, students.size()));
    }

    @Test
    void testFindStudentByFirstNameContaining() {
        List<Student> students = studentRepository.findByFirstNameContaining("Da");
        assertAll(() -> assertEquals(2, students.size()));
    }

    @Test
    void testFindStudentByLastNameNotNull() {
        List<Student> students = studentRepository.findByLastNameNotNull();
        assertAll(() -> assertEquals(2, students.size()));
    }

    @Test
    void testFindStudentByGuardianName() {
        List<Student> students = studentRepository.findByGuardianName("Dany");
        assertAll(() -> assertEquals(1, students.size()));
    }

    @Test
    void testFindByFirstNameAndLastName() {
        List<Student> byFirstNameAndAndLastName = studentRepository.findByFirstNameAndLastName("Daniel", "Gomez");
        assertAll(() -> assertEquals(1, byFirstNameAndAndLastName.size()));
    }

    @Test
    void testFindByEmailIdQuery() {
        Student student = studentRepository.getByEmailAddress("daniel.gomez@testing.com").get();
        assertAll(
                () -> assertEquals("Daniel", student.getFirstName()),
                () -> assertEquals("Gomez", student.getLastName()));

    }

    @Test
    void testFindFirstNameByEmailIdQueryNative() {
        String firstName = studentRepository.getStudentFirstNameEmailAddressNative("daniel.gomez@testing.com");
        assertAll(
                () -> assertEquals("Daniel", firstName));
    }

    @Test
    void getStudentFirstNameEmailAddressNativeNamedParam() {
        String firstName = studentRepository.getStudentFirstNameEmailAddressNativeNamedParam("daniel.gomez@testing.com");
        assertAll(
                () -> assertEquals("Daniel", firstName));
    }

    @Test
    void updateStudentNameByEmailId() {
        int rows = studentRepository.updateStudentNameByEmailId("DanielG", "daniel.go@testint.com");
        assertEquals(1, rows);
    }

    @Test
    void getStudents() {
        List<Student> studentList = studentRepository.findAll();

        assertAll(() -> assertEquals(2, studentList.size()));
    }
}