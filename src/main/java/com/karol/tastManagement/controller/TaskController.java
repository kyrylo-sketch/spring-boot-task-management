package com.karol.tastManagement.controller;

import com.karol.tastManagement.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
