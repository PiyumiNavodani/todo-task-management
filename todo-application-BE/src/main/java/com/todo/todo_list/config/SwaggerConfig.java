package com.todo.todo_list.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Configuration;

/**
 * @author by piyumi_navodani
 */

@Configuration
public class SwaggerConfig {

    public OpenAPI customeOpenApi(){
        return new OpenAPI()
                .info(new Info()
                        .title("Task Manager API")
                        .version("1.0")
                        .description("SpringBoot API for managing tasks and comments"));
    }
}
