package com.todo.todo_list.repository;

import com.todo.todo_list.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * @author by piyumi_navodani
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    List<Task> findTop5ByOrderByCreatedAtDesc();
}
