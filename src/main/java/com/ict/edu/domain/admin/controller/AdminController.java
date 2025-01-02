package com.ict.edu.domain.admin.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ict.edu.domain.admin.service.AdminService;
import com.ict.edu.domain.auth.vo.UserVO;
import com.ict.edu.domain.qna.service.QnaService;
import com.ict.edu.domain.qna.vo.QnaVO;
import com.ict.edu.domain.user.service.UserService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private AdminService adminService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private QnaService qnaService;
    
    @GetMapping("/user_list")
    public List<UserVO> getUserList() {
        List<UserVO> list = adminService.getUserList();
        return list;
    }
    
    @GetMapping("/qna_list")
    public List<QnaVO> getQnaList() {
        List<QnaVO> list = qnaService.getQnaList();
        return list;
    }
    
    @GetMapping("/qna_no_answer")
    public List<QnaVO> getNoAnswerList() {
        List<QnaVO> list = qnaService.getNoAnswerList();
        return list;
    }
    
}
