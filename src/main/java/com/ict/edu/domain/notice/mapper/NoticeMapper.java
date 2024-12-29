package com.ict.edu.domain.notice.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ict.edu.domain.notice.vo.NoticeVO;

@Mapper
public interface NoticeMapper {
    // 공지 리스트
    List<NoticeVO> getNoticeList();
    
    // 공지 디테일
    NoticeVO getNoticeDetail(String Notice_idx);
    
    // 공지 작성
    int postNoticeJoin(NoticeVO nvo);
    
    // 공지 삭제(실제로는 update)
    int putNoticeDelete(NoticeVO nvo);
    
    
}
