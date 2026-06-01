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

    public Project save(Project project, User owner){
        project.setOwner(owner);
        project.setCreatedAt(LocalDateTime.now());
        if (project.getColumns() == null || project.getColumns().isEmpty()) {
            project.setColumns(List.of(
                    new com.karol.tastManagement.model.Column("To Do", 0),
                    new com.karol.tastManagement.model.Column("In Progress", 1),
                    new com.karol.tastManagement.model.Column("Done", 2)
            ));
        }
        if (project.getTasks() == null) {
            project.setTasks(new ArrayList<>());
        }
        return projectRepository.save(project);
    }

    public List<Project> findAllUsersProjects(User user){
        return projectRepository.findByOwnerEmail(user.getEmail());
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
