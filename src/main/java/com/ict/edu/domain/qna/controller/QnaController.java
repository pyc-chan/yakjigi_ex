package com.ict.edu.domain.qna.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ict.edu.domain.auth.vo.DataVO;
import com.ict.edu.domain.comment.service.CommentService;
import com.ict.edu.domain.comment.vo.CommentVO;
import com.ict.edu.domain.comment.vo.CommentVO.Comment_board;
import com.ict.edu.domain.qna.service.QnaService;
import com.ict.edu.domain.qna.vo.QnaVO;



@RestController
@RequestMapping("/qna")
public class QnaController {
    
    @Autowired
    private QnaService qnaService;
    
    @Autowired
    private CommentService commentService;
    
    // 문의 리스트
    @GetMapping("/list")
    public List<QnaVO> getQnaList(){
        List<QnaVO> list = qnaService.getQnaList();
        return list;
    }
    
    // 문의 디테일
    @GetMapping("/detail")
    public DataVO getQnaDetail(String Qna_idx){
        DataVO dvo = new DataVO();
        QnaVO qvo = qnaService.getQnaDetail(Qna_idx);
        Map<String, Object> map = new HashMap<>();
        if(qvo.getQna_answer_stat()>0){
            List<CommentVO> list = commentService.getCommentList(Comment_board.QNA, Qna_idx);
            map.put("list",list);
        }
        map.put("qvo", qvo);
        dvo.setData(map);
        return dvo;
    }
    
    
}
