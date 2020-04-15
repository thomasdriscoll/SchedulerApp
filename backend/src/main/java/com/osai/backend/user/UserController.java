package com.osai.backend.user;

import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private UserRepository repository;

    UserController(UserRepository repository) {
        this.repository = repository;
    }

    @PostMapping(path="/createUser")
    public User createUser(@RequestBody User newUser) {
        return repository.save(newUser);
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        return repository.findAll();
    }

    @GetMapping("/getUserById/{id}")
    public User getUserById(@PathVariable long id) {
        return repository.findById(id).orElse(null);
    }

    @DeleteMapping("/deleteUserById/{id}")
    public void deleteUserById(@PathVariable long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        }
    }

    @PutMapping("/updateUserById/{id}")
    public User updateUserById(@RequestBody User newUser, @PathVariable long id) {
        return repository.findById(id).map(user -> {
            repository.deleteById(id);
            newUser.setId(id);
            return repository.save(newUser);
        }).orElseGet(() -> {
            newUser.setId(id);
            return repository.save(newUser);
        });
    }
}

