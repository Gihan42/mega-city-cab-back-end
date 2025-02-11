package com.mega.city.cab.backend.service.impl;



import com.mega.city.cab.backend.dto.AuthenticationRequestDTO;
import com.mega.city.cab.backend.dto.UserPasswordDto;
import com.mega.city.cab.backend.dto.userDto;
import com.mega.city.cab.backend.entity.User;
import com.mega.city.cab.backend.entity.UserRoles;
import com.mega.city.cab.backend.repo.UserRepo;
import com.mega.city.cab.backend.repo.UserRolesRepo;
import com.mega.city.cab.backend.repo.userRoleDetailsRepo;
import com.mega.city.cab.backend.service.userRoleDetailsService;
import com.mega.city.cab.backend.service.userService;
import com.mega.city.cab.backend.util.JwtUtil;
import com.mega.city.cab.backend.util.response.LoginResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class userServiceImpl implements userService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserRepo userRepo;

    @Autowired
    userRoleDetailsRepo userRoleDetailsRepo;

    @Autowired
    userRoleDetailsService  userRoleDetailsService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRolesRepo userRolesRepo;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    JwtUtil jwtUtil;

    @Override
    public LoginResponse logUser(AuthenticationRequestDTO dto) {

        try{
            String userRole="";
            User userByEmail = userRepo.getUserByEmail(dto.getEmail());
            List<String> userRoleByUserEmail = userRoleDetailsRepo.getUserRoleByUserEmail(dto.getEmail());
            ArrayList<String> roles = new ArrayList<>();
            for (String role : userRoleByUserEmail) {
                if(role.equalsIgnoreCase("Admin")|| role.equalsIgnoreCase("User")){
                    userRole = role;
                    break;

                }
                roles.add(role);
            }
            if(Objects.equals(userByEmail,null)){
                throw new BadCredentialsException("invalid details");
            }

            LoginResponse loginResponse = new LoginResponse();
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userByEmail.getEmail(), dto.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(userByEmail.getEmail());

            if(userRole.equalsIgnoreCase("Admin")|| userRole.equalsIgnoreCase("User")){
                String generateToken = jwtUtil.generateToken(userDetails, userRole);
                loginResponse.setJwt(generateToken);
                loginResponse.setUserName(userByEmail.getUsername());
                loginResponse.setUserId(userByEmail.getUserId());
                loginResponse.setRole(userRole);
                loginResponse.setEmail(userByEmail.getEmail());
                loginResponse.setMessage("Login Success");
                loginResponse.setCode(200);
                return loginResponse;

            }else{
                String generateToken = jwtUtil.generateTokenRoles(userDetails, roles);
                loginResponse.setJwt(generateToken);
                loginResponse.setUserName(userByEmail.getUsername());
                loginResponse.setUserId(userByEmail.getUserId());
                loginResponse.setRole(String.valueOf(roles));
                loginResponse.setEmail(userByEmail.getEmail());
                loginResponse.setMessage("Login Success");
                loginResponse.setCode(200);
                return loginResponse;
            }


        }catch (Exception e){
            throw new BadCredentialsException("invalid details");
        }
    }

    @Override
    public LoginResponse saveUser(userDto dto) {
        User userByEmail = userRepo.getUserByEmail(dto.getEmail());
        if(Objects.equals(userByEmail,null)) {
            User map = modelMapper.map(dto, User.class);
            map.setPassword(BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt(10)));
            User save = userRepo.save(map);

            UserRoles byRole = userRolesRepo.findByRole(dto.getRole());

            LoginResponse loginResponse = new LoginResponse();

            if (!Objects.equals(byRole, null)) {
                userRoleDetailsService.saveUserRoleDetails(save.getUserId(), byRole.getUserRoleId());
                Authentication authenticate =
                        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(save.getEmail(), dto.getPassword()));
                UserDetails userDetails = userDetailsService.loadUserByUsername(save.getEmail());
                String generateToken = jwtUtil.generateToken(userDetails, dto.getRole());



                loginResponse.setMessage("User Created Successfully");
                loginResponse.setJwt(generateToken);
                loginResponse.setUserName(save.getUsername());
                loginResponse.setUserId(save.getUserId());
                loginResponse.setEmail(save.getEmail());
                loginResponse.setRole(dto.getRole());
                loginResponse.setCode(200);
                return loginResponse;
            }
        }
        throw new BadCredentialsException("User Already Exist");
    }

    @Override
    public User updateUser(userDto dto, String type) {
        if (!type.equals("User") && !type.equals("Admin")){
            throw new BadCredentialsException("dont have permission");
        }
        User userById = userRepo.getUserById(dto.getId());
        System.out.println("user="+userById);
        if(!Objects.equals(userById,null)){
            User map = modelMapper.map(dto, User.class);
            map.setStatus("1");
            map.setPassword(userById.getPassword());
            return  userRepo.save(map);

        }
        throw new RuntimeException("user not Exist!");
    }

    @Override
    public User deleteUser(long userId ,String type) {
        if (!type.equals("User") && !type.equals("Admin")){
            throw new BadCredentialsException("dont have permission");
        }
        User userById = userRepo.getUserById(userId);
        if(!Objects.equals(userById,null)){
            userRoleDetailsService.deleteUserRoleDetails(userId);
            userRepo.delete(userById);
            return userById;

        }
        throw new RuntimeException("user id is invalid");
    }

    @Override
    public List<userDto> getAllUser(String type) {
        if (!type.equals("User") && !type.equals("Admin")){
            throw new BadCredentialsException("dont have permission");
        }
        return modelMapper.map(userRepo.getAllUserByUser(),new TypeToken<List<userDto>>() {}.getType());
    }

    @Override
    public int getUserCount(String type) {
        if ( !type.equals("Admin")){
            throw new BadCredentialsException("dont have permission");
        }
        return userRepo.getTotalUser();
    }

    @Override
    public String updateUserPassword(UserPasswordDto dto, String type) {
        if (!type.equals("User") && !type.equals("Admin")){
            throw new BadCredentialsException("dont have permission");

        }
        User userById = userRepo.getUserById(dto.getUserId());
        if(!Objects.equals(userById,null)){
            boolean checkpw = BCrypt.checkpw(dto.getCurrentPassword(), userById.getPassword());
        if(checkpw){
            userById.setPassword(BCrypt.hashpw(dto.getNewPassword(), BCrypt.gensalt(10)));
            return "Password Updated Successfully";
            }
            throw new RuntimeException("user current password is wrong!");
        }
        throw new RuntimeException("user is not exist");
    }

    @Override
    public boolean checkUserContact(long userId, String type) {
        if (!type.equals("User") && !type.equals("Admin")){
            throw new BadCredentialsException("dont have permission");
        }
        User userById = userRepo.getUserById(userId);
        if(userById.getContactNumber().equals("null")
                || userById.getContactNumber().equals("")){
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    public List<userDto> getAllAdmin(String type) {
        if (!type.equals("Admin")){
            throw new BadCredentialsException("dont have permission");
        }
        return modelMapper.map(userRepo.getAllAdmins(),new TypeToken<List<userDto>>() {}.getType());
    }

    @Override
    public User getUserByUserId(long userId, String type) {
        if (!type.equals("User")){
            throw new BadCredentialsException("dont have permission");
        }
        User userById = userRepo.getUserById(userId);
        if (!Objects.equals(userById,null)){
            return userById;
        }
        throw new RuntimeException("user not exit");
    }
}
