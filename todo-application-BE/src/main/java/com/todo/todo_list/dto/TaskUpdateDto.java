package com.todo.todo_list.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author by piyumi_navodani
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskUpdateDto {
    private Integer taskId;
    private Boolean taskStatus;
}
