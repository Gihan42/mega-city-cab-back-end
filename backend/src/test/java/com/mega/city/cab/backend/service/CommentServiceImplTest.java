package com.mega.city.cab.backend.service;

import com.mega.city.cab.backend.dto.CommentsDto;
import com.mega.city.cab.backend.entity.Comments;
import com.mega.city.cab.backend.entity.User;
import com.mega.city.cab.backend.entity.custom.CommentCustomResult;
import com.mega.city.cab.backend.entity.custom.CustomComment;
import com.mega.city.cab.backend.repo.CommentRepo;
import com.mega.city.cab.backend.repo.UserRepo;
import com.mega.city.cab.backend.service.impl.CommentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CommentServiceImplTest {

    @Mock
    private CommentRepo commentRepo;

    @Mock
    private UserRepo userRepo;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CommentServiceImpl commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveComment_Success() {
        // Arrange
        CommentsDto dto = new CommentsDto();
        dto.setUserId(1L);
        User user = new User();
        user.setUserId(1L);
        Comments comment = new Comments();
        comment.setCommentId(1L);

        when(userRepo.getUserById(1L)).thenReturn(user);
        when(modelMapper.map(dto, Comments.class)).thenReturn(comment);
        when(commentRepo.save(comment)).thenReturn(comment);

        // Act
        Comments result = commentService.saveComment(dto, "User");

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getCommentId());
        verify(userRepo, times(1)).getUserById(1L);
        verify(commentRepo, times(1)).save(comment);
    }

    @Test
    void testSaveComment_UserNotExist() {
        // Arrange
        CommentsDto dto = new CommentsDto();
        dto.setUserId(1L);

        when(userRepo.getUserById(1L)).thenReturn(null);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> commentService.saveComment(dto, "User"));
        verify(userRepo, times(1)).getUserById(1L);
    }

    @Test
    void testSaveComment_NoPermission() {
        // Arrange
        CommentsDto dto = new CommentsDto();

        // Act & Assert
        assertThrows(BadCredentialsException.class, () -> commentService.saveComment(dto, "Admin"));
    }

    @Test
    void testDeleteComment_Success() {
        // Arrange
        Comments comment = new Comments();
        comment.setCommentId(1L);

        when(commentRepo.getCommentById(1L)).thenReturn(comment);
        doNothing().when(commentRepo).delete(comment);

        // Act
        Comments result = commentService.deleteComment(1L, "Admin");

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getCommentId());
        verify(commentRepo, times(1)).getCommentById(1L);
        verify(commentRepo, times(1)).delete(comment);
    }

    @Test
    void testDeleteComment_CommentNotExist() {
        // Arrange
        when(commentRepo.getCommentById(1L)).thenReturn(null);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> commentService.deleteComment(1L, "Admin"));
        verify(commentRepo, times(1)).getCommentById(1L);
    }

    @Test
    void testDeleteComment_NoPermission() {
        // Arrange & Act & Assert
        assertThrows(BadCredentialsException.class, () -> commentService.deleteComment(1L, "User"));
    }

    @Test
    void testGetCommentById_Success() {
        // Arrange
        Comments comment = new Comments();
        comment.setCommentId(1L);

        when(commentRepo.getCommentById(1L)).thenReturn(comment);

        // Act
        Comments result = commentService.getCommentById(1L, "Admin");

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getCommentId());
        verify(commentRepo, times(1)).getCommentById(1L);
    }

    @Test
    void testGetCommentById_CommentNotExist() {
        // Arrange
        when(commentRepo.getCommentById(1L)).thenReturn(null);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> commentService.getCommentById(1L, "Admin"));
        verify(commentRepo, times(1)).getCommentById(1L);
    }

    @Test
    void testGetCommentById_NoPermission() {
        // Arrange & Act & Assert
        assertThrows(BadCredentialsException.class, () -> commentService.getCommentById(1L, "User"));
    }

    @Test
    void testGetAllComments_Success() {
        // Arrange
        CommentCustomResult commentCustomResult = mock(CommentCustomResult.class); // Create a mock instance
        List<CommentCustomResult> comments = Collections.singletonList(commentCustomResult);

        when(commentRepo.getAllComments()).thenReturn(comments);

        // Act
        List<CommentCustomResult> result = commentService.getAllComments("Admin");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(commentRepo, times(1)).getAllComments();
    }

    @Test
    void testGetAllComments_NoPermission() {
        // Arrange & Act & Assert
        assertThrows(BadCredentialsException.class, () -> commentService.getAllComments("User"));
    }

    @Test
    void testGetRandomComments_Success() {
        // Arrange
        CustomComment customComment = mock(CustomComment.class); // Create a mock instance
        List<CustomComment> comments = Collections.singletonList(customComment);

        when(commentRepo.getRandomComments()).thenReturn(comments);

        // Act
        List<CustomComment> result = commentService.getRandomComments("User");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(commentRepo, times(1)).getRandomComments();
    }

    @Test
    void testGetRandomComments_NoPermission() {
        // Arrange & Act & Assert
        assertThrows(BadCredentialsException.class, () -> commentService.getRandomComments("Admin"));
    }
}