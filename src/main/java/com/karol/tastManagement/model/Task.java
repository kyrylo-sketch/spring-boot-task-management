package com.karol.tastManagement.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Document(collection = "tasks")
public class Task {
    @Id
    private String id;
    private Project project;
    private String title;
    private String description;
    private Column column;
    private Priority priority;
    private LocalDateTime deadline;
    private List<Comment> comment;
    private LocalDateTime createdAt;

    public Task() {}

    public Task(Project project, String title, String description, Column column, Priority priority, LocalDateTime deadline) {
        this.project = project;
        this.title = title;
        this.description = description;
        this.column = column;
        this.priority = priority;
        this.deadline = deadline;
        this.createdAt = LocalDateTime.now();
    }

    public void addComment(Comment comment) {
        this.comment.add(comment);
    }

    public void removeComment(Comment comment) {
        this.comment.remove(comment);
    }

    public List<Comment> getComment() {
        return comment;
    }

    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
