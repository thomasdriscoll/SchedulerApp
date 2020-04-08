// Package declaration and API class imports
package com.osai.backend;

//AssertJ, gives you basic assertThat functionality
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.osai.backend.task.Task;
import com.osai.backend.task.TaskController;
import com.osai.backend.task.TaskRepository;

import static org.mockito.Mockito.*;
//JUnit packages (provides @Test annotations and )
import org.junit.jupiter.api.Test;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTests {
    @MockBean 
        TaskRepository repository;
    @Autowired  
        MockMvc controller;

    // Reference: https://mkyong.com/spring-boot/mockito-how-to-mock-repository-findbyid-thenreturn-optional/
    @Before 
        public void init() {
            Task task1 =  Task expect1 = new Task("thomasdriscoll", "Example Task 1", 5, 0, 5, 3, true,
                "123 Sesame St., Disney Land, CA 12345", 10, "afternoon", (float) 72.0, 1234567890, 987654321);
            Task expect2 = new Task("thomasdriscoll", "Example Task 2", 5, 0, 5, 3, true,
                "123 Sesame St., Disney Land, CA 12345", 10, "afternoon", (float) 72.0, 1234567890, 987654321);
            Task[] task_list 
            
        }
    
    // @Before("execution(* com.")
    // public void initialization() {
    //     controller = MockMvcBuilders.standaloneSetup(new TaskController(repository)).build();
    // }
    //Sanity check; loads controller for testing. If this breaks, either your controller doesn't compile or your test suite is wrong
    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    // Reference: https://howtodoinjava.com/spring-boot2/testing/spring-boot-mockmvc-example/
    @Test 
    public void getAllTasks() throws Exception {
       

        //JSONify Task_list
        ObjectMapper mapper = new ObjectMapper();
        String task_list_str = mapper.writeValueAsString(task_list);

        controller.perform(MockMvcRequestBuilders
            .get("/all")
            .content(task_list_str)
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8"))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
            .andDo(MockMvcResultHandlers.print());
        // .andExpect(MockMvcResultMatchers.jsonPath("$.tasks").exists())
        // .andExpect(MockMvcResultMatchers.jsonPath("$.tasks[*].id").isNotEmpty());
    }

    // References: https://stackoverflow.com/questions/51346781/how-to-test-post-method-in-spring-boot-using-mockito-and-junit
    // References: https://stackoverflow.com/questions/49956208/spring-controller-testing-with-mockmvc-post-method
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