package com.mega.city.cab.backend.controller;

import com.mega.city.cab.backend.dto.CommentsDto;
import com.mega.city.cab.backend.dto.DriverDto;
import com.mega.city.cab.backend.entity.Comments;
import com.mega.city.cab.backend.entity.custom.CommentCustomResult;
import com.mega.city.cab.backend.entity.custom.CustomComment;
import com.mega.city.cab.backend.service.CommentService;
import com.mega.city.cab.backend.util.response.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comment")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class CommentController {

    @Autowired
    CommentService commentService;

//    save comment
    @PostMapping(path = "/save")
    public ResponseEntity<StandardResponse> saveComment(@RequestBody CommentsDto dto,
                                                        @RequestAttribute String type){
        Comments comments = commentService.saveComment(dto, type);
        return new ResponseEntity<>(
                new StandardResponse(200,"Comment Saved",comments),
                HttpStatus.OK
        );

    }
//    get all comments with user
    @GetMapping(path = "/allComments")
    public ResponseEntity<StandardResponse> getAllComments(@RequestAttribute String type){
        List<CommentCustomResult> allComments = commentService.getAllComments(type);
        return new ResponseEntity<>(
                new StandardResponse(200,"all comments",allComments),
                HttpStatus.OK
        );
    }
//    delete comment

    @DeleteMapping(params = {"commentId"})
    public ResponseEntity<StandardResponse> deleteComment(@RequestParam Long commentId,
                                                         @RequestAttribute String type){
        Comments comments = commentService.deleteComment(commentId, type);
        return new ResponseEntity<>(
                new StandardResponse(200,"Comment Deleted",comments),
                HttpStatus.OK
        );
    }

//    get comment by id
    @GetMapping(params = {"commentId"})
    public ResponseEntity<StandardResponse> getCommentById(@RequestParam Long commentId,
                                                          @RequestAttribute String type){
        Comments comments = commentService.getCommentById(commentId, type);
        return new ResponseEntity<>(
                new StandardResponse(200,"Comment Found",comments),
                HttpStatus.OK
        );
    }

//    get randomly four comments
@GetMapping(path = "/randomComment")
public ResponseEntity<StandardResponse> getRandomComment(@RequestAttribute String type) {
    List<CustomComment> randomComments = commentService.getRandomComments(type);
    return new ResponseEntity<>(
            new StandardResponse(200, "Random comments", randomComments),
            HttpStatus.OK
    );
}
}
