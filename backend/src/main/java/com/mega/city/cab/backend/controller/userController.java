package com.mega.city.cab.backend.controller;



import com.mega.city.cab.backend.dto.AuthenticationRequestDTO;
import com.mega.city.cab.backend.dto.userDto;
import com.mega.city.cab.backend.entity.User;
import com.mega.city.cab.backend.service.userService;
import com.mega.city.cab.backend.util.response.LoginResponse;
import com.mega.city.cab.backend.util.response.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class userController {

    @Autowired
    userService userService;

    @PostMapping(path = "/login")
    public ResponseEntity<LoginResponse> userLogin(@RequestBody AuthenticationRequestDTO dto){
        LoginResponse response = userService.logUser(dto);
        return new ResponseEntity<LoginResponse>(
                response,
                HttpStatus.OK
        );
    }
    @PostMapping(path = "/register")
    public ResponseEntity<?> saveUser(@RequestBody userDto dto){
        LoginResponse response = userService.saveUser(dto);
        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        );
    }
    @PutMapping(path = "/update")
    public ResponseEntity<StandardResponse> updateUser(@RequestBody userDto dto,@RequestAttribute String type){
        User user = userService.updateUser(dto, type);
        return new ResponseEntity<>(
                new StandardResponse(200,"User Updated",user),
                HttpStatus.OK
        );
    }

    @DeleteMapping(params = {"userId"})
    public ResponseEntity<StandardResponse> deleteUser(@RequestParam Long userId,
                                                       @RequestAttribute String type){
        User user = userService.deleteUser(userId,type);
        return new ResponseEntity<>(
                new StandardResponse(200,"User Deleted",user),
                HttpStatus.OK
        );
    }

}