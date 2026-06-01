package com.karol.tastManagement.controller;

import com.karol.tastManagement.model.Column;
import com.karol.tastManagement.model.Task;
import com.karol.tastManagement.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class TaskController {
    //    POST   /api/tasks                       (create)
//    GET    /api/projects/{projectId}/tasks  (list)
//    GET    /api/tasks/{id}                  (get one)
//    PUT    /api/tasks/{id}                  (update)
//    DELETE /api/tasks/{id}                  (delete)
//    PUT    /api/tasks/{id}/move             (move between columns)
    @Autowired
    private TaskService taskService;

    @PostMapping("tasks")
    public Task createTask(@RequestBody Task task){
        return taskService.save(task);
    }

    @GetMapping("/projects/{projectsId}/tasks")
    public List<Task> getAllTasks(@PathVariable String projectId){
        return taskService.findAll(projectId);
    }

    @GetMapping("/tasks{id}")
    public Task getTaskById(@PathVariable String id){
        return taskService.findById(id);
    }

    @PutMapping("/tasks{id}")
    public Task updateTak(@PathVariable String id){
        return taskService.update(id);
    }

    @DeleteMapping("/tasks{id}")
    public void deleteTask(@PathVariable String id){
        taskService.delete(id);
    }

    @PutMapping("/tasks/{id}/move")
    public void move(@RequestBody Column column,@PathVariable String id){
        taskService.move(column, id);
    }
}
