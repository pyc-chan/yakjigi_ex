package com.ict.edu.domain.notice.service;

import java.util.List;

import com.ict.edu.domain.notice.vo.NoticeVO;


public interface NoticeService {
   // 공지 리스트
   List<NoticeVO> getNoticeList();
   
   // 공지 디테일
   NoticeVO getNoticeDetail(String notice_idx);
   
   // 공지 작성
   int postNoticeJoin(NoticeVO nvo);
   
   // 공지 삭제(실제로는 update)
   int putNoticeDelete(NoticeVO nvo);
   
   // 공지는 comment 끌고와야함!
}
