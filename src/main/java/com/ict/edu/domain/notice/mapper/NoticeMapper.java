package com.ict.edu.domain.notice.mapper;

import java.util.List;


import org.apache.ibatis.annotations.Mapper;

import com.ict.edu.domain.notice.vo.NoticeVO;

@Mapper
public interface NoticeMapper {
   // 공지 리스트
   List<NoticeVO> getNoticeList();
   NoticeVO getNoticeDetail(String notice_idx);
}
