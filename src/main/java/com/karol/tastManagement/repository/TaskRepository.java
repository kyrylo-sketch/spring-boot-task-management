package com.karol.tastManagement.repository;

import com.karol.tastManagement.model.Comment;
import com.karol.tastManagement.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends MongoRepository<Task, String> {
}
