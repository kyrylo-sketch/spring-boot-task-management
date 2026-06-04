package com.karol.tastManagement.service;

import com.karol.tastManagement.model.Comment;
import com.karol.tastManagement.model.Task;
import com.karol.tastManagement.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TaskService taskService;

    public Comment save(Comment comment, String taskId){
        log.info("Saving comment request: comment{}, taskId={}", comment, taskId);
        Task task = taskService.findById(comment.getTaskId());
        if (task == null) {
            log.error("Cannot find task with id={}", comment.getTaskId());
            throw new IllegalArgumentException("Task not found: " + taskId);
        }
        commentRepository.save(comment);
        task.addComment(comment);
        taskService.save(task);
        log.info("Saving comment successful: comment{}, taskId={}", comment, taskId);
        return comment;
    }

    public List<Comment> findAllComments(String taskId){
        log.info("Finding all comment request: taskId={}", taskId);
        return commentRepository.findAllByTaskId(taskId);
    }

    public void  remove(String commentId){
        log.info("Removing comment request: commentId={}", commentId);
        commentRepository.deleteById(commentId);
    }
}
