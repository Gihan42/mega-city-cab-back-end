package com.mega.city.cab.backend.repo;

import com.mega.city.cab.backend.entity.Comments;
import com.mega.city.cab.backend.entity.custom.CommentCustomResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepo extends JpaRepository<Comments,Long> {

    @Query(value = "select * from comments where comment_id =:commentId and status='1'",nativeQuery = true)
    Comments getCommentById(long commentId);

    @Query(value = "select  c.comment_id as commentId,c.user_id as userId, u.username as username, c.comment as comment,c.date as date,c.status as status from comments c join user u ON c.user_id = u.user_id order by  comment_id desc",nativeQuery = true)
    List<CommentCustomResult> getAllComments();
}
