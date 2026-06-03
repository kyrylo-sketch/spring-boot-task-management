package com.karol.tastManagement.service;

import com.karol.tastManagement.model.Column;
import com.karol.tastManagement.model.Project;
import com.karol.tastManagement.model.Task;
import com.karol.tastManagement.repository.ProjectRepository;
import com.karol.tastManagement.repository.TaskRepository;
import com.karol.tastManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectRepository projectRepository;

    public Task save(Task task){
        Project project = projectRepository.findById(task.getProjectId()).orElseThrow();
        try{
            project.addTask(task);
            taskRepository.save(task);
            projectRepository.save(project);

        }catch (NullPointerException e){
            throw e;
        }
        return task;
    }

    public List<Task> findAllById(String projectId) {
        return taskRepository.findAllByProjectId(projectId);
    }

    public Task findById(String id){
        return taskRepository.findById(id).orElse(null);
    }

    public Task update(String taskId, Task updated){
        updated.set_id(taskId);
        return taskRepository.save(updated);
    }

    public void delete(String id){
        taskRepository.deleteById(id);
    }

    public void move(Column column, String taskId){
        Task task = findById(taskId);
        task.setColumnId(column.get_id());
        taskRepository.save(task);
    }
}
