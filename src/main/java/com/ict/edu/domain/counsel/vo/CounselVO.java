package com.ict.edu.domain.counsel.vo;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CounselVO {
    private int counsel_idx; // 상담 고유 인덱스
    private int user_idx; // 사용자 고유 인덱스
    private String question_title = ""; // 상담 질문 제목
    private String question_content = ""; // 상담 질문 내용
    private LocalDate question_date; // 상담 질문 등록일
    private int counsel_open = 0; // 상담 진행 상황 (0: 미완료, 1: 완료)
    private String response_content = ""; // 상담 답변 내용
    private LocalDate response_date; // 상담 답변 등록일
    private LocalDate counsel_out_date; // 상담 답변 삭제일
    private int counsel_delete = 0; // 상담 삭제 여부 (0: 활성, 1: 삭제)
}

