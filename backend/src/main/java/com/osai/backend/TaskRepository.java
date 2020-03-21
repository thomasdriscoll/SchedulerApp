package com.osai.backend;

import org.springframework.data.jpa.repository.JpaRepository;

// import com.osai.backend.Task;

interface TaskRepository extends JpaRepository<Task, Long> {
    
}