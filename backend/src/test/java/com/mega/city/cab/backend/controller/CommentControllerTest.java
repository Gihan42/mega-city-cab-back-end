package com.mega.city.cab.backend.controller;

import com.mega.city.cab.backend.dto.CommentsDto;
import com.mega.city.cab.backend.entity.Comments;
import com.mega.city.cab.backend.entity.custom.CommentCustomResult;
import com.mega.city.cab.backend.entity.custom.CustomComment;
import com.mega.city.cab.backend.service.CommentService;
import com.mega.city.cab.backend.util.response.StandardResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CommentControllerTest {

    @InjectMocks
    private CommentController commentController;

    @Mock
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveComment() {
        CommentsDto commentsDto = new CommentsDto();
        Comments comments = new Comments();
        when(commentService.saveComment(commentsDto, "user")).thenReturn(comments);

        ResponseEntity<StandardResponse> response = commentController.saveComment(commentsDto, "user");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Comment Saved", response.getBody().getMessage());
        assertEquals(comments, response.getBody().getData());
        verify(commentService, times(1)).saveComment(commentsDto, "user");
    }

    @Test
    void testGetAllComments() {
        CommentCustomResult comment1 = mock(CommentCustomResult.class);
        CommentCustomResult comment2 = mock(CommentCustomResult.class);
        List<CommentCustomResult> comments = Arrays.asList(comment1, comment2);
        when(commentService.getAllComments("user")).thenReturn(comments);

        ResponseEntity<StandardResponse> response = commentController.getAllComments("user");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("all comments", response.getBody().getMessage());
        assertEquals(comments, response.getBody().getData());
        verify(commentService, times(1)).getAllComments("user");
    }

    @Test
    void testDeleteComment() {
        Long commentId = 1L;
        Comments comments = new Comments();
        when(commentService.deleteComment(commentId, "user")).thenReturn(comments);

        ResponseEntity<StandardResponse> response = commentController.deleteComment(commentId, "user");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Comment Deleted", response.getBody().getMessage());
        assertEquals(comments, response.getBody().getData());
        verify(commentService, times(1)).deleteComment(commentId, "user");
    }

    @Test
    void testGetCommentById() {
        Long commentId = 1L;
        Comments comments = new Comments();
        when(commentService.getCommentById(commentId, "user")).thenReturn(comments);

        ResponseEntity<StandardResponse> response = commentController.getCommentById(commentId, "user");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Comment Found", response.getBody().getMessage());
        assertEquals(comments, response.getBody().getData());
        verify(commentService, times(1)).getCommentById(commentId, "user");
    }

    @Test
    void testGetRandomComment() {
        CustomComment comment1 = mock(CustomComment.class);
        CustomComment comment2 = mock(CustomComment.class);
        List<CustomComment> comments = Arrays.asList(comment1, comment2);
        when(commentService.getRandomComments("user")).thenReturn(comments);

        ResponseEntity<StandardResponse> response = commentController.getRandomComment("user");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Random comments", response.getBody().getMessage());
        assertEquals(comments, response.getBody().getData());
        verify(commentService, times(1)).getRandomComments("user");
    }
}