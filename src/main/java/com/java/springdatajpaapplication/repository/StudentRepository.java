package com.java.springdatajpaapplication.repository;

import com.java.springdatajpaapplication.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByFirstName(String firstName);

    List<Student> findByFirstNameContaining(String name);

    List<Student> findByLastNameNotNull();

    List<Student> findByGuardianName(String guardianName);

    List<Student> findByFirstNameAndLastName(String firstName, String lastName);

    // Based on classes rather than tables (JPQL)
    @Query("select s from Student s where s.emailId = ?1")
    Optional<Student> getByEmailAddress(String emailId);

    // Native query
    @Query(value = "SELECT s.first_name FROM schooldb.tbl_student s WHERE s.email_address = ?1", nativeQuery = true)
    String getStudentFirstNameEmailAddressNative(String emailId);

    // Native query Named param
    @Query(value = "SELECT s.first_name FROM schooldb.tbl_student s WHERE s.email_address = :emailId", nativeQuery = true)
    String getStudentFirstNameEmailAddressNativeNamedParam(@Param("emailId") String emailId);


    @Modifying
    @Transactional
    @Query(value = "UPDATE schooldb.tbl_student set first_name = ?1 WHERE email_address = ?2", nativeQuery = true)
    int updateStudentNameByEmailId(String firstName, String emailId);
}
