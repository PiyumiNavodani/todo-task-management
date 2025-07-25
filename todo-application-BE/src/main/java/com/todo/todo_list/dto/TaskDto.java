package com.todo.todo_list.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author by piyumi_navodani
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    private Integer taskId;
    private String taskName;
    private String taskDescription;
    private Boolean taskStatus;
    private LocalDateTime createdDate;
}
