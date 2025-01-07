package com.ict.edu.domain.fna.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ict.edu.domain.fna.vo.FnaVO;

@Mapper
public interface FnaMapper {
    // 자주 묻는 질문 리스트
    List<FnaVO> getFnaList();
    
    // 자주 묻는 질문 작성
    int postFnaJoin(FnaVO fvo);
    
    // 자주 묻는 질문 수정
    int putFnaUpdate(FnaVO fvo);
    
    // 자주 묻는 질문 삭제(실제로는 update)
    int putFnaDelete(String fna_idx);
}
