package com.karol.tastManagement.service;
import com.karol.tastManagement.model.Comment;
import com.karol.tastManagement.model.Task;
import com.karol.tastManagement.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @Mock
    CommentRepository commentRepository;

    @Mock
    TaskService taskService;

    @InjectMocks
    CommentService commentService;

    Comment comment;

    @BeforeEach
    void setUp() {
        comment = new Comment();
        comment.set_id("comm_id");
        comment.setContent("content");
        comment.setTaskId("task_id");
        comment.setUserId("user_id");
    }

    @Test
    void saveComment_shouldSaveAndReturnIt(){
        Task task = new Task();
        task.set_id("task_id");
        when(taskService.findById(comment.getTaskId())).thenReturn(task);
        when(commentRepository.save(comment)).thenReturn(comment);
        when(taskService.save(task)).thenReturn(task);

        Comment result = commentService.save(comment, comment.getTaskId());

        assertNotNull(result);
        assertEquals(comment, task.getComment().getFirst());
        verify(commentRepository).save(comment);
        verify(taskService).save(task);
        verify(taskService).findById(comment.getTaskId());
    }

    @Test
    void saveComment_shouldThrow_whenTaskNotFound(){
        when(taskService.findById(comment.getTaskId())).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> commentService.save(comment, comment.getTaskId()));
        verify(commentRepository, never()).save(any());
    }

    @Test
    void findAllComments_shouldReturnAllByTaskId(){
        when(commentRepository.findAllByTaskId(comment.getTaskId())).thenReturn(List.of(new Comment(), new Comment()));

        List<Comment> result = commentService.findAllComments(comment.getTaskId());

        assertEquals(2, result.size());
        verify(commentRepository).findAllByTaskId(comment.getTaskId());
    }

    @Test
    void findAllComments_shouldReturnEmptyList_whenTaskNotFound(){
        when(commentRepository.findAllByTaskId("empty")).thenReturn(List.of());

        List<Comment> result = commentService.findAllComments("empty");

        assertEquals(0, result.size());
        verify(commentRepository).findAllByTaskId("empty");
    }

    @Test
    void removeComment_shouldRemoveComment(){
        commentService.remove(comment.get_id());

        verify(commentRepository,  times(1)).deleteById(comment.get_id());
    }


}
