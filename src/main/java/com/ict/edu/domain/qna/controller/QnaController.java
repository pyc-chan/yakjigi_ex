package com.ict.edu.domain.qna.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ict.edu.domain.auth.vo.DataVO;
import com.ict.edu.domain.qna.service.QnaService;
import com.ict.edu.domain.qna.vo.QnaVO;



@RestController
@RequestMapping("/qna")
public class QnaController {
    
    @Autowired
    private QnaService qnaService;
    
    
    // 문의 리스트
    @GetMapping("/list")
    public List<QnaVO> getQnaList(){
        List<QnaVO> list = qnaService.getQnaList();
        return list;
    }
    
    // 문의 디테일
    @GetMapping("/detail")
    public QnaVO getQnaDetail(String Qna_idx){
        QnaVO qvo = qnaService.getQnaDetail(Qna_idx);
        return qvo;
    }
    
    // 문의 작성
    @PostMapping("/insert")
    public DataVO postQnaJoin(QnaVO qvo){
        DataVO dvo = new DataVO();
        if(qnaService.postQnaJoin(qvo)>0){
            dvo.setMessage("작성 성공");
            dvo.setSuccess(true);
        }else{
            dvo.setMessage("작성 실패");
            dvo.setSuccess(false);
            dvo.setData(qvo);
        }
        return dvo;
    }
    
    // 답변 작성
    @PutMapping("/answer")
    public DataVO putQnaUpdate(QnaVO qvo){
        DataVO dvo = new DataVO();
        if(qnaService.putQnaUpdate(qvo)>0){
            dvo.setMessage("답변 완료");
            dvo.setSuccess(true);
        }else{
            dvo.setMessage("답변 실패");
            dvo.setSuccess(false);
            dvo.setData(qvo);
        }
        return dvo;
    }
    
    // 답변 안된 리스트
    @GetMapping("/no_answer")
    public List<QnaVO> getNoAnswerList(){
        List<QnaVO> list = qnaService.getNoAnswerList();
        return list;
    }
}
