package com.karol.tastManagement.service;
import com.karol.tastManagement.model.Project;
import com.karol.tastManagement.model.User;
import com.karol.tastManagement.repository.ProjectRepository;
import com.karol.tastManagement.repository.UserRepository;
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
public class ProjectServiceTest {
    @Mock
    ProjectRepository projectRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    ProjectService projectService;

    Project project;

    @BeforeEach
    void setUp() {
        project = new Project();
        project.set_id("proj_id");
        project.setName("proj_name");
        project.setUserId("user_id");
    }

    @Test
    void save_shouldSaveAndReturnIt(){
        //arrange
        User user = new User();
        user.set_id("user_id");
        when(userRepository.findById(project.getUserId())).thenReturn(Optional.of(user));
        when(projectRepository.save(project)).thenReturn(project);
        when(userRepository.save(user)).thenReturn(user);

        //act
        Project result = projectService.save(project);

        //assert
        assertNotNull(result);
        verify(projectRepository, times(1)).save(project);
        verify(userRepository, times(1)).save(user);
        verify(userRepository, times(1)).findById(project.getUserId());
    }

    @Test
    void save_shouldThrowWhenUserNotFound(){
        User user = new User();
        user.set_id("user_id");

        when(userRepository.findById(project.getUserId())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> projectService.save(project));
        verify(projectRepository, never()).save(project);

    }

    @Test
    void findAllUser_shouldReturnProjects(){
        User user = new User();
        user.set_id("user_id");

        when(projectRepository.findAllByUserId("user_id")).thenReturn(List.of(new Project(), new Project()));


        List<Project> result = projectService.findAllUsersProjects(user.get_id());


        assertEquals(2, result.size());
        verify(projectRepository).findAllByUserId("user_id");
    }

    @Test
    void findAllUsersProjects_shouldReturnEmptyList_whenUserNotFound(){
        when(projectRepository.findAllByUserId("empty")).thenReturn(List.of());

        List<Project> result = projectService.findAllUsersProjects("empty");

        assertEquals(0, result.size());
        verify(projectRepository).findAllByUserId("empty");
    }

    @Test
    void findById_shouldReturnIt(){
        when(projectRepository.findById("proj_id")).thenReturn(Optional.of(project));

        Project result = projectService.findById("proj_id");

        assertEquals(project, result);
        verify(projectRepository, times(1)).findById("proj_id");

    }

    @Test
    void findById_shouldReturnNull_whenProjectNotFound(){
        when(projectRepository.findById("empty")).thenReturn(Optional.empty());

        Project result = projectService.findById("empty");

        assertNull(result);
        verify(projectRepository).findById("empty");
    }

    @Test
    void updateProjet_shouldUpdateIt(){
        Project updated = new Project();
        updated.set_id(project.get_id());
        updated.setName("new name");
        when(projectRepository.save(updated)).thenReturn(updated);

        Project result = projectService.updateProject(updated);

        assertEquals("proj_id", result.get_id());
        verify(projectRepository, times(1)).save(updated);
    }

    @Test
    void deleteProject_shouldDeleteIt(){
        projectService.deleteProject("proj_id");

        verify(projectRepository, times(1)).deleteById(project.get_id());
    }
}
