package com.ict.edu.domain.notice.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.edu.domain.notice.mapper.NoticeMapper;
import com.ict.edu.domain.notice.vo.NoticeVO;


@Service
public class NoticeServiceImpl implements NoticeService {
   @Autowired
   private NoticeMapper noticeMapper;

   @Override
   public List<NoticeVO> getNoticeList() {
      return noticeMapper.getNoticeList();
   }

   @Override
   public NoticeVO getNoticeDetail(String notice_idx) {
      return noticeMapper.getNoticeDetail(notice_idx);
   }

   @Override
   public int postNoticeJoin(NoticeVO nvo) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'postNoticeJoin'");
   }

   @Override
   public int putNoticeDelete(NoticeVO nvo) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'putNoticeDelete'");
   }

}
