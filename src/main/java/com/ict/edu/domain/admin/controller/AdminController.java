package com.ict.edu.domain.admin.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ict.edu.domain.admin.service.AdminService;
import com.ict.edu.domain.auth.vo.UserVO;
import com.ict.edu.domain.user.service.UserService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;


@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private AdminService adminService;
    
    @Autowired
    private UserService userService;
    
    
    @GetMapping("/userlist")
    public List<UserVO> getUserList() {
        List<UserVO> list = adminService.getUserList();
        return list;
    }
    
}
