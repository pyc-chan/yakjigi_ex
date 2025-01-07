package com.ict.edu.domain.qna.vo;

import java.time.LocalDate;

import lombok.Data;

@Data
public class QnaVO {
    private String qna_idx = "";
    private String user_idx = "";
    private String user_level_idx = "";
    private String qna_title = "";
    private String qna_question = null;
    private LocalDate qna_q_reg_date = LocalDate.of(2000, 1, 1);
    private String admin_idx = null;
    private int qna_answer_stat = 0;
}
