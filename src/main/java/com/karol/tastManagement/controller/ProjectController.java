package com.karol.tastManagement.controller;

import com.karol.tastManagement.model.Project;
import com.karol.tastManagement.model.User;
import com.karol.tastManagement.model.UserPrincipal;
import com.karol.tastManagement.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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

    @PostMapping
    public Project createProject(@RequestBody Project project, Authentication authentication){
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        return projectService.save(project, principal.getUser());
    }

    @GetMapping
    public List<Project> getProjects(Authentication authentication){
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        return projectService.findAllUsersProjects(principal.getUser());
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
    public void deleteProject(@PathVariable(name = "id") String id){
        projectService.deleteProject(id);
    }

}
