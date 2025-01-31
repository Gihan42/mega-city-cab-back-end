package com.mega.city.cab.backend.service.impl;


import com.mega.city.cab.backend.entity.UserRoleDetails;
import com.mega.city.cab.backend.repo.userRoleDetailsRepo;
import com.mega.city.cab.backend.service.userRoleDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class userRoleDetailsServiceImpl implements userRoleDetailsService {

    @Autowired
    userRoleDetailsRepo userRoleDetailsRepo;

    @Override
    public void saveUserRoleDetails(long userId, long roleId) {

        UserRoleDetails userRoleDetails = new UserRoleDetails(
                (long)0,
                roleId,
                userId,
                "1"

        );
        userRoleDetailsRepo.save(userRoleDetails);
    }
}
