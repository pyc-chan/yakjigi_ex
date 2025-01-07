package com.ict.edu.domain.notice.vo;

import java.time.LocalDate;

import lombok.Data;

@Data
public class NoticeVO {
    private String notice_idx = "";
    private String admin_idx = "";
    private String notice_title = "";
    private String notice_content = null;
    private LocalDate notice_reg_date = LocalDate.of(2000, 1, 1);
    private String notice_file = null;
    private String notice_file_name = null;
    private int notice_delete = 0;
    private LocalDate notice_out_date = null;
}
