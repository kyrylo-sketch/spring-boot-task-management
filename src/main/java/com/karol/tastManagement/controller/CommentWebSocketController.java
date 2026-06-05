package com.karol.tastManagement.controller;

import com.karol.tastManagement.model.Comment;
import com.karol.tastManagement.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class CommentWebSocketController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/taskManagement.addComment")
    public void addComment(@Payload Comment comment) {
        System.out.println("COMMENT RECEIVED: " + comment);
        System.out.println("TASK ID: " + comment.getTaskId());

        if (comment.getTaskId() == null) {
            throw new IllegalArgumentException("taskId missing");
        }

        Comment saved = commentService.save(comment, comment.getTaskId());

        messagingTemplate.convertAndSend(
                "/topic/comments/" + comment.getTaskId(),
                saved
        );
    }
}
