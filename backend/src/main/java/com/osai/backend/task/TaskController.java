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

// import java.util.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Collections;

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
    }
   
    public int insertTask(ArrayList<Task> tasks, ArrayList<Task> kdtree, String ancestry, int depth){
        //Making an init tree
        if(tasks.isEmpty()){
            return -1;
        }
        // Find the median element of the current array, set its ancestry and add it to the kdtree, removing it from the other
        Task root = MedianOfMedians(tasks, tasks.size()/2, depth);                       //O(n)
        int rootIndex = tasks.indexOf(root);
        ancestry = ancestry+tasks.get(rootIndex).getId()+".";
        System.out.println(ancestry);
        tasks.get(rootIndex).setAncestry(ancestry);
        kdtree.add(tasks.get(rootIndex));       
        double cut = getDepthValue(depth, tasks.get(rootIndex));             //O(1)
        tasks.remove(rootIndex);
        rootIndex = kdtree.size()-1;
        // divide the tree into two
        ArrayList<Task> rightTree = getRightTree(tasks,cut, depth);     // O(n)
        //Left half of tree
        int left = insertTask(tasks, kdtree, ancestry, (depth+1)%4);               //4 because there are 4 cutting dimensions (time, mood, energy, location)
        kdtree.get(rootIndex).setLeftChild(left);
        //Right half of Tree
        int right = insertTask(rightTree, kdtree, ancestry, (depth+1)%4);
        kdtree.get(rootIndex).setRightChild(right);
        return rootIndex;   //Ok, due to the way the tree is structured (left to right), we know that the index here will be preserved on the retrieval
    
    }
    //Helper functions for insertTask
    public Task MedianOfMedians(ArrayList<Task> tasks, int k, int depth) {
        if(tasks.size() <= 25){
            return tasks.get(k);
        }
        double numBlocks = tasks.size() / 5;
        Task mom;
        ArrayList<Task> medians = new ArrayList<Task>();
        int start_index = 0, end_index = 4, r=-1;

        for (double i = 0.0; i < numBlocks; i++) {
            ArrayList<Task> subList;
            if (start_index < tasks.size() && end_index < tasks.size()) {
                subList = new ArrayList<Task> (tasks.subList(start_index, end_index));
                start_index += 5;
                end_index += 5;
            }
            else {
                subList = new ArrayList<Task> (tasks.subList(start_index, tasks.size() - 1)); //for last block, may have less than five
            }
            medians.add(MedianOfFive(subList, k));
           
        }
        mom = MedianOfMedians(medians, (int) medians.size() / 2, depth);
        r = partition(tasks, tasks.indexOf(mom), depth);
        if (k < r) {
            return MedianOfMedians(new ArrayList<Task> (tasks.subList(0, r)), k-1, depth);
        }
        else if (k > r) {
            return MedianOfMedians(new ArrayList<Task> (tasks.subList(r + 1, tasks.size() - 1)), k-r-1, depth);
        }
        else {
            return mom;
        }
    }

    //https://stackoverflow.com/questions/18441846/how-to-sort-an-arraylist-in-java
    public Task MedianOfFive(ArrayList<Task> subList, int depth) {
        Collections.sort(subList, new Comparator<Task>() {
            @Override
            public int compare(Task task1, Task task2) {
                return Double.compare(getDepthValue(depth, task1), (getDepthValue(depth, task2)));
            }
        });
        return subList.get(subList.size() / 2); //get the middle (median value)
    }

    public int partition(ArrayList<Task> tasks, int mom, int depth) {
        Collections.swap(tasks, mom, tasks.size()-1);
        int l = -1;
        double curr_depth_value = getDepthValue(depth, tasks.get(tasks.size() - 1));


        for (int i = 0; i < tasks.size()-1; i++) {
            if (getDepthValue(depth, tasks.get(i)) < curr_depth_value) {
                l += 1;
                Collections.swap(tasks, i, l);
            }    
        }
        Collections.swap(tasks, tasks.size()-1, l+1);
        return l+1;
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
        switch(depth){
            case 0:
                return curr.getTime();
            case 1:
                return curr.getMood();
            case 2:
                return curr.getEnergy();
            case 3:
                return Math.sqrt(Math.pow(curr.getLatitude(), 2) + Math.pow(curr.getLongitude(), 2));   //This line needs work
            default:
                return 0;
        }
    }
      
    // Get best fit
    // Return top 10 results
    @GetMapping("/api/task/bestTen/{username}/{time}/{energy}/{mood}/{latitude}/{longitude}")
    public @ResponseBody Iterable<Task> getBestTenTasks(
        String username,
        double time,
        double energy,
        double mood,
        double latitude,
        double longitude
    ){
        double [] compare = {time, mood, energy, latitude, longitude}; //switched mood and energy order in array
        ArrayList<Task> tree = repository.getTreeByUser(username);
        return findBestTen(tree, compare, 0);
    }

    public ArrayList<Task> findBestTen(ArrayList<Task> tree, double [] compare, int depth){
        int best = 0;

        ArrayList<Task> results = new ArrayList<Task>();
        for(int i = 0; i < 10; i++){
            best = traverseTree(tree, compare, depth, 0);
            results.add(tree.get(best));
            tree.get(best).setAncestry("none");
        } 
         /* for(int i =0; i <10; i++){
            System.out.println(results.get(i));
        } */
        return results;
    }

    public int traverseTree(ArrayList<Task> tree, double [] compare, int depth, int i) {
        if(tree.get(i).getLeftChild() == -1 && tree.get(i).getRightChild() == -1) { //if node is a leaf (can't traverse anymore, end recursion)
            return i;
        }

        int left = tree.get(i).getLeftChild(); 
        int right = tree.get(i).getRightChild();
        int best = -1;

        if((getDepthValue(depth, tree.get(i)) > compare[depth]) && left != -1 && tree.get(i).getAncestry() != "none") {
            best = traverseTree(tree, compare, (depth+1)%4, left); //will return some leaf node and assign it to var best 
        }
        else {
            best = traverseTree(tree, compare, (depth+1)%4, right);
        }

        double curr_node = calculateWeight(tree.get(i), compare);
        double pot_best = calculateWeight(tree.get(best), compare); //will return some arraylist value

        if (tree.get(best).getAncestry() == "none") {
            return i;
        }
        
        if(curr_node < pot_best && tree.get(i).getAncestry() != "none"){
            best = i; //new best index
        }

        return best;   
    }

    //checks for "closeness"
    public double calculateWeight(Task node, double [] curr) {
        return Math.abs((curr[0] - node.getTime())) + Math.abs((curr[1] - node.getMood())) + Math.abs((curr[2] - node.getEnergy())) + 
        Math.abs(Math.sqrt(Math.pow((curr[3] - node.getLatitude()), 2) + Math.pow((curr[4] - node.getLongitude()), 2)));
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
}