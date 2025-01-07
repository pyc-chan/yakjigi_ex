package com.ict.edu.domain.auth.vo;

import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// getter, setter, toString(), equals(), hashCode() 메서드 자동생성
@Data
// 모든 필드를 매개변수로 받는 생성자를 생성한다.
@AllArgsConstructor
// 기본 생성자를 자동으로 생성한다.
@NoArgsConstructor
public class DataVO {
    
    private boolean success = false;
    private Object data = null;
    private String token = null;
    private String message = null;
    private UserDetails userDetails;
}
