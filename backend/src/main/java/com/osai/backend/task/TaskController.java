/*
    Author: Thomas Driscoll
    Date: 18 April 2020
    Remaining work: 
*/
package com.osai.backend.task;


// import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class TaskController {
    private TaskRepository repository;

    public TaskController(TaskRepository repository) {
        this.repository = repository;
    }

    // Mappings
    @PostMapping(path = "/api/task/createTask")
    public List<Task> createTask(@Valid @RequestBody Task newTask) {
        ArrayList<Task> tree = repository.getTreeByUser(newTask.getUser());
        tree = insertTask(tree, newTask, 0);
        return repository.saveAll(tree);


        //batch save 
        //Old CreateTask function
    }

    //Get all -- mostly for testing
    @GetMapping(path="/api/task/all")
    public @ResponseBody Iterable<Task> getAllTasks(){
        return repository.findAll();
    }
    
    @GetMapping("/api/task/getTaskById/{id}")
    public Task getTaskById(@PathVariable long id) {
         Task found = repository.findById(id).orElse(null);
         if(found == null){
             throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
         }
         else{
             return found;
         }
    }
    // Get best fit
    // Return top 10 results
    @GetMapping("/api/task/bestTen/{username}")
    public @ResponseBody Iterable<Task> getBestTenTasks(String username){
        ArrayList<Task> tree = repository.getTreeByUser(username);
        System.out.println(tree.get(0));
        return tree;
    }

    @DeleteMapping("/api/task/deleteTaskById/{id}") 
    public void deleteTaskById(@PathVariable long id) {
        if(repository.existsById(id)){
            repository.deleteById(id);
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
        }
    }

    @PutMapping("/api/task/updateTaskById/{id}")
    public Task updateTaskById(@RequestBody Task newTask, @PathVariable long id) {
        return repository.findById(id)
            .map(task -> {
                repository.deleteById(id);
                newTask.setId(id);
                return repository.save(newTask);
            }).orElseGet(() -> {
                newTask.setId(id);
                return repository.save(newTask);
            });
    }

    public void traverseTree(ArrayList<Task> tree, Task curr){

    }

    public ArrayList<Task> insertTask(ArrayList<Task> tree, Task task, int depth){
        //Making an init tree
        if(tree.isEmpty()){
            //Set ancestry of root to be the id of the root
            task.setAncestry(String.valueOf(task.getId())); 
            tree.add(task);
            return tree;
        }

        //Current dimension of tree
        // int[] values = []
        int cd = depth % 7;

        return tree;
    }

}