/*
    Author: Thomas Driscoll
    Date: 18 April 2020
    Remaining work: PUT pass/fail tests. Low priority (all functionality is tested in other unit tests)
*/

// Package declaration and API class imports
package com.osai.backend;

//AssertJ, gives you basic assertThat functionality
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.osai.backend.task.Task;
import com.osai.backend.task.TaskController;
import com.osai.backend.task.TaskRepository;

import static org.mockito.Mockito.*;

//JUnit packages (provides @Test annotations and )
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;

//Spring Boot framework MockBean, Autowire, WebMvc, etc. -- non-specific testing imports
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
//Imports for MockMvc -- MockMvc Class, status(), print(), etc.
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TaskController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TaskControllerTests {
    @MockBean
    TaskRepository mockRepository;
    @Autowired
    MockMvc controller;

    // Reference:
    // https://mkyong.com/spring-boot/mockito-how-to-mock-repository-findbyid-thenreturn-optional/
    @BeforeEach
    public void init() {
        final Task task1 = new Task("thomasdriscoll", "Example Task 1", 5, 5, 3, true,
                80.0, 100.2, 70, "afternoon");
        final Task task2 = new Task("thomasdriscoll", "Example Task 2", 5, 0, 5, true,
                80.0, 100.2, 70, "afternoon");
        task1.setId((long) 1);
        task2.setId((long) 2);
        final List<Task> task_list = new ArrayList<Task>();
        task_list.add(task1); task_list.add(task2);
        when(mockRepository.findAll()).thenReturn(task_list);
        // when(mockRepository.getTreeByUser("thomasdriscoll")).thenReturn((ArrayList<Task>) task_list);
        doReturn(Optional.of(task1)).when(mockRepository).findById((long) 1);
        when(mockRepository.existsById((long) 1)).thenReturn(true);
        when(mockRepository.existsById((long) 0)).thenReturn(false);
        when(mockRepository.saveAll(task_list)).thenReturn(task_list);
    }
    
    //Sanity check; loads controller for testing. If this breaks, either your controller doesn't compile or your test suite is wrong
    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    // Reference: https://howtodoinjava.com/spring-boot2/testing/spring-boot-mockmvc-example/
    // Tests for GET operation for /all
    @Test 
    public void getAllTasks() throws Exception {
        controller.perform(MockMvcRequestBuilders
            .get("/api/task/all")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8"))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.jsonPath("$.[*].id").exists())
        .andExpect(MockMvcResultMatchers.jsonPath("$.[0].title").value("Example Task 1"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.[1].title").value("Example Task 2"));
    }

    // References: https://stackoverflow.com/questions/51346781/how-to-test-post-method-in-spring-boot-using-mockito-and-junit
    // References: https://stackoverflow.com/questions/49956208/spring-controller-testing-with-mockmvc-post-method
    // Tests good POST operation
    @Test
    public void createTaskTest() throws Exception {
        //Mock task
        Task expect = new Task("thomasdriscoll", "Example Task 1", 5, 0, 3, true,
        80.0, 100.2, (float) 70, "afternoon");
        
        //Put task in database -- handled by the BeforeEach annotation

        //JSONify Task
        ObjectMapper mapper = new ObjectMapper();
        String expect_str = mapper.writeValueAsString(expect);

        controller.perform(MockMvcRequestBuilders
            .post("/api/task/createTask")
            .contentType(MediaType.APPLICATION_JSON)
            .content(expect_str)
            .characterEncoding("utf-8"))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.[*].title").value("Example Task 1"));
    }

    // Tests bad POST operation
    @Test 
    public void createTask_andFail_Test() throws Exception {
        Task expect = new Task("thomasdriscoll", "Example Task 1", 5, 0, 5, true,
        80.0, 100.2, (float) 70, "");

        //Put task in database
        when(mockRepository.save(expect)).thenReturn(expect);
        //JSONify Task_list
        ObjectMapper mapper = new ObjectMapper();
        String expect_str = mapper.writeValueAsString(expect);

        controller.perform(MockMvcRequestBuilders
            .post("/api/task/createTask")
            .contentType(MediaType.APPLICATION_JSON)
            .content(expect_str)
            .characterEncoding("utf-8"))
        .andExpect(status().isBadRequest());
    }

    //Tests for successful GET by id function 
    @Test
    public void getTaskById_andReturnTask() throws Exception{
        controller.perform(MockMvcRequestBuilders
            .get("/api/task/getTaskById/1")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8"))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
        .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Example Task 1"));
    }

    //Tests for failed GET by id function
    @Test
    public void getTaskById_andFail() throws Exception{
        controller.perform(MockMvcRequestBuilders
            .get("/api/task/getTaskById/0")         // 0 doesn't exist
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8"))
        .andExpect(status().isNotFound());
    }


    @Test
    public void deleteTaskById_andReturn() throws Exception{
        controller.perform(MockMvcRequestBuilders
            .delete("/api/task/deleteTaskById/1")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8"))
        .andExpect(status().isOk());
    }

    
    @Test
    public void deleteTaskByIdFails_throw404() throws Exception{
        controller.perform(MockMvcRequestBuilders
            .delete("/api/task/deleteTaskById/0")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8"))
        .andExpect(status().isNotFound());
    }

    @Test
    public void updateTaskById_andReturn() throws Exception{
        controller.perform(MockMvcRequestBuilders
            .delete("/api/task/deleteTaskById/0")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8"))
        .andExpect(status().isNotFound());
    }

}