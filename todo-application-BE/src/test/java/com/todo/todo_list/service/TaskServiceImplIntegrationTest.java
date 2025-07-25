package com.todo.todo_list.service;

import com.todo.todo_list.entity.Comment;
import com.todo.todo_list.entity.Task;
import com.todo.todo_list.repository.CommentRepository;
import com.todo.todo_list.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;


/**
 * @author by piyumi_navodani
 */
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
class TaskServiceImplIntegrationTest {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TaskService taskService;

    @Test
    void testCreateTaskIntegration() {
        Task task = new Task();
        task.setTitle("Integration Task");

        Task saved = taskService.createTask(task);

        assertNotNull(saved.getId());
        assertEquals("Integration Task", saved.getTitle());
    }

    @Test
    void testUpdateTaskIntegration() {
        Task task = new Task();
        task.setTitle("Initial");
        Task saved = taskRepository.save(task);

        Task update = new Task();
        update.setTitle("Updated");
        update.setDescription("Updated Desc");

        Task result = taskService.updateTask(saved.getId(), update);

        assertEquals("Updated", result.getTitle());
    }

    @Test
    void testToggleCompleteIntegration() {
        Task task = new Task();
        task.setCompleted(false);
        Task saved = taskRepository.save(task);

        Task result = taskService.toggleCompletion(saved.getId(), true);

        assertTrue(result.isCompleted());
    }

    @Test
    void testDeleteTaskIntegration() {
        Task task = new Task();
        Task saved = taskRepository.save(task);

        taskService.deletTask(saved.getId());

        assertFalse(taskRepository.findById(saved.getId()).isPresent());
    }

    @Test
    void testAddCommentIntegration() {
        Task task = new Task();
        task.setComments(new ArrayList<>());
        Task saved = taskRepository.save(task);

        Comment comment = new Comment();
        comment.setText("Test Comment");

        Task result = taskService.addComment(saved.getId(), comment);

        assertEquals(1, result.getComments().size());
    }
}
