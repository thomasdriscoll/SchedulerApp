package com.osai.backend;

// import java.util.List;

// import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
    public @ResponseBody String createTask(
        String user, 
        String title, 
        Integer minute, 
        Integer hour, 
        Integer mood,
        Integer energy, 
        Boolean inside,
        String location, 
        Integer novelty, 
        String time_of_day,
        Float temperature, 
        Long right_child,
        Long left_child
    ){
        System.out.println(user);
        System.out.println(title);
        
        System.out.println(minute.toString());
        System.out.println(hour.toString()+ mood.toString()+ energy.toString());
        System.out.println(inside.toString()+ location+ novelty.toString()+ time_of_day+ temperature.toString()); 
        System.out.println(right_child.toString() + left_child.toString());

        Task newTask = new Task(user, title, minute.intValue(), hour.intValue(), mood.intValue(), energy.intValue(),
                                inside.booleanValue(), location, novelty.intValue(), time_of_day, temperature.floatValue(), 
                                right_child.longValue(), left_child.longValue());
        repository.save(newTask);
        return "Saved";
    }

    //Get all -- mostly for testing
    @GetMapping(path="/all")
    public @ResponseBody Iterable<Task> getTaskById(){
        return repository.findAll();
    }

}