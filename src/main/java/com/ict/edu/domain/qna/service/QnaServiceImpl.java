package com.ict.edu.domain.qna.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ict.edu.domain.qna.mapper.QnaMapper;
import com.ict.edu.domain.qna.vo.QnaVO;

public class QnaServiceImpl implements QnaService{
    
    @Autowired
    private QnaMapper qnaMapper;
    
    @Override
    public List<QnaVO> getQnaList() {
        return qnaMapper.getQnaList();
    }

    @Override
    public QnaVO getQnaDetail(String Qna_idx) {
        return qnaMapper.getQnaDetail(Qna_idx);
    }

    @Override
    public int postQnaJoin(QnaVO qvo) {
        return qnaMapper.postQnaJoin(qvo);
    }

    @Override
    public int putQnaUpdate(QnaVO qvo) {
        return qnaMapper.putQnaUpdate(qvo);
    }

    @Override
    public int putQnaDelete(QnaVO qvo) {
        return qnaMapper.putQnaDelete(qvo);
    }

    @Override
    public List<QnaVO> getNoAnswerList() {
        return qnaMapper.getNoAnswerList();
    }
    
}
