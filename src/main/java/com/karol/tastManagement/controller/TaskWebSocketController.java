package com.karol.tastManagement.controller;

import com.karol.tastManagement.model.Task;
import com.karol.tastManagement.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class TaskWebSocketController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("taskManagement.createTask")
    @SendTo("/topic/projects")
    public Task createTask(@Payload Task task){
        return taskService.save(task);
    }

//    @MessageMapping("taskManagement.updateTask")
//    @SendTo("/topic/projects")
//    public Task updateTask(@Payload Task task){
//        re
//    }
}
