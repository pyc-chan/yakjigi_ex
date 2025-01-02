package com.ict.edu.domain.qna.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ict.edu.domain.qna.vo.QnaVO;

@Mapper
public interface QnaMapper {
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
}
