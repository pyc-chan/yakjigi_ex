package com.ict.edu.domain.counsel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public List<CounselVO> getCounselList() {
        return counselService.getCounselList();
    }

    // 상담 디테일
    @GetMapping("/detail")
    public CounselVO getCounselDetail(@RequestParam String counsel_idx) {
        return counselService.getCounselDetail(counsel_idx);
    }

    // 상담 작성
    @PostMapping("/create")
    public int postCounselJoin(@RequestBody CounselVO counvo) {
        return counselService.postCounselJoin(counvo);
    }

    // 상담 수정
    @PutMapping("/update")
    public int putCounselUpdate(@RequestBody CounselVO counvo) {
        return counselService.putCounselUpdate(counvo);
    }

    // 상담 삭제
    @PutMapping("/delete")
    public int putCounselDelete(@RequestBody CounselVO counvo) {
        return counselService.putCounselDelete(counvo);
    }

    // 상담 응답 작성
    @PutMapping("/comment")
    public int putCounselCommentJoin(@RequestBody CounselVO counvo) {
        return counselService.putCounselCommentJoin(counvo);
    }
    
    
}
