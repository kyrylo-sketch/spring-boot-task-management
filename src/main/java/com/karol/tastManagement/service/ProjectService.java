package com.karol.tastManagement.service;

import com.karol.tastManagement.model.Project;
import com.karol.tastManagement.model.User;
import com.karol.tastManagement.repository.ProjectRepository;
import com.karol.tastManagement.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    public Project save(Project project){
        log.info("Saving project request: userId={}", project.getUserId());
        User user = userRepository.findById(project.getUserId()).orElseThrow(
                () -> new NoSuchElementException("User not found: " + project.getUserId())
        );
        user.addProject(project);
        projectRepository.save(project);
        userRepository.save(user);
        log.info("Saving project successful: userId={}", project.getUserId());
        return project;
    }

    public List<Project> findAllUsersProjects(String userId){
        log.info("Finding all project by user request: userId={}", userId);
        return projectRepository.findAllByUserId(userId);
    }

    public Project findById(String id){
        log.info("Finding project by id request: id={}", id);
        return projectRepository.findById(id).orElse(null);
    }

    public Project updateProject(Project project){
        log.info("Updating project request: project={}", project);
        return projectRepository.save(project);
    }

    public void deleteProject(String id){
        log.info("Deleting project by id request: id={}", id);
        projectRepository.deleteById(id);
    }

}
