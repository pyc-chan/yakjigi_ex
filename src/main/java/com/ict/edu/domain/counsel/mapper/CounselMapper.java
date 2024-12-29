package com.ict.edu.domain.counsel.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ict.edu.domain.counsel.vo.CounselVO;

@Mapper
public interface CounselMapper {
    // 상담 리스트
    List<CounselVO> getCounselList();
    
    // 상담 디테일
    CounselVO getCounselDetail(String counsel_idx);
    
    // 상담 작성
    int postCounselJoin(CounselVO counvo);
    
    // 상담 수정
    int putCounselUpdate(CounselVO counvo);
    
    // 상담 삭제(실제로는 update)
    int putCounselDelete(CounselVO counvo);
    
    // 상담 응답 작성
    int putCounselCommentJoin(CounselVO counvo);
}
    