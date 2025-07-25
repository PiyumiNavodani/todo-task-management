package com.todo.todo_list.service;

import com.todo.todo_list.entity.Comment;
import com.todo.todo_list.entity.Task;
import com.todo.todo_list.repository.CommentRepository;
import com.todo.todo_list.repository.TaskRepository;
import com.todo.todo_list.service.impl.TaskServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

/**
 * @author by piyumi_navodani
 */
@ExtendWith(MockitoExtension.class)
class TaskServiceImplUnitTest {
    @Mock
    private TaskRepository taskRepository;

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    void testCreateTask_success() {
        Task task = new Task();
        task.setTitle("New Task");

        Task savedTask = new Task();
        savedTask.setId(UUID.randomUUID());
        savedTask.setTitle("New Task");

        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        Task result = taskService.createTask(task);

        assertNotNull(result.getId());
        assertEquals("New Task", result.getTitle());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void testCreateTask_whenExceptionThrown() {
        Task task = new Task();
        task.setTitle("New Task");

        when(taskRepository.save(any(Task.class))).thenThrow(new RuntimeException("DB error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            taskService.createTask(task);
        });

        assertEquals("Failed to create task", exception.getMessage());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void testUpdateTask_success() {
        UUID id = UUID.randomUUID();
        Task existing = new Task();
        existing.setId(id);
        existing.setTitle("Old");

        Task updated = new Task();
        updated.setTitle("Updated");
        updated.setDescription("New desc");

        when(taskRepository.findById(id)).thenReturn(Optional.of(existing));
        when(taskRepository.save(any(Task.class))).thenReturn(existing);

        Task result = taskService.updateTask(id, updated);

        assertEquals("Updated", result.getTitle());
        verify(taskRepository).save(existing);
    }

    @Test
    void testUpdateTask_nullArguments() {
        UUID id = UUID.randomUUID();

        assertThrows(IllegalArgumentException.class, () -> taskService.updateTask(null, new Task()));
        assertThrows(IllegalArgumentException.class, () -> taskService.updateTask(id, null));
    }

    @Test
    void testToggleCompletion_success() {
        UUID id = UUID.randomUUID();
        Task task = new Task();
        task.setId(id);
        task.setCompleted(false);

        when(taskRepository.findById(id)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task result = taskService.toggleCompletion(id, true);

        assertTrue(result.isCompleted());
    }

    @Test
    void testToggleCompletion_nullId() {
        assertThrows(IllegalArgumentException.class, () -> taskService.toggleCompletion(null, true));
    }

    @Test
    void testDeleteTask_success() {
        UUID id = UUID.randomUUID();

        doNothing().when(taskRepository).deleteById(id);

        taskService.deletTask(id);

        verify(taskRepository).deleteById(id);
    }

    @Test
    void testDeletTask_nullId() {
        assertThrows(IllegalArgumentException.class, () -> taskService.deletTask(null));
    }

    @Test
    void testDeletTask_emptyResultDataAccessException() {
        UUID id = UUID.randomUUID();

        doThrow(new EmptyResultDataAccessException(1)).when(taskRepository).deleteById(id);

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> {
            taskService.deletTask(id);
        });

        assertTrue(ex.getMessage().contains(id.toString()));
    }

    @Test
    void testDeletTask_runtimeException() {
        UUID id = UUID.randomUUID();

        doThrow(new RuntimeException("DB error")).when(taskRepository).deleteById(id);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            taskService.deletTask(id);
        });

        assertEquals("Failed to delete task", ex.getMessage());
    }

    @Test
    void testAddComment_success() {
        UUID taskId = UUID.randomUUID();
        Task task = new Task();
        task.setId(taskId);
        task.setComments(new ArrayList<>());

        Comment comment = new Comment();
        comment.setText("Hello");

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task result = taskService.addComment(taskId, comment);

        assertEquals(1, result.getComments().size());
        verify(commentRepository).save(any(Comment.class));
    }

    @Test
    void testAddComment_nullTaskId() {
        Comment comment = new Comment();
        comment.setText("Sample");

        assertThrows(IllegalArgumentException.class, () -> taskService.addComment(null, comment));
    }

    @Test
    void testAddComment_nullComment() {
        UUID taskId = UUID.randomUUID();

        assertThrows(IllegalArgumentException.class, () -> taskService.addComment(taskId, null));
    }

    @Test
    void testGetTaskById_NotFound() {
        UUID id = UUID.randomUUID();
        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> taskService.getTaskById(id));
    }

    @Test
    void testGetTaskById_success() {
        UUID id = UUID.randomUUID();
        Task mockTask = new Task();
        mockTask.setId(id);
        mockTask.setTitle("Sample Task");

        when(taskRepository.findById(id)).thenReturn(Optional.of(mockTask));

        Task result = taskService.getTaskById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Sample Task", result.getTitle());
        verify(taskRepository, times(1)).findById(id);
    }
    @Test
    void testGetTasks_ReturnsTop5Tasks() {
        Task task1 = new Task();
        task1.setId(UUID.randomUUID());
        task1.setTitle("Task 1");
        task1.setCreatedAt(LocalDateTime.now());

        Task task2 = new Task();
        task2.setId(UUID.randomUUID());
        task2.setTitle("Task 2");
        task2.setCreatedAt(LocalDateTime.now());

        List<Task> mockTasks = Arrays.asList(task1, task2);
        when(taskRepository.findTop5ByOrderByCreatedAtDesc()).thenReturn(mockTasks);

        List<Task> result = taskService.getTasks(null, null, null, null);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(taskRepository, times(1)).findTop5ByOrderByCreatedAtDesc();
    }


    @Test
    void testGetTasks_ThrowsRuntimeExceptionOnFailure() {
        // Arrange
        when(taskRepository.findTop5ByOrderByCreatedAtDesc()).thenThrow(new RuntimeException("DB Error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                taskService.getTasks(null, null, null, null)
        );
        assertEquals("Failed to fetch tasks", exception.getMessage());
        verify(taskRepository, times(1)).findTop5ByOrderByCreatedAtDesc();
    }

}
