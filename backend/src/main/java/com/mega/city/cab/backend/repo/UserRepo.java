package com.mega.city.cab.backend.repo;

import com.mega.city.cab.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {
    @Query(value = "select * from user where email =?1 and status='1'", nativeQuery = true)
    User getUserByEmail(String email);

    @Query(value = "select * from user where user_id=?1 and status='1'", nativeQuery = true)
    User getUserById(Long userId);

    @Query(value = "select * from user where user.status='1' order by  user_id desc",nativeQuery = true)
    List<User> getAllUser();

    @Query(value = "select count(*) from user",nativeQuery = true)
    int getTotalUser();
}

