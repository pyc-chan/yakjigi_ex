package com.ict.edu.domain.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.ict.edu.domain.auth.vo.DataVO;
import com.ict.edu.domain.auth.vo.UserVO;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.io.UnsupportedEncodingException;

@Service
public class EmailService {
    
    @Value("${spring.mail.username}")
    private String emailUsername;

    @Value("${spring.mail.password}")
    private String emailPassword;

    @Value("${spring.mail.host}")
    private String emailHost;

    @Value("${spring.mail.port}")
    private int emailPort;
    
    @Autowired
    private JavaMailSender javaMailSender;
    
    public DataVO sendEmail(UserVO uvo, String randomnumber){
        DataVO dvo = new DataVO();
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setFrom(emailUsername, "YAKJIGI");  // 보내는 사람의 이메일과 이름
            helper.setTo(uvo.getUser_email());
            helper.setSubject("YAKJIGI 인증번호 입니다.");  // 이메일 제목
            helper.setText("<table><tbody>"
            +"<tr><td><h2>메일 인증 </h2></td></tr>"
            +"<tr><td><h3>Green Spot Finder</h3></td></tr>"
            +"<tr><td><font size='8px'>인증 번호 안내</font></td></tr>"
            +"<tr><td><font size='10px'>확인번호 : "+randomnumber+"</font></td></tr>"
            +"</tbody></table>");  // 이메일 본문 (HTML 형식)
            javaMailSender.send(message);  // 이메일 전송
            dvo.setSuccess(true);
            dvo.setMessage("메일이 성공적으로 발송되었습니다.");
        } catch (Exception e) {
            dvo.setSuccess(false);
            dvo.setMessage("메일 발송에 실패하였습니다.");
            System.out.println("메일 발송 실패");
        }
        return dvo;
    }
}
