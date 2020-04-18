package com.osai.backend;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import com.osai.backend.user.Username;
import com.osai.backend.user.UserRepository;

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
public class UserRepositoryTest {
    //Mock database
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository repository;

    //Test findAll
    public void whenFindAll_thenReturnUsers() throws Exception {
        //given
        Username one = new Username("Pauline", "Lu", "opensource", "pauline4@illinois.edu",
                new String[] {"1234 I Live Here Dr.", "5678 White House Lawn"}, 5);
        Username two = new Username("Pauline", "Lu", "opensource", "pauline4@illinois.edu",
                new String[] {"1234 I Live Here Dr.", "5678 White House Lawn"}, 5);
        entityManager.persist(one);
        entityManager.persist(two);
        entityManager.flush();

        //when
        List<Username> foundList;
        List<Username> expectedList = new ArrayList<>();
        foundlist = repository.findAll();
        Username[] found = foundlist.toArray(new Username[2]);
        expectedList.add(one);
        expectedList.add(two);
        Username[] expected = expectedlist.toArray(new Username [2]);

        //then
        assertArrayEquals(found, expected,
                "/api/user/all - Found" + foundlist.toString() + " does not equal expected " + expectedlist.toString());
    }

    //Successful test for finding task by id
    @Test
    public void whenFindByIdSucceeds_thenReturnOne() throws Exception {
        //given
        Username expect = new Username("Pauline", "Lu", "opensource", "pauline4@illinois.edu",
                new String[] {"1234 I Live Here Dr.", "5678 White House Lawn"}, 5);
        entityManager.persist(expect);
        entityManager.flush();

        //when
        Username found = repository.findById(expect.getId()).orElse(null);

        //then
        assertEquals(expect, found, "/api/user/getUserById - does not return task given id");
    }

    //Test what happens when findById fails
    @Test
    public void whenFindByIdFails_thenError() throws Exception {
        //given
        Username expect = new Username("Pauline", "Lu", "opensource", "pauline4@illinois.edu",
                new String[] {"1234 I Live Here Dr.", "5678 White House Lawn"}, 5);
        entityManager.persist(expect);
        entityManager.flush();

        //when
        Username found = repository.findById((long) -1).orElse(null);

        //then
        assertEquals(null, found, "/api/user/getUserById - does not return null on error");
    }

    //Test for successful save to repository
    @Test
    public void whenCreateUser_thenReturnUser() throws Exception {
        //given
        Username expect = new Username("Pauline", "Lu", "opensource", "pauline4@illinois.edu",
                new String[] {"1234 I Live Here Dr.", "5678 White House Lawn"}, 5);
        entityManager.flush();

        //when
        Username found = repository.save(expect);
        Username persistFind = repository.findById(expect.getId()).orElse(null);

        //then
        assertEquals(expect, found, "/api/user/createUser - Incorrect return value from post"); //Assert return val. correct
        assertEquals(expect, persistFind, "/api/user/createUser - Saved result is not queryable"); //Assert val. retrievable from db.
    }

    //Test for successful delete from repository
    @Test
    public void whenDeleteById_thenReturnVoid() throws Exception {
        //given
        Username expect = new Username("Pauline", "Lu", "opensource", "pauline4@illinois.edu",
                new String[] {"1234 I Live Here Dr.", "5678 White House Lawn"}, 5);
        entityManager.flush();

        //save first
        Username found = repository.save(expect);

        //make sure save works
        assertEquals(expect, found, "/api/user/deleteById - save failed");

        //when
        repository.deleteById/(expect.getId());
        boolean exists = repository.existsById(expect.getId());
        assertEquals(false, exists, "/api/user/deleteById - delete failed");
    }

    //Test for existence of ID
    @Test
    public void whenDeleteByIdFails() throws Exception {
        //given
        Username expect = new Username("Pauline", "Lu", "opensource", "pauline4@illinois.edu",
                new String[] {"1234 I Live Here Dr.", "5678 White House Lawn"}, 5);
        entityManager.flush();

        //save first
        Username found = repository.save(expect);

        //make sure save works
        assertEquals(expect, found, "/api/user/deleteById - save failed");

        //when exists
        boolean exists = repository.existsById(expect.getId());
        //then
        assertEquals(true, exists, "/api/user/deleteById - id doesn't exist when it should");

        //when it doesn't exist
        repository.deleteById(expect.getId());
        exists = repository.existsById(expect.getId());
        //then
        assertEquals(false, exists, "/api/user/deleteById - id exists when it shouldn't");
    }

    //Test for successful map function
    @Test
    public void whenUpdateUser_returnUpdatedUser() throws Exception {
        //given
        Username initial = new Username("Pauline", "Lu", "opensource", "pauline4@illinois.edu",
                new String[] {"1234 I Live Here Dr.", "5678 White House Lawn"}, 5);
        entityManager.flush();
        //save initial
        Username found = repository.save(initial);
        //make sure save works
        assertEquals(initial, found, "/api/user/updateUserById - save failed");

        //when updates
        Username expect = new Username("Pauline", "Lu", "opensource", "pauline4@illinois.edu",
                new String[] {"1234 I Live Here Dr.", "5678 White House Lawn"}, 5);
        long id = initial.getId();
        found = repository.findById(id)
                .map(task -> {
                    repository.deleteById(id);
                    return repository.save(expect);
                }).orElseGet(() -> {
                    return repository.save(expect)
                });
        //then
        assertEquals(expect, found, "/api/user/updateTaskById - did not update");
    }

    //Test for failed ID find, post instead
    @Test
    public void whenUpdateUserFails_returnCreatedUser() throws Exception {
        Username expect = new Username("Pauline", "Lu", "opensource", "pauline4@illinois.edu",
                new String[] {"1234 I Live Here Dr.", "5678 White House Lawn"}, 5);
        long id = -1;
        Username found = repository.findById(id)
                .map(task -> {
                    repository.deleteById(id);
                    return repository.save(expect);
                }).orElseGet(() -> {
                    return repository.save(expect);
                });
        //then
        assertEquals(expect, found, "api/user/updateUserById - did not update");
    }
}