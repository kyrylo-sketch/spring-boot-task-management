package com.karol.tastManagement.service;

import com.karol.tastManagement.model.Comment;
import com.karol.tastManagement.model.Task;
import com.karol.tastManagement.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
//    POST   /api/tasks/{taskId}/comments     (add)
//    GET    /api/tasks/{taskId}/comments     (list)
//    DELETE /api/comments/{id}               (delete)

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TaskService taskService;

    public Comment save(Comment comment, String taskId){
        Task task = taskService.findById(comment.getTaskId());
        if (task == null) throw new IllegalArgumentException("Task not found: " + taskId);
        commentRepository.save(comment);
        task.addComment(comment);
        taskService.save(task);
        return comment;
    }

    public List<Comment> findAllComments(String taskId){
        return commentRepository.findAllByTaskId(taskId);
    }

    public void  remove(String commentId){
        commentRepository.deleteById(commentId);
    }
}
