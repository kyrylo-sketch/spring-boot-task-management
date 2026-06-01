package com.karol.tastManagement.service;

import com.karol.tastManagement.model.Column;
import com.karol.tastManagement.model.Project;
import com.karol.tastManagement.model.Task;
import com.karol.tastManagement.repository.ProjectRepository;
import com.karol.tastManagement.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
//    POST   /api/tasks                       (create)
//    GET    /api/projects/{projectId}/tasks  (list)
//    GET    /api/tasks/{id}                  (get one)
//    PUT    /api/tasks/{id}                  (update)
//    DELETE /api/tasks/{id}                  (delete)
//    PUT    /api/tasks/{id}/move             (move between columns)
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectService projectService;

    public Task save(Task task){
        return taskRepository.save(task);
    }

    public List<Task> findAll(String projectId){
        Project project =  projectService.findById(projectId);
        return project.getTasks();
    }

    public Task findById(String id){
        return taskRepository.findById(id).orElse(null);
    }

    public Task update(String taskId){
        Task task = taskRepository.findById(taskId).orElse(null);
        return taskRepository.save(task);
    }

    public void delete(String id){
        taskRepository.deleteById(id);
    }

    public void move(Column column, String taskId){
        Task task = findById(taskId);
        task.setColumn(column);
    }
}
