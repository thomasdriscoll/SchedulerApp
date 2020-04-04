package com.osai.backend.task;

import org.springframework.data.jpa.repository.JpaRepository;

// import com.osai.backend.Task;

interface TaskRepository extends JpaRepository<Task, Long> {
    
}