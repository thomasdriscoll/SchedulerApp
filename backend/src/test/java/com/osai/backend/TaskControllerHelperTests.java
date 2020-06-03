package com.osai.backend;

import java.util.ArrayList;

import com.osai.backend.task.Task;
import com.osai.backend.task.TaskController;
import com.osai.backend.task.TaskRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@WebMvcTest(TaskController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TaskControllerHelperTests {
    //Create actual instance of the TaskController -- this is just for the helper
    // functions in the controller itself
    @MockBean
    TaskRepository mockRepo;
    TaskController controller = new TaskController(mockRepo);
    
    public ArrayList<Task> arrayOfTasks(){
        ArrayList<Task> tasks = new ArrayList<Task>();
        for(int i=0; i < 100; i++){
            Task temp = new Task(
                "thomasdriscoll",
                "Example Task "+ String.valueOf(i),
                5 * i,          //time
                i % 5,          //mood
                i % 5 -2,       //energy
                false,
                i * 4.5,        //latitude
                i * 9.0,        //longitude
                (float) 98.6,
                "afternoon"            
            );
            temp.setId((long) i);
            tasks.add(temp);
        }
        return tasks;
    }

    // Testing the helper functions
    @Test 
    public void insertTask_emptyTest() throws Exception{
        ArrayList<Task> tasks = new ArrayList<Task>();
        ArrayList<Task> kdtree = new ArrayList<Task>();
        this.controller.insertTask (tasks, kdtree, "", 0);
        Assertions.assertEquals(true, tasks.isEmpty());
    }

    @Test 
    public void insertTask_fullTree() throws Exception{
        ArrayList<Task> tasks = arrayOfTasks();
        ArrayList<Task> kdtree = new ArrayList<Task>();
        this.controller.insertTask(tasks, kdtree, "", 0);
        Assertions.assertEquals((long) 52, kdtree.get(0).getId());
        Assertions.assertEquals("52.", kdtree.get(0).getAncestry());
    }

    @Test
    public void insertTask_MomWorks() throws Exception {
        ArrayList<Task> tasks = arrayOfTasks();
        Task median = this.controller.MedianOfMedians(tasks, tasks.size()/2, 0);
        Assertions.assertEquals((long) 52, median.getId());
    } 

    @Test 
    public void insertTask_MedianOfFiveWorks() throws Exception {
        ArrayList<Task> tasks = arrayOfTasks();
        ArrayList<Task> subList = new ArrayList<Task> (tasks.subList(7, 11));
        Task median = this.controller.MedianOfFive(subList, 1); //using mood so that the medians aren't sorted already
        Assertions.assertEquals(tasks.get(8), median);
    }



}