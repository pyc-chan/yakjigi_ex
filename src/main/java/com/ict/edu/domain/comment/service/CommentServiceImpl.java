package com.ict.edu.domain.comment.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.edu.domain.comment.mapper.CommentMapper;
import com.ict.edu.domain.comment.vo.CommentVO;
import com.ict.edu.domain.comment.vo.CommentVO.CommentBoard;

@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    private CommentMapper commentMapper;
    
    @Override
    public List<CommentVO> getCommentList(CommentBoard comment_board, String idx) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("comment_board", comment_board.getBoard());
        if(comment_board.getBoard() == "Notice"){
            map.put("notice_idx", idx);
        }else if(comment_board.getBoard() == "Qna"){
            map.put("qna_idx", idx);
        }
        return commentMapper.getCommentList(map);
    }

    @Override
    public int postCommentJoin(CommentVO comvo) {
        return commentMapper.postCommentJoin(comvo);
    }

    @Override
    public int putCommentUpdate(CommentVO comvo) {
        return commentMapper.putCommentUpdate(comvo);
    }

    @Override
    public int putCommentDelete(CommentVO comvo) {
        return commentMapper.putCommentDelete(comvo);
    }
    
    
}
