package com.ict.edu.domain.comment.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ict.edu.domain.comment.vo.CommentVO;

@Mapper
public interface CommentMapper {
    // 댓글 리스트(게시판 종류, 게시판 idx)
    // 게시판 종류는 Enum형임!
    List<CommentVO> getCommentList(Map<String, String> map);
    
    // 댓글 작성
    int postCommentJoin(CommentVO cvo);
    
    // 댓글 수정
    int putCommentUpdate(CommentVO cvo);
    
    // 댓글 삭제(실제로는 update)
    int putCommentDelete(CommentVO cvo);
    
    // 댓글 데이터 확인
    CommentVO getCommentDetail(String comment_idx);
}