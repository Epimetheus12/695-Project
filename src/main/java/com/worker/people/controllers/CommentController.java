package com.worker.people.controllers;

import com.worker.people.domain.entities.Comment;
import com.worker.people.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api3")
public class CommentController {

    @Autowired
    CommentRepository commentRepository;

    public CommentController(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/comments")
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/createComment")
    public Comment save(@RequestBody Comment comment){
        commentRepository.save(comment);

        return comment;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Comment getCommentById(@PathVariable String id) {
        Optional<Comment> comment;
        comment = commentRepository.findById(id);
        return comment.orElseGet(Comment::new);
    }

    @PutMapping("/comments/{id}")
    public ResponseEntity<Comment> updateTutorial(@PathVariable("id") String id, @RequestBody Comment comment){
        Optional<Comment> commentData = commentRepository.findById(id);

        if (commentData.isPresent()){
            Comment _comment = commentData.get();
            _comment.setAuthor(comment.getAuthor());
            _comment.setAuthorId(comment.getAuthorId());
            _comment.setContent(comment.getContent());
            _comment.setShareId(comment.getShareId());
            _comment.setLike_num(comment.getLike_num());
            _comment.setTime(comment.getTime());


            return new ResponseEntity<>(commentRepository.save(_comment), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable("id") String id){
        try{
            commentRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
