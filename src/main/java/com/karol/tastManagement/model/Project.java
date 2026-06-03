package com.karol.tastManagement.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "projects")
public class Project {
    @Id
    private String _id;
    private String name;
    private List<Column> columns = new ArrayList<>(List.of(
            new Column("first","To Do", 0),
            new Column("second","In Progress", 1),
            new Column("thirt","Done", 2)));
    private List<Task> tasks = new ArrayList<>();
    private LocalDateTime createdAt;
    private String userId;

    public Project() {}

    public Project(String name, String userId) {
        this.name = name;
        this.userId = userId;
        this.createdAt = LocalDateTime.now();
    }

    @JsonProperty("_id")
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void addColumn(Column column) {
        this.columns.add(column);
    }

    public void removeColumn(Column column) {
        this.columns.remove(column);
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public void removeTask(Task task) {
        this.tasks.remove(task);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
