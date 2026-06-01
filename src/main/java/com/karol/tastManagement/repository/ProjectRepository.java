package com.karol.tastManagement.repository;

import com.karol.tastManagement.model.Comment;
import com.karol.tastManagement.model.Project;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends MongoRepository<Project, String> {
    List<Project> findByOwnerEmail(String email);
}
