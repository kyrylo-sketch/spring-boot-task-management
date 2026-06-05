package com.karol.tastManagement.controller;

import com.karol.tastManagement.model.Column;
import com.karol.tastManagement.model.Task;
import com.karol.tastManagement.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class TaskController {
    @Autowired
    private TaskService taskService;



    @GetMapping("/projects/{projectId}/tasks")
    public List<Task> getAllTasks(@PathVariable String projectId){
        return taskService.findAllById(projectId);
    }

    @GetMapping("/tasks/{id}")
    public Task getTaskById(@PathVariable String id){
        return taskService.findById(id);
    }

}
