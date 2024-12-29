package com.ict.edu.domain.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ict.edu.domain.user.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    
    @GetMapping("/detail")
    public String getUserDetail(String user_idx) {
        return new String();
    }
    
    
    
}
