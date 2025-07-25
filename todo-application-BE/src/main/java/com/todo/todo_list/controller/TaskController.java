package com.todo.todo_list.controller;

import com.todo.todo_list.entity.Comment;
import com.todo.todo_list.entity.Task;
import com.todo.todo_list.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * @author by piyumi_navodani
 */

@RestController
@RequestMapping("api/tasks")
@CrossOrigin
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    private static final Logger log = LoggerFactory.getLogger(TaskController.class);

    /**
     * This is the endpoint to create a new to-do task
     * @param task
     * @return Task
     */
    @PostMapping
    public Task createTask(@RequestBody final Task task){
        log.info("TaskController.createTask() started...");
        return taskService.createTask(task);
    }

    /**
     * This is the endpoint to edit a task
     * @param id
     * @param task
     * @return Task
     */
    @PutMapping("/{id}")
    public Task updateTask(@PathVariable final UUID id, @RequestBody Task task){
        log.info("TaskController.updateTask() started...");
        return taskService.updateTask(id, task);
    }

    /**
     * This is the end point to update the task as done by checked the checkbox
     * @param id
     * @param task
     * @return Task
     */
    @PatchMapping("/{id}")
    public Task toggleComplete(@PathVariable final UUID id, @RequestBody Task task){
        log.info("TaskController.toggleComplete() started...");
        return taskService.toggleCompletion(id, task.isCompleted());
    }

    /**
     * This is the endpoint to get tasks list
     * @param search
     * @param completed
     * @param dueDate
     * @param filterType
     * @return tasksList
     */
    @GetMapping
    public List<Task> getTasks(@RequestParam(required = false) String search,
                               @RequestParam(required = false) Boolean completed,
                               @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dueDate,
                               @RequestParam(required = false) String filterType){
        log.info("TaskController.getTasks() started...");
        return taskService.getTasks(search, completed, dueDate, filterType);
    }

    /**
     * This is the end point to get the task by task id
     * @param id
     * @return task
     */
    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable UUID id){
        log.info("TaskController.getTaskById() started...");
        return taskService.getTaskById(id);
    }

    /**
     * This is the end point to delete a task
     * @param id
     */
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable UUID id){
        log.info("TaskController.deleteTask() started...");
        taskService.deletTask(id);
    }

    /**
     * This is the end point to add comments to the task
     * @param id
     * @param comment
     * @return
     */
    @PostMapping("/{id}/comments")
    public Task addComment(@PathVariable UUID id, @RequestBody Comment comment){
        log.info("TaskController.addComment() started...");
        return taskService.addComment(id, comment);
    }
}
