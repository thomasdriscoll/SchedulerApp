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
        double[] hyperRect = {Double.MIN_VALUE, Double.MAX_VALUE, Double.MIN_VALUE, Double.MAX_VALUE, Double.MIN_VALUE, Double.MAX_VALUE, Double.MIN_VALUE, Double.MAX_VALUE, Double.MIN_VALUE, Double.MAX_VALUE};
        for(int i = 0; i < 10; i++){
            best = traverseTree(tree, compare, depth, 0, Double.MAX_VALUE, 0, hyperRect);
            results.add(tree.get(best));
            tree.get(best).setAncestry("none");
        } 
         /* for(int i =0; i <10; i++){
            System.out.println(results.get(i));
        } */
        return results;
    }

    public int traverseTree(ArrayList<Task> tree, double [] compare, int dim, int i, double best_dist, int best_node, double [] hyperRect) {

        if(i == -1 || calculateRectDist(compare, hyperRect) > best_dist) { //if node is a leaf (can't traverse anymore, end recursion)
            return best_node;
        }

        double distance = calculateWeight(tree.get(i), compare);
        int left = tree.get(i).getLeftChild(); 
        int right = tree.get(i).getRightChild();
        
        if(distance < best_dist) {
            best_node = i;
            best_dist = distance;
        }

        if(compare[dim] < getDepthValue(dim, tree.get(i))){
            best_node = traverseTree(tree, compare, (dim+1)%4, left, best_dist, best_node, trimHyperRectLeft(tree.get(i), hyperRect, dim)); //will return some leaf node and assign it to var best 
            best_node = traverseTree(tree, compare, (dim+1)%4, right, best_dist, best_node, trimHyperRectRight(tree.get(i), hyperRect, dim));
        }
        else {
            best_node = traverseTree(tree, compare, (dim+1)%4, right, best_dist, best_node, trimHyperRectRight(tree.get(i), hyperRect, dim)); //will return some leaf node and assign it to var best 
            best_node = traverseTree(tree, compare, (dim+1)%4, left, best_dist, best_node, trimHyperRectLeft(tree.get(i), hyperRect, dim));
        }

        return best_node;   
    }

    //checks for "closeness"
    public double calculateWeight(Task node, double [] curr) {
        return Math.abs((curr[0] - node.getTime())) + Math.abs((curr[1] - node.getMood())) + Math.abs((curr[2] - node.getEnergy())) + 
        Math.abs(Math.sqrt(Math.pow((curr[3] - node.getLatitude()), 2) + Math.pow((curr[4] - node.getLongitude()), 2)));
    }

    //Calculate the distance of the side nearest to a given point
    public double calculateRectDist(double[] compare, double[] rect){
        //Delete these dumb variables later
        double time, mood, energy, latitude, longitude = 0;
        time = compare[0];
        mood = compare[1];
        energy = compare[2];
        latitude = compare[3];
        longitude = compare[4];
        //Check if point is inside current bounds of rectangle
        if( 
            (rect[0] <= time && time <= rect[1]) ||
            (rect[2] <= mood && mood <= rect[3]) ||
            (rect[4] <= energy && energy <= rect[5]) ||
            (rect[6] <= latitude && latitude <= rect[7]) ||
            (rect[8] <= longitude && longitude <= rect[9])
        ){
            return 0;
        }
        else{
            time = Math.min(Math.abs(time-rect[0]), Math.abs(time-rect[1]));
            mood = Math.min(Math.abs(mood-rect[2]), Math.abs(mood-rect[3]));
            energy = Math.min(Math.abs(energy-rect[4]), Math.abs(energy-rect[5]));
            double distance = Math.min(
                Math.sqrt(Math.pow((rect[6] - latitude), 2) + Math.pow((rect[8] - longitude), 2)), 
                Math.sqrt(Math.pow((rect[7] - latitude), 2) + Math.pow((rect[9] - longitude), 2)));
            return Math.min(Math.min(time, mood), Math.min(energy, distance));
        }
    }

    //Update the max coordinate depending on if it is left or right
    public double[] trimHyperRectLeft(Task curr, double[] hyperRect, int dim){
        //Each row goes [time_min, time_max, mood_min, mood_max, energy_min, energy_max, lat_min, lat_max, long_min, long_max]
        if(dim != 3){
            hyperRect[2*dim+1] = getDepthValue(dim, curr);
        }
        else{
            hyperRect[2*dim+1] = curr.getLatitude();
            hyperRect[2*(dim+1)+1] = curr.getLongitude();
        }
        return hyperRect;
    }

    //Update the min coordinate depending on if it is left or right
    public double[] trimHyperRectRight(Task curr, double[] hyperRect, int dim) {
        // Remember dim 0 = time, dim 1 = mood, dim 2 = energy, dim 3 = distance
        // Each row goes [time_min, time_max, mood_min, mood_max, energy_min, energy_max, lat_min, lat_max, long_min, long_max]
        if(dim != 3){
            hyperRect[2*dim] = getDepthValue(dim, curr);
        }
        else{
            hyperRect[2*dim] = curr.getLatitude();
            hyperRect[2*(dim+1)] = curr.getLongitude();
        }

        return hyperRect;
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