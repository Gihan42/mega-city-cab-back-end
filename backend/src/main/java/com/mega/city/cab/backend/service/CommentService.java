package com.mega.city.cab.backend.service;

import com.mega.city.cab.backend.dto.CommentsDto;
import com.mega.city.cab.backend.entity.Comments;
import com.mega.city.cab.backend.entity.custom.CommentCustomResult;
import com.mega.city.cab.backend.entity.custom.CustomComment;

import java.util.List;

public interface CommentService {

    Comments saveComment(CommentsDto dto,String type);
    Comments deleteComment(Long commentId,String type);
    Comments getCommentById(Long commentId,String type);
    List<CommentCustomResult> getAllComments(String type);
    List<CustomComment> getRandomComments(String type);
}
