package com.todo.todo_list.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * @author by piyumi_navodani
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequestDto {
    @NotNull(message = "Task Name is required")
    private String taskName;
    @NotNull(message = "Task Description is required")
    private String taskDescription;
}
