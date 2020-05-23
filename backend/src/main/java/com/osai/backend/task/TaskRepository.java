package com.osai.backend.task;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TaskRepository extends JpaRepository<Task, Long>
{
    @Query("select * from task t where t.username = ?1 and t.ancestry like ?")
    List<Task> getTreeByUser(String username, Sort sort);
}


