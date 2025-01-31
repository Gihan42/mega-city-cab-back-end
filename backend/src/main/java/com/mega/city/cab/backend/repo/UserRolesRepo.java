package com.mega.city.cab.backend.repo;

import com.mega.city.cab.backend.entity.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRolesRepo extends JpaRepository<UserRoles,Long> {
    @Query(value = "select * from user_roles where user_role=?1",nativeQuery = true)
    UserRoles findByRole(String role);
}

