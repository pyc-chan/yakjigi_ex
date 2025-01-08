package com.ict.edu.domain.notice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.ict.edu.domain.auth.vo.DataVO;
import com.ict.edu.domain.notice.service.NoticeService;
import com.ict.edu.domain.notice.vo.NoticeVO;

@RestController
public class NoticeController {
    @Autowired
    private NoticeService noticeService;
    
    // 공지 리스트
    public DataVO getNoticeList(){
        DataVO dvo = new DataVO();
        
    }
    
    // 공지 디테일
    NoticeVO getNoticeDetail(String notice_idx);
    
    // 공지 작성
    int postNoticeJoin(NoticeVO nvo);
    
    // 공지 삭제(실제로는 update)
    int putNoticeDelete(NoticeVO nvo);
}
