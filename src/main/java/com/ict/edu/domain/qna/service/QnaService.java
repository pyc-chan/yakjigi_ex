package com.ict.edu.domain.qna.service;

import java.util.List;

import com.ict.edu.domain.qna.vo.QnaVO;

public interface QnaService {
    // 문의 리스트
    List<QnaVO> getQnaList();
    
    // 문의 디테일
    QnaVO getQnaDetail(String Qna_idx);
    
    // 문의 작성
    int postQnaJoin(QnaVO qvo);
    
    // 문의 수정
    int putQnaUpdate(QnaVO qvo);
    
    // 문의 삭제(실제로는 update)
    int putQnaDelete(QnaVO qvo);
}
