package com.ict.edu.domain.fna.service;

import java.util.List;

import com.ict.edu.domain.fna.vo.FnaVO;

public interface FnaService {
    // 공지 리스트
    List<FnaVO> getFnaList();
    
    // 공지 디테일
    FnaVO getFnaDetail(String Fna_idx);
    
    // 공지 작성
    int postFnaJoin(FnaVO fvo);
    
    // 공지 삭제(실제로는 update)
    int putFnaDelete(FnaVO fvo);
}
