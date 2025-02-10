package com.mega.city.cab.backend.controller;

import com.mega.city.cab.backend.entity.custom.CommentCustomResult;
import com.mega.city.cab.backend.service.CategoryService;
import com.mega.city.cab.backend.service.CommentService;
import com.mega.city.cab.backend.util.response.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    //get all categories
    //    get all comments with user
    @GetMapping(path = "/allCategories")
    public ResponseEntity<StandardResponse> getAllCategories(@RequestAttribute String type){
        List<String> allCategory = categoryService.getAllCategory(type);
        return new ResponseEntity<>(
                new StandardResponse(200,"all categories",allCategory),
                HttpStatus.OK
        );
    }
}
