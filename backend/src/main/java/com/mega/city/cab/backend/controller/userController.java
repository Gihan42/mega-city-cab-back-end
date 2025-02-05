package com.mega.city.cab.backend.controller;



import com.mega.city.cab.backend.dto.AuthenticationRequestDTO;
import com.mega.city.cab.backend.dto.UserPasswordDto;
import com.mega.city.cab.backend.dto.userDto;
import com.mega.city.cab.backend.entity.User;
import com.mega.city.cab.backend.service.userService;
import com.mega.city.cab.backend.util.response.LoginResponse;
import com.mega.city.cab.backend.util.response.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class userController {

    @Autowired
    userService userService;

//    user or admin login
    @PostMapping(path = "/login")
    public ResponseEntity<LoginResponse> userLogin(@RequestBody AuthenticationRequestDTO dto){
        LoginResponse response = userService.logUser(dto);
        return new ResponseEntity<LoginResponse>(
                response,
                HttpStatus.OK
        );
    }

//    user register
    @PostMapping(path = "/register")
    public ResponseEntity<?> saveUser(@RequestBody userDto dto){
        LoginResponse response = userService.saveUser(dto);
        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        );
    }

//    update user
    @PutMapping(path = "/update")
    public ResponseEntity<StandardResponse> updateUser(@RequestBody userDto dto,
                                                       @RequestAttribute String type){
        User user = userService.updateUser(dto, type);
        return new ResponseEntity<>(
                new StandardResponse(200,"User Updated",user),
                HttpStatus.OK
        );
    }


//    delete user
    @DeleteMapping(params = {"userId"})
    public ResponseEntity<StandardResponse> deleteUser(@RequestParam Long userId,
                                                       @RequestAttribute String type){
        User user = userService.deleteUser(userId,type);
        return new ResponseEntity<>(
                new StandardResponse(200,"User Deleted",user),
                HttpStatus.OK
        );
    }

//    get all users
    @GetMapping(path = "/allUsers")
    public ResponseEntity<StandardResponse> getAllUser(@RequestAttribute String type){
        List<userDto> allUser = userService.getAllUser(type);
        return new ResponseEntity<>(
                new StandardResponse(200,"all users",allUser),
                HttpStatus.OK
        );

    }

//    user count
    @GetMapping(path = "/count")
    public  ResponseEntity<StandardResponse> countUser(@RequestAttribute String type){
        int count = userService.getUserCount(type);
        return new ResponseEntity<>(
                new StandardResponse(200,"User Count",count),
                HttpStatus.OK
        );
    }

//    change password
    @PutMapping(path = "/updateUserPassword")
    public ResponseEntity<StandardResponse> updateUserPassword(@RequestBody UserPasswordDto dto,
                                                               @RequestAttribute String type){
        String response = userService.updateUserPassword(dto, type);
        return new ResponseEntity<>(
                new StandardResponse(200,"Password Updated",response),
                HttpStatus.OK
        );

    }

//    check user contact
    @GetMapping(params = {"userId"})
    public ResponseEntity<StandardResponse> updateUserPassword(@RequestParam long userId,
                                                               @RequestAttribute String type){
        boolean response = userService.checkUserContact(userId, type);
        return new ResponseEntity<>(
                new StandardResponse(200,"response is",response),
                HttpStatus.OK
        );
    }

}