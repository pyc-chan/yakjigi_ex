package com.ict.edu.domain.notice.vo;

import java.time.LocalDate;

import lombok.Data;

@Data
public class NoticeVO {
    private String notice_idx;
    private String admin_idx;
    private String notice_title;
    private String notice_content;
    private LocalDate notice_reg_date;
    private String notice_file;
    private String notice_file_name;
    private int notice_delete;
    private LocalDate notice_out_date;
}
