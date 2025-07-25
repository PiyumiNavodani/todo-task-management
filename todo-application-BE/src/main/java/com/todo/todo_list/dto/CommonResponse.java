package com.todo.todo_list.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * @author by piyumi_navodani
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponse {
    public String message;
    public HttpStatus status;
    public Object data;
}
