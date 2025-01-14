package com.ict.edu.domain.notice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ict.edu.domain.auth.vo.DataVO;
import com.ict.edu.domain.notice.service.NoticeService;
import com.ict.edu.domain.notice.vo.NoticeVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/noticetbl")
public class NoticeController {
   @Autowired
   private NoticeService noticeTblService;

   @GetMapping("/list")
   // 공지 리스트
   public DataVO getNoticeList(){
      DataVO dataVO = new DataVO();
   
      try {
         log.info("Controller: getNoticeList 호출");
         List<NoticeVO> list = noticeTblService.getNoticeList();
         log.info("Controller: list : " + list);
         dataVO.setSuccess(true);
         dataVO.setMessage("기록 조회 성공");
         dataVO.setData(list);
      } catch (Exception e) {
         dataVO.setSuccess(false);
         dataVO.setMessage("기록 조회 실패: " + e.getMessage());
         dataVO.setData(null);
         e.printStackTrace();
      }
   
   
      return dataVO;
   }

   

   @GetMapping("/detail/{notice_idx}")
   public DataVO getNoticeDetail(@PathVariable("notice_idx") String notice_idx) {
      DataVO dataVO = new DataVO();
      try {
         log.info("notice_idx : " + notice_idx);
         NoticeVO ntvo = noticeTblService.getNoticeDetail(notice_idx);
         if (ntvo == null) {
            dataVO.setSuccess(false);
            dataVO.setMessage("상세보기 실패");
            return dataVO;
         }
         dataVO.setSuccess(true);
         dataVO.setMessage("상세보기 성공");
         dataVO.setData(ntvo);
      } catch (Exception e) {
         dataVO.setSuccess(false);
         dataVO.setMessage("상세보기 실패");
      }
      return dataVO;
   }
}
