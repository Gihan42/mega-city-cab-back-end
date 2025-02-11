package com.mega.city.cab.backend.service.impl;

import com.mega.city.cab.backend.dto.CommentsDto;
import com.mega.city.cab.backend.entity.Comments;
import com.mega.city.cab.backend.entity.User;
import com.mega.city.cab.backend.entity.custom.CommentCustomResult;
import com.mega.city.cab.backend.entity.custom.CustomComment;
import com.mega.city.cab.backend.repo.CommentRepo;
import com.mega.city.cab.backend.repo.UserRepo;
import com.mega.city.cab.backend.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepo commentRepo;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserRepo userRepo;


    @Override
    public Comments saveComment(CommentsDto dto, String type) {
        if (!type.equals("User")){
            throw new BadCredentialsException("dont have permission");
        }
        User userById = userRepo.getUserById(dto.getUserId());
        if(!Objects.equals(userById,null)){
            Comments map = modelMapper.map(dto, Comments.class);
            return commentRepo.save(map);
        }
       throw new RuntimeException("user is exist");
    }

    @Override
    public Comments deleteComment(Long commentId, String type) {
        if (!type.equals("Admin")){
            throw new BadCredentialsException("dont have permission");
        }
        Comments commentById = commentRepo.getCommentById(commentId);
        if(!Objects.equals(commentById,null)){
            commentRepo.delete(commentById);
            return commentById;
        }
        throw new RuntimeException("comment not exist");

    }

    @Override
    public Comments getCommentById(Long commentId, String type) {
        if (!type.equals("Admin")){
            throw new BadCredentialsException("dont have permission");
        }
        Comments commentById = commentRepo.getCommentById(commentId);
        if(!Objects.equals(commentById,null)){
            return commentById;
        }
        throw new RuntimeException("comment not exist");
    }

    @Override
    public List<CommentCustomResult> getAllComments(String type) {
        if (!type.equals("Admin")){
            throw new BadCredentialsException("dont have permission");
        }
       return  commentRepo.getAllComments();

    }

    @Override
    public List<CustomComment> getRandomComments(String type) {
        if (!type.equals("User")){
            throw new BadCredentialsException("dont have permission");
        }
        return commentRepo.getRandomComments();
    }
}
