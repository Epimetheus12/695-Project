package com.worker.people.repositories;

import com.worker.people.domain.entities.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findByIdInOrderByTimeDesc(List<String> comments);
}
