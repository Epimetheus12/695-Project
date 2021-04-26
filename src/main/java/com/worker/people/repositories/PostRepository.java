package com.worker.people.repositories;

import com.worker.people.domain.entities.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface PostRepository extends MongoRepository<Post,String> {
    List<Post> findAllByTimelineUserOrderByTimeDesc(String timelineUser);
}
