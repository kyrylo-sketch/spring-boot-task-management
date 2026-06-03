package com.karol.tastManagement.service;

import com.karol.tastManagement.model.*;
import com.karol.tastManagement.repository.ProjectRepository;
import com.karol.tastManagement.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    TaskRepository taskRepository;

    @Mock
    ProjectRepository projectRepository;

    @InjectMocks
    TaskService taskService;

    Task task;
    Project project;


    @BeforeEach
    void setUp() {
        task = new Task();
        task.set_id("task_id");
        task.setTitle("title");
        task.setDescription("description");
        task.setPriority(Priority.MEDIUM);
        task.setProjectId("proj_id");

        project = new Project();
        project.set_id("proj_id");
    }

    @Test
    void save_shouldSaveAndReturnIt() {
        //arrange

        when(projectRepository.findById(task.getProjectId())).thenReturn(Optional.of(project));
        when(taskRepository.save(task)).thenReturn(task);

        //act
        Task result = taskService.save(task);

        //assert
        assertNotNull(result);
        verify(taskRepository).save(task);
        verify(projectRepository).save(project);

    }

    @Test
    void save_shouldThrowWhenProjectNotFound() {
        // GIVEN
        Task task = new Task();
        task.setProjectId("nieistniejace-id");

        when(projectRepository.findById("nieistniejace-id"))
                .thenReturn(Optional.empty());

        // WHEN + THEN
        assertThrows(NoSuchElementException.class, () -> taskService.save(task));
        verify(taskRepository, never()).save(any());
    }

    @Test
    void findAllById_shouldReturnTasks(){
        //arrange
        when(taskRepository.findAllByProjectId("proj_id")).thenReturn(List.of(task));

        //act
        List<Task> result = taskService.findAllById("proj_id");

        //assert
        assertEquals(1, result.size());
        verify(taskRepository).findAllByProjectId("proj_id");
    }

    @Test
    void findAllById_shouldReturnEmptyList_whenProjectIfNotFound() {
        //arrange
        when(taskRepository.findAllByProjectId("empty")).thenReturn(List.of());

        //act
        List<Task> result = taskService.findAllById("empty");

        //asserr
        assertEquals(0, result.size());
        verify(taskRepository).findAllByProjectId("empty");

    }

    @Test
    void findById_shouldReturnTask(){
        //arrange
        when(taskRepository.findById("task_id")).thenReturn(Optional.of(task));

        //act
        Task result = taskService.findById("task_id");

        //assert
        assertEquals(task, result);
        verify(taskRepository).findById("task_id");
    }

    @Test
    void findById_shouldReturnNull_whenTaskNotFound(){
        //arrange
        when(taskRepository.findById("empty")).thenReturn(Optional.empty());

        //act
        Task result = taskService.findById("empty");

        //assert
        assertNull(result);
        verify(taskRepository).findById("empty");
    }


    @Test
    void update_shouldUpdateTask() {
        Task updated = new Task();
        updated.setTitle("Nowy tytuł");
        when(taskRepository.save(any(Task.class))).thenReturn(updated);

        taskService.update("task-123", updated);

        assertEquals("task-123", updated.get_id());
        verify(taskRepository).save(updated);

    }


    @Test
    void delete_shouldDeleteTask(){
        //act
        taskService.delete("task_id");

        verify(taskRepository).deleteById("task_id");
    }

    @Test
    void moveColumn_shouldMoveColumn(){
        //arrange
        Column column = new Column("nowa-kolumna", "Done", 2);

        when(taskRepository.findById("task_id")).thenReturn(Optional.of(task));
        //act
        taskService.move(column, "task_id");

        //assert
        assertEquals("nowa-kolumna", task.getColumnId());
        verify(taskRepository).save(task);
    }
}
