package com.java.springdatajpaapplication.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.springdatajpaapplication.dto.CourseRequest;
import com.java.springdatajpaapplication.dto.CourseResponse;
import com.java.springdatajpaapplication.entity.Teacher;
import com.java.springdatajpaapplication.exception.CourseNotFoundException;
import com.java.springdatajpaapplication.service.CourseService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CourseController.class)
class CourseControllerTest {

    @MockBean
    private CourseService courseService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should return course with valid title")
    void whenValidTitle_shouldGetCourse() throws Exception {

        String courseTitle = "MBA";

        Teacher teacher = Teacher.builder()
                .teacherId(13L)
                .firstName("Albert")
                .lastName("Romero")
                .build();

        CourseResponse courseResponse = CourseResponse.builder()
                .courseId(1L)
                .title("SSA")
                .credit(200)
                .teacher(teacher)
                .build();

        Mockito.when(courseService.getCourseByTitle(courseTitle)).thenReturn(courseResponse);

        // Testing before Java 15 (jsonPath)
        MvcResult actualResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/courses/title/" + courseTitle))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.courseId", Matchers.is(1)))
                .andExpect(jsonPath("$.title", Matchers.is("SSA")))
                .andExpect(jsonPath("$.credit", Matchers.is(200)))
                .andExpect(jsonPath("$.teacher.teacherId", Matchers.is(13)))
                .andExpect(jsonPath("$.teacher.firstName", Matchers.is("Albert")))
                .andExpect(jsonPath("$.teacher.lastName", Matchers.is("Romero")))
                .andReturn();

        // Or

        String expectedResBeforeJava15 = "{\n" +
                "                        \"courseId\": 1,\n" +
                "                        \"title\": \"SSA\",\n" +
                "                        \"credit\": 200,\n" +
                "                        \"teacher\": {\n" +
                "                            \"teacherId\": 13,\n" +
                "                            \"firstName\": \"Albert\",\n" +
                "                            \"lastName\": \"Romero\"\n" +
                "                        }\n" +
                "                }";

        // Java 15 text-blocks way
        String expectedResult = """
                {
                        "courseId": 1,
                        "title": "SSA",
                        "credit": 200,
                        "teacher": {
                            "teacherId": 13,
                            "firstName": "Albert",
                            "lastName": "Romero"
                        }
                }
                """;

        assertAll(
                () -> JSONAssert.assertEquals(expectedResBeforeJava15, actualResult.getResponse().getContentAsString(), true),
                () -> JSONAssert.assertEquals(expectedResult, actualResult.getResponse().getContentAsString(), true)
        );
    }

    @Test
    @DisplayName("Should return not found with invalid title")
    void whenInvalidTitle_shouldReturnNotFound() throws Exception{

        String courseTitle = "SSA";

        Mockito.when(courseService.getCourseByTitle(courseTitle))
                .thenThrow(new CourseNotFoundException(String.format("course with title %s not found", courseTitle)));

        MvcResult actualResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/courses/title/" + courseTitle))
                .andExpect(status().is(404))
                .andExpect(jsonPath("$.status", Matchers.is("NOT_FOUND")))
                .andExpect(jsonPath("$.message", Matchers.is("course with title SSA not found")))
                .andReturn();

        String expectedResult = """
                {
                    "status": "NOT_FOUND",
                    "message": "course with title SSA not found",
                    "path": ""
                }
                """;

        assertAll(
                () -> JSONAssert.assertEquals(expectedResult, actualResult.getResponse().getContentAsString(), true)
        );
    }

    @Test
    @DisplayName("Should fully update course")
    void whenValidFullRequest_shouldUpdateCourse() throws Exception {

        String courseTitle = "MBA";

        CourseRequest courseRequest = new CourseRequest(null, "MBA", 250, "Albert", null);

        mockMvc.perform(MockMvcRequestBuilders.put("/courses/title/" + courseTitle)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(courseRequest)))
                .andExpect(status().is(200));
    }
}