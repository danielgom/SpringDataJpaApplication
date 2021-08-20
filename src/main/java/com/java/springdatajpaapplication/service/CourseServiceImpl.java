package com.java.springdatajpaapplication.service;

import com.java.springdatajpaapplication.dto.CourseRequest;
import com.java.springdatajpaapplication.dto.CourseResponse;
import com.java.springdatajpaapplication.dto.CourseTeacherResponse;
import com.java.springdatajpaapplication.entity.Course;
import com.java.springdatajpaapplication.entity.Teacher;
import com.java.springdatajpaapplication.exception.CourseNotFoundException;
import com.java.springdatajpaapplication.exception.TeacherNotFoundException;
import com.java.springdatajpaapplication.repository.CourseRepository;
import com.java.springdatajpaapplication.repository.TeacherRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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

    @Override
    public List<CourseResponse> getAllCourses() {
        List<Course> allCourses = courseRepository.findAllCourses();

        return allCourses.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseTeacherResponse> getCoursesByTeacherFirstName(String firstName) {
        Teacher teacher = teacherRepository.getTeacherByFirstName(firstName)
                .orElseThrow(() -> new TeacherNotFoundException(String.format("teacher with name %s, not found", firstName)));

        CompletableFuture<List<Course>> allCourses = courseRepository.findByTeacher(teacher);

        CompletableFuture<List<CourseTeacherResponse>> courseList = allCourses.thenApply(courses -> courses.stream()
                .map(
                        course -> CourseTeacherResponse.builder()
                                .courseId(course.getCourseId())
                                .students(course.getStudents())
                                .credit(course.getCredit())
                                .title(course.getTitle())
                                .build())
                .toList());

        return courseList.join();
    }

    @Override
    public CourseResponse getCourseByTitle(String title) {

        Optional<Course> currentCourse = courseRepository.getCourse(title);
        return currentCourse.map(this::mapToDto)
                .orElseThrow(() -> new CourseNotFoundException(String.format("course with title %s, not found", title)));

    }

    @Transactional
    @Override
    public void createCourse(CourseRequest courseRequest) {
        courseRepository.save(mapToEntity(courseRequest));
    }

    @Transactional
    @Override
    public void updateCourse(CourseRequest courseRequest) {

        CourseResponse currentCourse = this.getCourseByTitle(courseRequest.getTitle());

        Teacher teacher = teacherRepository.getTeacherByFirstName(courseRequest.getTeacherName())
                .orElseThrow(() -> new TeacherNotFoundException(String.format("teacher with name %s, not found", courseRequest.getTeacherName())));

        currentCourse.setTitle(courseRequest.getTitle());
        currentCourse.setCredit(courseRequest.getCredit());
        currentCourse.setTeacher(teacher);

        courseRepository.updateCourse(currentCourse.getCredit(), currentCourse.getTitle(), currentCourse.getTeacher().getTeacherId(),
                        currentCourse.getCourseId());
    }

    @Override
    public void updateCoursePartial(Course course) {

        CourseResponse currentCourse = this.getCourseByTitle(course.getTitle());

        if (!course.getTitle().equals("") && course.getTitle() != null) {
            currentCourse.setTitle(course.getTitle());
        }
        if (course.getCredit() != null) {
            currentCourse.setCredit(course.getCredit());
        }

        courseRepository
                .updateCourse(currentCourse.getCredit(), currentCourse.getTitle(), currentCourse.getTeacher().getTeacherId(),
                        currentCourse.getCourseId());
    }

    @Override
    @Transactional
    @Async
    public void deleteCourseByTitle(String title) {
        CourseResponse courseByTitle = getCourseByTitle(title);
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
