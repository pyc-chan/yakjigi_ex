package com.ict.edu.domain.comment.vo;

import java.time.LocalDate;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Data;

@Data
public class CommentVO {
    private MultipartFile file = null;
    
    private String comment_idx = "";
    private String user_idx = null;
    private String admin_idx = null;
    private String comment_content = null;
    private LocalDate comment_reg_date = LocalDate.now();
    private int comment_update = 0;
    private int comment_delete = 0;
    private String comment_file = null; 
    private String notice_idx = null;
    private String qna_idx = null;
    private Comment_board comment_board;
    private String comment_file_name = null;
    
    public enum Comment_board{
        NOTICE("Notice"),
        QNA("Qna");
        
        private String board;
        
        Comment_board(String board){
            this.board = board;
        }
        
        @JsonValue
        public String getBoard(){
            return board;
        }
        
    }
}
