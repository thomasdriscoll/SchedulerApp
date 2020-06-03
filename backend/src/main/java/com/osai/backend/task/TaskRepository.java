package com.osai.backend.task;

import java.util.ArrayList;

// import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TaskRepository extends JpaRepository<Task, Long>
{
    @Query("select t from Task t where t.username = ?1 ORDER BY t.ancestry ASC")
    ArrayList<Task> getTreeByUser(String username);
}


