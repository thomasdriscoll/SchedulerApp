package com.osai.backend.user;

import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private UserRepository repository;

    UserController(UserRepository repository) {
        this.repository = repository;
    }

    @PostMapping(path="/api/user/createUser")
    public Username createUser(@RequestBody Username newUser) {
        return repository.save(newUser);
    }

    @GetMapping(path="/api/user/all")
    public @ResponseBody Iterable<Username> getAllUsers() {
        return repository.findAll();
    }

    @GetMapping("/api/user/getUserById/{id}")
    public Username getUserById(@PathVariable long id) {
        return repository.findById(id).orElse(null);
    }

    @DeleteMapping("/api/user/deleteUserById/{id}")
    public void deleteUserById(@PathVariable long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        }
    }

    @PutMapping("/api/user/updateUserById/{id}")
    public Username updateUserById(@RequestBody Username newUser, @PathVariable long id) {
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

