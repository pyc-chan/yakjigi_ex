package com.ict.edu.domain.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ict.edu.domain.admin.service.AdminService;
import com.ict.edu.domain.auth.vo.UserVO;
import com.ict.edu.domain.qna.service.QnaService;
import com.ict.edu.domain.qna.vo.QnaVO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;



@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private AdminService adminService;
    
    @Autowired
    private QnaService qnaService;
    
    // 유저 목록
    @GetMapping("/user_list")
    public List<UserVO> getUserList() {
        List<UserVO> list = adminService.getUserList();
        return list;
    }
    
    // 문의 목록
    @GetMapping("/qna_list")
    public List<QnaVO> getQnaList() {
        List<QnaVO> list = qnaService.getQnaList();
        return list;
    }
    
    // 문의 답변 없는 목록
    @GetMapping("/qna_no_answer")
    public List<QnaVO> getNoAnswerList() {
        List<QnaVO> list = qnaService.getNoAnswerList();
        return list;
    }
    
    // 전문가 회원 미등록 목록
    @GetMapping("/user_pen_list")
    public List<UserVO> getProPenUser(){
        List<UserVO> list = adminService.getProPenUser("전문가 대기idx");
        return list;
    }
}
