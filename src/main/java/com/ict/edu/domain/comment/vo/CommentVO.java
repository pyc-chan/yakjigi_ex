package com.ict.edu.domain.comment.vo;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Data;

@Data
public class CommentVO {
    private String user_idx = null;
    private String admin_idx = null;
    private String comment_content = "";
    private LocalDate comment_reg_date = LocalDate.now();
    private int comment_update;
    private int comment_delete;
    // private comment_file; mediumblob String 결정나면 수정
    private String comment_file_name;
    private String notice_idx;
    private String qna_idx;
    private CommentBoard comment_board;
    
    
    public enum CommentBoard{
        NOTICE("Notice"),
        QNA("Qna");
        
        private String board;
        
        CommentBoard(String board){
            this.board = board;
        }
        
        @JsonValue
        public String getBoard(){
            return board;
        }
        
    }
}
