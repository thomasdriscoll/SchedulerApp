package com.osai.backend;

// import java.util.List;

// import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    public @ResponseBody Iterable<Task> getTaskById(){
        return repository.findAll();
    }

}