package com.java.springdatajpaapplication.service;

import com.java.springdatajpaapplication.dto.CourseRequest;
import com.java.springdatajpaapplication.dto.CourseResponse;
import com.java.springdatajpaapplication.dto.CourseTeacherResponse;
import com.java.springdatajpaapplication.entity.Course;
import com.java.springdatajpaapplication.entity.Teacher;
import com.java.springdatajpaapplication.exception.NewNotFoundException;
import com.java.springdatajpaapplication.repository.CourseRepository;
import com.java.springdatajpaapplication.repository.TeacherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;

    public CourseServiceImpl(CourseRepository courseRepository, TeacherRepository teacherRepository) {
        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;
    }

    public Set<CourseResponse> getAllCourses() {
        CompletableFuture<Set<Course>> allCourses = courseRepository.findAllCourses();

        return allCourses.thenApply(courses -> courses.stream()
                .map(this::mapToDto)
                .collect(Collectors.toUnmodifiableSet()))
                .join();
    }

    public Set<CourseTeacherResponse> getCoursesByTeacherFirstName(String firstName) {
        Teacher teacher = teacherRepository.getTeacherByFirstName(firstName)
                .orElseThrow(() -> new NewNotFoundException(String.format("teacher with name %s, not found", firstName)));

        CompletableFuture<Set<Course>> allCourses = courseRepository.findByTeacher(teacher);

        CompletableFuture<Set<CourseTeacherResponse>> courseList = allCourses.thenApply(courses -> courses.stream()
                .map(
                        course -> CourseTeacherResponse.builder()
                                .courseId(course.getCourseId())
                                .students(course.getStudents())
                                .credit(course.getCredit())
                                .title(course.getTitle())
                                .build())
                .collect(Collectors.toUnmodifiableSet()));

        return courseList.join();
    }

    public CourseResponse getCourseByTitle(String title) {

        Optional<Course> currentCourse = courseRepository.getCourseByTitle(title);
        return currentCourse.map(this::mapToDto)
                .orElseThrow(() -> new NewNotFoundException(String.format("course with title %s, not found", title)));
    }

    @Transactional
    public void createCourse(CourseRequest courseRequest) {
        courseRepository.save(mapToEntity(courseRequest));
    }

    @Transactional
    public void updateCourse(CourseRequest courseRequest, String title) {

        Teacher teacher = teacherRepository.getTeacherByFirstName(courseRequest.getTeacherName())
                .orElseThrow(() -> new NewNotFoundException(String.format("teacher with name %s, not found", courseRequest.getTeacherName())));

        CourseResponse currentCourse = this.getCourseByTitle(title);

        currentCourse.setTitle(courseRequest.getTitle());
        currentCourse.setCredit(courseRequest.getCredit());
        currentCourse.setTeacher(teacher);

        courseRepository.updateCourse(currentCourse.getCredit(), currentCourse.getTitle(), currentCourse.getTeacher().getTeacherId(),
                        currentCourse.getCourseId());
    }

    @Transactional
    public void updateCoursePartial(CourseRequest courseRequest, String title) {

        CourseResponse currentCourse = this.getCourseByTitle(title);

        if (courseRequest.getTitle() != null && !courseRequest.getTitle().equals("")) {
            currentCourse.setTitle(courseRequest.getTitle());
        }

        if (courseRequest.getCredit() != null) {
            currentCourse.setCredit(courseRequest.getCredit());
        }

        if (courseRequest.getTeacherName() != null && !courseRequest.getTeacherName().equals("")) {
            Teacher teacher = teacherRepository.getTeacherByFirstName(courseRequest.getTeacherName())
                    .orElseThrow(() -> new NewNotFoundException(String.format("teacher with name %s, not found", courseRequest.getTeacherName())));
            currentCourse.setTeacher(teacher);
        }

        if (currentCourse.getTeacher() == null) {
            courseRepository.updateCoursePartial(currentCourse.getCredit(), currentCourse.getTitle(), currentCourse.getCourseId());
        } else {
            courseRepository.updateCourse(currentCourse.getCredit(), currentCourse.getTitle(), currentCourse.getTeacher().getTeacherId(),
                            currentCourse.getCourseId());
        }
    }

    @Transactional
    public void deleteCourseByTitle(String title) {
        Optional<Course> currentCourse = courseRepository.getCourseByTitle(title);
        currentCourse.ifPresent(courseRepository::delete);
    }

    private CourseResponse mapToDto(Course course) {
        return CourseResponse.builder()
                .courseId(course.getCourseId())
                .credit(course.getCredit())
                .title(course.getTitle())
                .teacher(course.getTeacher())
                .build();
    }

    private Course mapToEntity(CourseRequest courseRequest) {
        return Course.builder()
                .credit(courseRequest.getCredit())
                .title(courseRequest.getTitle())
                .build();
    }
}
