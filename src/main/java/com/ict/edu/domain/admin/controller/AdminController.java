package com.ict.edu.domain.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ict.edu.domain.admin.service.AdminService;
import com.ict.edu.domain.auth.vo.DataVO;
import com.ict.edu.domain.auth.vo.UserVO;
import com.ict.edu.domain.fna.service.FnaService;
import com.ict.edu.domain.fna.vo.FnaVO;
import com.ict.edu.domain.qna.service.QnaService;
import com.ict.edu.domain.qna.vo.QnaVO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;



@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private FnaService fnaService;
    
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
    
    // 답변 작성
    @PutMapping("/qna_answer")
    public DataVO putQnaUpdate(QnaVO qvo){
        DataVO dvo = new DataVO();
        qvo.setQna_answer_stat(1);
        if(qnaService.putQnaUpdate(qvo)>0){
            dvo.setMessage("답변 완료");
            dvo.setSuccess(true);
        }else{
            dvo.setMessage("답변 실패");
            dvo.setSuccess(false);
            dvo.setData(qvo);
        }
        return dvo;
    }
    
    // 전문가 회원 미등록 목록
    @GetMapping("/user_pen_list")
    public List<UserVO> getProPenUser(){
        List<UserVO> list = adminService.getProPenUser("2");
        return list;
    }
    
    // fna 작성
    @PostMapping("/fna_join")
    public DataVO postFnaJoin(FnaVO fvo){
        DataVO dvo = new DataVO();
        if(fnaService.postFnaJoin(fvo)>0){
            dvo.setMessage("작성 성공");
            dvo.setSuccess(true);
        }else{
            dvo.setSuccess(false);
            dvo.setMessage("작성 실패");
        }
        return dvo;
    }
    
    // fna 수정
    @PutMapping("/fna_update")
    public DataVO putFnaUpdate(FnaVO fvo){
        DataVO dvo = new DataVO();
        if(fnaService.putFnaUpdate(fvo)>0){
            dvo.setSuccess(true);
            dvo.setMessage("수정 성공");
        }else{
            dvo.setSuccess(false);
            dvo.setMessage("수정 실패");
        }
        return dvo;
    }
    
    // fna 삭제
    @PutMapping("/fna_delete")
    public DataVO putFnaDelete(FnaVO fvo){
        DataVO dvo = new DataVO();
        if(fnaService.putFnaDelete(fvo)>0){
            dvo.setSuccess(true);
            dvo.setMessage("삭제 성공");
        }else{
            dvo.setSuccess(false);
            dvo.setMessage("삭제 실패");
        }
        return dvo;
    }
}
