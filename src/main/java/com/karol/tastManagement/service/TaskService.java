package com.karol.tastManagement.service;

import com.karol.tastManagement.model.Column;
import com.karol.tastManagement.model.Project;
import com.karol.tastManagement.model.Task;
import com.karol.tastManagement.repository.ProjectRepository;
import com.karol.tastManagement.repository.TaskRepository;
import com.karol.tastManagement.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;


    @Autowired
    private ProjectRepository projectRepository;

    public Task save(Task task){
        log.info("Saving task request: task={}", task);
        Project project = projectRepository.findById(task.getProjectId()).orElseThrow();
        try{
            project.addTask(task);
            taskRepository.save(task);
            projectRepository.save(project);

        }catch (NullPointerException e){
            log.error("Saving task exception: task={}, project={}", task, project);
            throw e;
        }
        log.info("Saving task successful: task={}, project={}", task, project);
        return task;
    }

    public List<Task> findAllById(String projectId) {
        log.info("Finding all tasks by project request: projectId={}", projectId);
        return taskRepository.findAllByProjectId(projectId);
    }

    public Task findById(String id){
        log.info("Finding task by id request taskId={}", id);
        return taskRepository.findById(id).orElse(null);
    }

    public Task update(String taskId, Task updated){
        log.info("Updating task request: taskId={}, updated={} ", taskId, updated);
        updated.set_id(taskId);
        return taskRepository.save(updated);
    }

    public void delete(String id){
        log.info("Deleting task by id request taskId={}", id);
        taskRepository.deleteById(id);
    }

    public void move(Column column, String taskId){
        log.info("Moving task request: taskId={}, column={}", taskId, column);
        try{
            Task task = findById(taskId);
            task.setColumnId(column.get_id());
            taskRepository.save(task);
        }catch (NullPointerException e){
            log.error("Error NullPointerException: taskId={}, column={}", taskId, column);
            e.printStackTrace();
        }catch (Exception e){
            log.error("Error Exception: taskId={}, column={}", taskId, column);
            e.printStackTrace();
        }
        log.info("Moving task successful: taskId={}, column={}", taskId, column);

    }
}
