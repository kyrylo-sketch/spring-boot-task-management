package com.karol.tastManagement.controller;

import com.karol.tastManagement.model.Project;
import com.karol.tastManagement.model.User;
import com.karol.tastManagement.model.UserPrincipal;
import com.karol.tastManagement.repository.UserRepository;
import com.karol.tastManagement.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@CrossOrigin
@RequestMapping("/api/projects")
public class ProjectController {
//    POST   /api/projects                    (create)
//    GET    /api/projects                    (list user's projects)
//    GET    /api/projects/{id}               (get one)
//    PUT    /api/projects/{id}               (update)
//    DELETE /api/projects/{id}               (delete)
    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public Project createProject(@RequestBody Project project,
                                 @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
        project.setUserId(user.get_id());
        return projectService.save(project);
    }

    @GetMapping
    public List<Project> getProjects(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
        return projectService.findAllUsersProjects(user.get_id());
    }

    @GetMapping("/{id}")
    public Project getProjectById(@PathVariable String id){
        return projectService.findById(id);
    }

    @PutMapping("/update")
    public Project updateProject(Project project){
        return projectService.updateProject(project);
    }

    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable String id){
        projectService.deleteProject(id);
    }

}
