package com.karol.tastManagement.service;

import com.karol.tastManagement.model.Project;
import com.karol.tastManagement.model.User;
import com.karol.tastManagement.repository.ProjectRepository;
import com.karol.tastManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Service
public class ProjectService {
    //    POST   /api/projects                    (create)
//    GET    /api/projects                    (list user's projects)
//    GET    /api/projects/{id}               (get one)
//    PUT    /api/projects/{id}               (update)
//    DELETE /api/projects/{id}               (delete)
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    public Project save(Project project){
        User user = userRepository.findById(project.getUserId()).orElseThrow();
        user.addProject(project);
        projectRepository.save(project);
        userRepository.save(user);
        return project;
    }

    public List<Project> findAllUsersProjects(String userId){
        return projectRepository.findAllByUserId(userId);
    }

    public Project findById(String id){
        return projectRepository.findById(id).orElse(null);
    }

    public Project updateProject(Project project){
        return projectRepository.save(project);
    }

    public void deleteProject(String id){
        projectRepository.deleteById(id);
    }

}
