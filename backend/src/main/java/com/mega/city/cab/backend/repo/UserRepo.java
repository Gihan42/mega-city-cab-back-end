package com.mega.city.cab.backend.repo;

import com.mega.city.cab.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {
    @Query(value = "select * from user where email =?1 and status='1'", nativeQuery = true)
    User getUserByEmail(String email);
}

