package com.ict.edu.domain.fna.service;

import java.util.List;

import com.ict.edu.domain.fna.vo.FnaVO;

public interface FnaService {
    // 자주 묻는 질문 리스트
    List<FnaVO> getFnaList();
    
    // 자주 묻는 질문 작성
    int postFnaJoin(FnaVO fvo);
    
    // 자주 묻는 질문 수정
    int putFnaUpdate(FnaVO fvo);
    
    // 자주 묻는 질문 삭제(실제로는 update)
    int putFnaDelete(FnaVO fvo);
}
