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
import java.util.Iterator;
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
        ArrayList<Task> tasks = repository.getTreeByUser(newTask.getUser());
        tasks.add(newTask);
        ArrayList<Task> kdtree = new ArrayList<Task>();
        insertTask(tasks, kdtree, "", 0);

        return repository.saveAll(kdtree);
        /*
            Note: Probably would be a good idea to get rid of the switch statement in the insertTask function to create
            an array of values in this function. That way, we could just preset the values to correspond to the task and just have O(1) lookup.
            The trick is figuring out how to use the modulus function s.t. the indices always match up. Or that may be a limiting factor for the trees.
            FOR INSTANCE: location/mood/time/energy are always going to be 1-4, I don't see a use case where they wouldn't be important. But novelty isn't always
            important (I will be using it as a tie-breaker value probably), time_of_day may be more important and they'd both be fighting for that 5th position.
            Good problem to solve later...
        */
    }
   
    public void insertTask(ArrayList<Task> tasks, ArrayList<Task> kdtree, String ancestry, int depth){
        //Making an init tree
        if(tasks.isEmpty()){
            return;
        }
        // Find the median element of the current array, set its ancestry and add it to the kdtree, removing it from the other
        int root = MedianOfMedians(tasks, depth);                       //O(n)
        ancestry = ancestry+tasks.get(root).getId()+".";
        tasks.get(root).setAncestry(ancestry);
        kdtree.add(tasks.get(root));
        double cut = getDepthValue(depth, tasks.get(root));             //O(1)
        tasks.remove(root);
        // divide the tree into two
        ArrayList<Task> rightTree = getRightTree(tasks,cut, depth);     // O(n)
        //Left half of tree
        insertTask(tasks, kdtree, ancestry, (depth+1)%4);       //4 because there are 4 cutting dimensions (time, mood, energy, location)
        //Right half of Tree
        insertTask(rightTree, kdtree, ancestry, (depth+1)%4);
    
    }
    //Helper functions for insertTask
    public int MedianOfMedians(ArrayList<Task> tasks, int depth){
        return 0;
    }
   
    public ArrayList<Task> getRightTree(ArrayList<Task> leftTree, double cut, int depth){
        Iterator<Task> iterator = leftTree.iterator();
        ArrayList<Task> rightTree = new ArrayList<Task>();
        while(iterator.hasNext()){
            Task curr = iterator.next();
            if(getDepthValue(depth, curr) >= cut){
                rightTree.add(curr);
                iterator.remove();
            }
        }
        return rightTree;
    }

    public double getDepthValue(int depth, Task curr){
        double taskValue =0;
        switch(depth){
            case 0:
                taskValue = curr.getTime();
            case 1:
                taskValue = curr.getMood();
            case 2:
                taskValue = curr.getEnergy();
            case 3:
                taskValue = Math.sqrt(Math.pow(curr.getLatitude(), 2) + Math.pow(curr.getLongitude(), 2));
        }
        return taskValue;
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
}