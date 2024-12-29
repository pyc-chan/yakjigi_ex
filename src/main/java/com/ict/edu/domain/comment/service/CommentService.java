package com.ict.edu.domain.comment.service;

import java.util.List;

import com.ict.edu.domain.comment.vo.CommentVO;

public interface CommentService {
    // 댓글 리스트(게시판 종류, 게시판 idx)
    // 게시판 종류는 Enum형임!
    List<CommentVO> getCommentList(CommentVO.CommentBoard comment_board, String idx);
    
    // 댓글 작성
    int postCommentJoin(CommentVO comvo);
    
    // 댓글 수정
    int putCommentUpdate(CommentVO comvo);
    
    // 댓글 삭제(실제로는 update)
    int putCommentDelete(CommentVO comvo);
    
    
}
