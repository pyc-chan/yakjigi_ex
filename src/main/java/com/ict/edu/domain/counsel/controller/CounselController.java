package com.ict.edu.domain.counsel.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ict.edu.common.util.UserInfoService;
import com.ict.edu.domain.auth.vo.DataVO;
import com.ict.edu.domain.counsel.service.CounselService;
import com.ict.edu.domain.counsel.vo.CounselVO;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/counsel") // 기본 매핑 경로 설정
public class CounselController {

    @Autowired
    private CounselService counselService;
    
    
    // 상담 리스트
    @GetMapping("/list")
    public DataVO getCounselList() {
        DataVO dvo = new DataVO();
        Map <String, Object> result = new HashMap<>();
        List<CounselVO> list = counselService.getCounselList();
        result.put("list", list);
        result.put("size", list.size());
        dvo.setData(result);
        dvo.setSuccess(true);
        dvo.setMessage(null);
        return dvo;
    }

    // 상담 디테일
    @GetMapping("/detail")
    public DataVO getCounselDetail(@RequestParam String counsel_idx) {
        DataVO dvo = new DataVO();
        CounselVO counvo = counselService.getCounselDetail(counsel_idx);
        UserInfoService userInfoService = new UserInfoService();
        counvo.setUser_nickname(userInfoService.getUserNickName(counvo.getUser_idx()));
        counvo.setUser_response_nickname(userInfoService.getUserNickName(counvo.getUser_response_idx()));
        dvo.setData(counvo);
        dvo.setSuccess(true);
        dvo.setMessage(null);
        return dvo;
    }

    // 상담 작성
    @PostMapping("/create")
    public DataVO postCounselJoin(@RequestBody CounselVO counvo) {
        DataVO dvo = new DataVO();
        if(counselService.postCounselJoin(counvo)>0){
            dvo.setData(counvo);
            dvo.setSuccess(true);
            dvo.setMessage(null);
        }else{
            dvo.setSuccess(false);
            dvo.setMessage("");
        }
        return dvo;
    }

    // 상담 수정
    @PutMapping("/update")
    public DataVO putCounselUpdate(@RequestBody CounselVO counvo) {
        DataVO dvo = new DataVO();
        if(counselService.putCounselUpdate(counvo)>0){
            dvo.setData(counvo);
            dvo.setSuccess(true);
            dvo.setMessage(null);
        }else{
            dvo.setSuccess(false);
            dvo.setMessage("");
        }
        return dvo;
    }

    // 상담 삭제
    @PutMapping("/delete")
    public DataVO putCounselDelete(@RequestBody CounselVO counvo) {
        DataVO dvo = new DataVO();
        if(counselService.putCounselDelete(counvo)>0){
            dvo.setData(counvo);
            dvo.setSuccess(true);
            dvo.setMessage(null);
        }else{
            dvo.setSuccess(false);
            dvo.setMessage("");
        }
        return dvo;
    }

    // 상담 응답 작성
    @PutMapping("/comment")
    public DataVO putCounselCommentJoin(@RequestBody CounselVO counvo) {
        DataVO dvo = new DataVO();
        if(counselService.putCounselCommentJoin(counvo)>0){
            dvo.setData(counvo);
            dvo.setSuccess(true);
            dvo.setMessage(null);
        }else{
            dvo.setSuccess(false);
            dvo.setMessage("");
        }
        return dvo;
    }
    
    
}
