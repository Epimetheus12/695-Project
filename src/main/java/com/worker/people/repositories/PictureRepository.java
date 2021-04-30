package com.worker.people.repositories;

import com.worker.people.domain.entities.Picture;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface PictureRepository extends MongoRepository<Picture, String> {
    List<Picture> findAllByUserId(String userId);
}
