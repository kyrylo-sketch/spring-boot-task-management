package com.karol.tastManagement.controller;

import com.karol.tastManagement.model.Comment;
import com.karol.tastManagement.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/tasks/{taskId}/comments")
    public Comment addComment(@RequestBody Comment comment, @PathVariable(name = "taskId") String taskId){
        return commentService.save(comment, taskId);
    }

    @GetMapping("/tasks/{taskId}/comments")
    public List<Comment> getComments(@PathVariable(name = "taskId") String taskId){
        return commentService.findAllComments(taskId);
    }

    @DeleteMapping("/comments/{commentId}")
    public void deleteComment(@PathVariable(name = "commentId") String commentId){
        commentService.remove(commentId);
    }
}
