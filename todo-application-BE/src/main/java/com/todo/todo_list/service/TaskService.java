package com.todo.todo_list.service;

import com.todo.todo_list.dto.CommonResponse;
import com.todo.todo_list.dto.TaskRequestDto;
import com.todo.todo_list.dto.TaskUpdateDto;
import com.todo.todo_list.entity.Comment;
import com.todo.todo_list.entity.Task;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * @author by piyumi_navodani
 */
public interface TaskService {
    /**
     * This method is to create a new to-do task
     * @param task
     * @return task
     */
    Task createTask(final Task task);

    /**
     * This method is to edit a task
     * @param id
     * @param updated
     * @return task
     */
    Task updateTask(final UUID id, final Task updated);

    /**
     * This method is to update the task as done by checked the checkbox
     * @param id
     * @param completed
     * @return task
     */
    Task toggleCompletion(final UUID id, final boolean completed);

    /**
     * This method is to delete a task
     * @param id
     */
    void deletTask(final UUID id);

    /**
     * This method is to get the task by task id
     * @param id
     * @return task
     */
    Task getTaskById(final UUID id);

    /**
     * This method is to get tasks list
     * @param search
     * @param completed
     * @param dueDate
     * @param filterType
     * @return tasksList
     */
    List<Task> getTasks(final String search, final Boolean completed, final LocalDate dueDate, final String filterType);

    /**
     * This method is to add comments to the task
     * @param taskId
     * @param comment
     * @return
     */
    Task addComment(final UUID taskId, final Comment comment);
}
