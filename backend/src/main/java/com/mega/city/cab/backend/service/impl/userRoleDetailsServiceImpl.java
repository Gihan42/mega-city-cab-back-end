package com.mega.city.cab.backend.service.impl;


import com.mega.city.cab.backend.entity.User;
import com.mega.city.cab.backend.entity.UserRoleDetails;
import com.mega.city.cab.backend.repo.UserRepo;
import com.mega.city.cab.backend.repo.userRoleDetailsRepo;
import com.mega.city.cab.backend.service.userRoleDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class userRoleDetailsServiceImpl implements userRoleDetailsService {

    @Autowired
    public userRoleDetailsRepo userRoleDetailsRepo;

    @Autowired
    UserRepo userRepo;

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

    @Override
    public void deleteUserRoleDetails(long userId) {
            List<UserRoleDetails> allUserRoleByUserId
                    = userRoleDetailsRepo.getAllUserRoleByUserId(userId);
            for (UserRoleDetails userRoleDetails : allUserRoleByUserId) {
                UserRoleDetails userRoleDetailsById = userRoleDetailsRepo.getUserRoleDetailsById(userRoleDetails.getUserRoleDetailsId());
                userRoleDetailsRepo.delete(userRoleDetailsById);
            }
        }

}
