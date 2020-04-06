package com.osai.backend;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.osai.backend.task.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTests {
    @Autowired MockMvc controller;
    @MockBean TaskRepository repository;

    //Sanity check; loads controller for testing. If this breaks, either your controller doesn't compile or your test suite is wrong
    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }



    // References: https://stackoverflow.com/questions/51346781/how-to-test-post-method-in-spring-boot-using-mockito-and-junit
    // @Test
    // public void createTaskTest() throws Exception {

    //     Task expect = new Task("thomasdriscoll", "Example Task 1", 5, 0, 5, 3, true,
    //     "123 Sesame St., Disney Land, CA 12345", 10, "afternoon", (float) 72.0, 1234567890, 987654321);


    //     controller.perform(post("/createTask")
    //         .contentType(MediaType.APPLICATION_JSON)
    //         .content(toJson(expect)))
    //         .andExpect(status().isOk());
    // }
}