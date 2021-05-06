package com.worker.people.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.worker.people.domain.entities.Comment;
import com.worker.people.domain.models.CommentCreateModel;
import com.worker.people.repositories.CommentRepository;
import com.worker.people.services.CommentService;
import com.worker.people.utils.CustomException;
import static com.worker.people.utils.messages.ResponseMessage.*;
import com.worker.people.utils.responses.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/api/comment")
public class CommentController {

    private final CommentService commentService;
    private final ObjectMapper objectMapper;

    @Autowired
    public CommentController( CommentService commentService, ObjectMapper objectMapper) {
        this.commentService = commentService;
        this.objectMapper = objectMapper;
    }

    /*public CommentController(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }*/

    /*@RequestMapping(method = RequestMethod.GET, value = "/comment/all")
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }*/

    @PostMapping(value = "/create")
    public ResponseEntity<Object> createComment(@RequestBody @Valid CommentCreateModel commentCreateModel) throws Exception {
        boolean comment = this.commentService.createComment(commentCreateModel);
        if (comment) {
            SuccessResponse successResponse = new SuccessResponse(LocalDateTime.now(), SUCCESSFUL_CREATE_COMMENT_MESSAGE, "", true);
            return new ResponseEntity<>(this.objectMapper.writeValueAsString(successResponse), HttpStatus.OK);
        }
        throw new CustomException(SERVER_ERROR_MESSAGE);
    }

    @PostMapping(value = "/remove")
    public ResponseEntity removeComment(@RequestBody Map<String, Object> body) throws Exception {
        String loggedInUserId = (String) body.get("loggedInUserId");
        String commentToRemoveId = (String) body.get("commentToRemoveId");

        CompletableFuture<Boolean> result = this.commentService.deleteComment(loggedInUserId, commentToRemoveId);
        if (result.get()) {
            SuccessResponse successResponse = new SuccessResponse(LocalDateTime.now(), SUCCESSFUL_DELETE_COMMENT_MESSAGE, "", true);
            return new ResponseEntity<>(this.objectMapper.writeValueAsString(successResponse), HttpStatus.OK);
        }
        throw new CustomException(SERVER_ERROR_MESSAGE);
    }

    /*@RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public Comment getCommentById(@PathVariable String id) {
        Optional<Comment> comment;
        comment = commentRepository.findById(id);
        return comment.orElseGet(Comment::new);
    }

    @RequestMapping(value = "/comment/{id}",method = RequestMethod.PUT)
    public ResponseEntity<Comment> updateTutorial(@PathVariable("id") String id, @RequestBody Comment comment){
        Optional<Comment> commentData = commentRepository.findById(id);

        if (commentData.isPresent()){
            Comment _comment = commentData.get();
            _comment.setAuthor(comment.getAuthor());
            _comment.setLoggedInUserId(comment.getLoggedInUserId());
            _comment.setContent(comment.getContent());
            _comment.setShareId(comment.getShareId());
            _comment.setLike_num(comment.getLike_num());
            _comment.setTime(comment.getTime());


            return new ResponseEntity<>(commentRepository.save(_comment), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/comment/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable("id") String id){
        try{
            commentRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/
}
