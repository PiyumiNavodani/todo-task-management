package com.todo.todo_list.service.impl;

import com.todo.todo_list.dto.CommonResponse;
import com.todo.todo_list.dto.TaskDto;
import com.todo.todo_list.dto.TaskRequestDto;
import com.todo.todo_list.dto.TaskUpdateDto;
import com.todo.todo_list.entity.Comment;
import com.todo.todo_list.entity.Task;
import com.todo.todo_list.repository.CommentRepository;
import com.todo.todo_list.repository.TaskRepository;
import com.todo.todo_list.service.TaskService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author by piyumi_navodani
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final CommentRepository commentRepository;

    /**
     * This method is to create a new to-do task
     * @param task
     * @return task
     */
    @Override
    public Task createTask(Task task) {
        log.info("TaskServiceImpl.createTask() started.");
        if (task == null) {
            log.warn("Provided task is null. Cannot create task.");
            throw new IllegalArgumentException("Task must not be null");
        }
        try{
            log.info("Creating a new task...");
            task.setId(null);
            task.setCreatedAt(LocalDateTime.now());
            task.setUpdatedAt(LocalDateTime.now());
            task.setPriority(task.getPriority());

            Task savedTask = taskRepository.save(task);

            log.info("Task created successfully with ID: {}", savedTask.getId());
            return savedTask;
        }catch (Exception e){
            log.error("Error occurred while creating task: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to create task", e);
        }
    }

    /**
     * This method is to edit a task
     * @param id
     * @param updated
     * @return task
     */
    @Override
    public Task updateTask(UUID id, Task updated) {
        log.info("TaskServiceImpl.updateTask() started.");
        if (id == null || updated == null) {
            log.warn("Task ID or updated task is null. Cannot proceed with update.");
            throw new IllegalArgumentException("Task ID and updated task must not be null.");
        }

        try {
            Task task = getTaskById(id);
            log.info("Updating task with ID: {}", id);
            task.setTitle(updated.getTitle());
            task.setDescription(updated.getDescription());
            task.setDueDate(updated.getDueDate());
            task.setUpdatedAt(LocalDateTime.now());
            task.setCompleted(updated.isCompleted());
            task.setPriority(updated.getPriority());

            Task savedTask = taskRepository.save(task);

            log.info("Task updated successfully. ID: {}", savedTask.getId());

            return savedTask;

        } catch (EntityNotFoundException e) {
            log.error("Task not found with ID: {}", id);
            throw e;
        } catch (Exception e) {
            log.error("Error while updating task with ID: {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to update task", e);
        }
    }

    /**
     * This method is to update the task as done by checked the checkbox
     * @param id
     * @param completed
     * @return task
     */
    @Override
    public Task toggleCompletion(UUID id, boolean completed) {
        log.info("TaskServiceImpl.toggleCompletion() started.");
        if (id == null) {
            log.warn("Task ID is null. Cannot toggle completion.");
            throw new IllegalArgumentException("Task ID must not be null.");
        }
        try {
            Task task = getTaskById(id);
            log.info("Toggling completion status for task with ID: {} to {}", id, completed);
            task.setCompleted(completed);
            Task savedTask = taskRepository.save(task);

            log.info("Task completion status updated successfully. ID: {}, Completed: {}", savedTask.getId(), savedTask.isCompleted());

            return savedTask;
        } catch (EntityNotFoundException e) {
            log.error("Task not found with ID: {}", id);
            throw e;
        } catch (Exception e) {
            log.error("Error while toggling completion for task ID: {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to toggle task completion", e);
        }
    }

    /**
     * This method is to delete a task
     * @param id
     */
    @Override
    public void deletTask(UUID id) {
        log.info("TaskServiceImpl.deletTask() started.");
        if (id == null) {
            log.warn("Task ID is null. Cannot delete task.");
            throw new IllegalArgumentException("Task ID must not be null.");
        }
        try {
            log.info("Deleting task with ID: {}", id);
            taskRepository.deleteById(id);
            log.info("Task deleted successfully. ID: {}", id);
        } catch (EmptyResultDataAccessException e) {
            log.error("Task with ID {} not found. Nothing to delete.", id);
            throw new EntityNotFoundException("Task not found with ID: " + id);
        } catch (Exception e) {
            log.error("Error while deleting task with ID: {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to delete task", e);
        }
    }

    /**
     * This method is to get the task by task id
     * @param id
     * @return task
     */
    @Override
    public Task getTaskById(UUID id) {
        log.info("TaskServiceImpl.getTaskById() started.");
        if (id == null) {
            log.warn("Task ID is null. Cannot fetch task.");
            throw new IllegalArgumentException("Task ID must not be null.");
        }
        try {
            return taskRepository.findById(id)
                    .orElseThrow(() -> {
                        log.error("Task not found with ID: {}", id);
                        return new EntityNotFoundException("Task not found with ID: " + id);
                    });
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error while fetching task with ID: {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to fetch task", e);
        }
    }

    /**
     * This method is to get tasks list
     * @param search
     * @param completed
     * @param dueDate
     * @param filterType
     * @return tasksList
     */
    @Override
    public List<Task> getTasks(String search, Boolean completed, LocalDate dueDate, String filterType) {
        log.info("TaskServiceImpl.getTasks() started.");
        try {
            return taskRepository.findTop5ByOrderByCreatedAtDesc();
        } catch (Exception e) {
            log.error("Error while fetching tasks list: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch tasks", e);
        }
    }

    /**
     * This method is to add comments to the task
     * @param taskId
     * @param comment
     * @return
     */
    @Override
    public Task addComment(UUID taskId, Comment comment) {
        log.info("TaskServiceImpl.addComment() started.");
        if (taskId == null) {
            log.warn("Task ID is null. Cannot add comment.");
            throw new IllegalArgumentException("Task ID must not be null.");
        }
        if (comment == null) {
            log.warn("Comment is null. Cannot add comment.");
            throw new IllegalArgumentException("Comment must not be null.");
        }
        try {
            Task task = getTaskById(taskId);
            comment.setId(null);
            comment.setText(comment.getText());
            comment.setTimeStamp(LocalDateTime.now());
            comment.setTask(task);
            task.getComments().add(comment);
            commentRepository.save(comment);
            Task savedTask = taskRepository.save(task);
            log.info("Comment added successfully to task with ID: {}", taskId);
            return savedTask;
        } catch (EntityNotFoundException e) {
            log.error("Task not found with ID: {}", taskId);
            throw e;
        } catch (Exception e) {
            log.error("Error adding comment to task with ID {}: {}", taskId, e.getMessage(), e);
            throw new RuntimeException("Failed to add comment", e);
        }
    }
}
