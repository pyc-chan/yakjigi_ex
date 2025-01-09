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
    
    // 답변 안된 리스트
    List<QnaVO> getNoAnswerList();
    
    // 내가 작성한 리스트
    List<QnaVO> getUserQnaList(String user_idx);
}
