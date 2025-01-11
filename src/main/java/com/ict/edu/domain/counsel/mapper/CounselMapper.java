package com.ict.edu.domain.counsel.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ict.edu.domain.counsel.vo.CounselVO;

@Mapper
public interface CounselMapper {
    // 상담 리스트
    public List<CounselVO> getCounselList();
    
    // 상담 디테일
    public CounselVO getCounselDetail(String counsel_idx);
    
    // 상담 작성
    public int postCounselJoin(CounselVO counvo);
    
    // 상담 수정
    public int putCounselUpdate(CounselVO counvo);
    
    // 상담 삭제(실제로는 update)
    public int putCounselDelete(CounselVO counvo);
    
    // 상담 응답 작성
    public int putCounselCommentJoin(CounselVO counvo);
}