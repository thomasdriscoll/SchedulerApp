/*
    Author: Thomas Driscoll
    Date: 18 April 2020
    Remaining work: PUT pass/fail tests. Low priority (all functionality is tested in other unit tests)
*/

package com.osai.backend;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import com.osai.backend.task.Task;
import com.osai.backend.task.TaskRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase
public class TaskRepositoryTest {
    // Mock database
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TaskRepository repository;

    // Test for findAll
    @Test
    public void whenFindAll_thenReturnTasks() throws Exception {
        // given
        Task one = new Task("thomasdriscoll", "Example Task 1", 5, 5, 3, true,
                "123 Sesame St., Disney Land, CA 12345", (float) 72.0, "afternoon");
        Task two = new Task("thomasdriscoll", "Example Task 2", 5, 5, 3, true,
                "123 Sesame St., Disney Land, CA 12345", (float) 72.0, "afternoon");
        entityManager.persist(one);
        entityManager.persist(two);
        entityManager.flush();

        // when
        List<Task> foundlist;
        List<Task> expectedlist = new ArrayList<>();
        foundlist = repository.findAll();
        Task[] found = foundlist.toArray(new Task[2]);
        expectedlist.add(one);
        expectedlist.add(two);
        Task[] expected = expectedlist.toArray(new Task[2]);

        // then
        assertArrayEquals(found, expected,
                "/all - Found " + foundlist.toString() + " does not equal expected " + expectedlist.toString());
    }

    // Successful test for finding task by id
    @Test
    public void whenFindByIdSucceeds_thenReturnOne() throws Exception {
        // given
        Task expect = new Task("thomasdriscoll", "Example Task 1", 0, 5, 3, true,
                "123 Sesame St., Disney Land, CA 12345", (float) 72.0, "afternoon");
        entityManager.persist(expect);
        entityManager.flush();

        // when
        Task found = repository.findById(expect.getId()).orElse(null);

        // then
        assertEquals(expect, found, "/getTaskById - does not return task given id");
    }

    //Tests what happens when findById fails
    @Test
    public void whenFindByIdFails_thenError() throws Exception {
        // given
        Task expect = new Task("thomasdriscoll", "Example Task 1", 0, 5, 3, true,
                "123 Sesame St., Disney Land, CA 12345", (float) 72.0, "afternoon");
        entityManager.persist(expect);
        entityManager.flush();

        // when
        Task found = repository.findById((long) -1).orElse(null);

        // then
        assertEquals(null, found, "/getTaskById - does not return null on error");
    }

    //Test for successful save to repository
    @Test
    public void whenCreateTask_thenReturnTask() throws Exception {
        // given
        Task expect = new Task("thomasdriscoll", "Example Task 1", 0, 5, 3, true,
                "123 Sesame St., Disney Land, CA 12345", (float) 72.0, "afternoon");
        entityManager.flush();

        // when
        Task found = repository.save(expect);
        Task persistFind = repository.findById(expect.getId()).orElse(null);

        //then  
        assertEquals(expect, found, "/createTask - Incorrect return value from post");         // Assert that the return value is correct
        assertEquals(expect, persistFind, "/createTask - Saved result is not queryable");   // Assert that the value is retrievable from db
    }    

    //Test for successful delete from repository
    @Test
    public void whenDeleteById_thenReturnVoid() throws Exception {
        //given 
        Task expect = new Task("thomasdriscoll", "Example Task 1", 0, 5, 3, true,
                "123 Sesame St., Disney Land, CA 12345", (float) 72.0, "afternoon");
        entityManager.flush();
        // save first
        Task found = repository.save(expect);
        // then make sure save works
        assertEquals(expect, found, "/deleteById - save failed");

        //when
        repository.deleteById(expect.getId());
        boolean exists = repository.existsById(expect.getId());
        assertEquals(false, exists, "/deleteById - delete failed");
    }

    //Test for existence of ID
    @Test 
    public void whenDeleteByIdFails() throws Exception{
        //given 
        Task expect = new Task("thomasdriscoll", "Example Task 1", 5, 5, 3, true,
                "123 Sesame St., Disney Land, CA 12345", (float) 72.0, "afternoon");
        entityManager.flush();
        // save first
        Task found = repository.save(expect);
        // then make sure save works
        assertEquals(expect, found, "/deleteById - save failed");

        //when exists
        boolean exists = repository.existsById(expect.getId());
        //then
        assertEquals(true, exists, "/deleteById - id doesn't exist when it should");

        //when it doesn't exist
        repository.deleteById(expect.getId());
        exists = repository.existsById(expect.getId());
        //then
        assertEquals(false, exists, "/deleteById - id exists when it shouldn't");
    }

    //Test for successful map function
    @Test
    public void whenUpdateTask_returnUpdatedTask() throws Exception {
        //given
        Task initial = new Task("thomasdriscoll", "Example Task 1", 5, 0, 5, true,
                "123 Sesame St., Disney Land, CA 12345", (float) 72.0, "afternoon");
        entityManager.flush();
        // save initial
        Task found = repository.save(initial);
        // then make sure save works
        assertEquals(initial, found, "/updateTaskById - save failed");

        //when updates
        Task expect = new Task("thomasdriscoll", "Example Task 1", 5, 0, 3, true,
                "123 Sesame St., Disney Land, CA 12345", (float) 72.0, "afternoon");
        long id = initial.getId();
        found = repository.findById(id)
            .map(task -> {
                repository.deleteById(id);
                return repository.save(expect);
            }).orElseGet(() -> {
                return repository.save(expect);
            });
        //then
        assertEquals(expect, found, "/updateTaskById - did not update");        
    }

    //Test for failed ID find, post instead
    @Test
    public void whenUpdateTaskFails_returnCreatedTask() throws Exception {
        Task expect = new Task("thomasdriscoll", "Example Task 1", 0, 5, 3, true,
                "123 Sesame St., Disney Land, CA 12345", (float) 72.0, "afternoon");
        long id = -1;
        Task found = repository.findById(id)
            .map(task -> {
                repository.deleteById(id);
                return repository.save(expect);
            }).orElseGet(() -> {
                return repository.save(expect);
            });
        //then
        assertEquals(expect, found, "/updateTaskById - did not update");
    }
}