package com.osai.backend;

// import java.util.List;

// import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class TaskController {
    private TaskRepository repository;

    TaskController(TaskRepository repository){
        this.repository = repository;
    }

    //Mappings
    @PostMapping(path="/createTask")
    public Task createTask(@RequestBody Task newTask){
        return repository.save(newTask);
    }

    //Get all -- mostly for testing
    @GetMapping(path="/all")
    public @ResponseBody Iterable<Task> getAllTasks(){
        return repository.findAll();
    }
    
    @GetMapping("/getTaskById/{id}")
    public Task getTaskById(@PathVariable long id) {
         return repository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
    }

    @DeleteMapping("/deleteTaskById/{id}") 
    public void deleteTaskById(@PathVariable long id) {
        repository.deleteById(id);
    }

    @PutMapping("/updateTaskById/{id}")
    public Task updateTaskById(@RequestBody Task newTask, @PathVariable long id) {
        return repository.findById(id)
            .map(task -> {
                repository.deleteById(id);
                return repository.save(newTask);
            }).orElseGet(() -> {
                return repository.save(newTask);
            });
    }
    
}