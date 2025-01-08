package com.ict.edu.domain.auth.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.ict.edu.domain.auth.mapper.AuthMapper;
import com.ict.edu.domain.auth.vo.DataVO;
import com.ict.edu.domain.auth.vo.UserBanVO;
import com.ict.edu.domain.auth.vo.UserVO;


@Service
public class UserDetailService implements UserDetailsService{
    @Autowired
    private AuthMapper authMapper;
    
    // 아이디로 찾기
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserVO uvo = authMapper.getUserDetail(username);
        if (uvo == null) {
            throw new UsernameNotFoundException("없는 아이디 입니다.");
        }
        return new User(uvo.getUser_id(), uvo.getUser_pw(), new ArrayList<>());
    }
    
    // DB에서 아이디로 정보 추출
    public UserVO getUserDetail(String user_id) {
        return authMapper.getUserDetail(user_id);
    }
    
    
    // sns유저
    public UserDetails loadUserByOAuth2User(OAuth2User oAuth2User, String provider) {
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        // kakao Long 이고 바로 String으로 변경안됨
        String id = "";
        UserVO uvo = new UserVO();
        if (provider.equals("kakao")) {
            Long kakaoId = oAuth2User.getAttribute("id");
            id = String.valueOf(kakaoId);
            uvo.setUser_kakao(id);
            uvo.setUser_nickname(name);
            uvo.setUser_id("kakao_"+id);
            uvo.setProvider(provider);
            uvo.setUser_email(email);
        } else if (provider.equals("naver")) {
            id = oAuth2User.getAttribute("id");
            uvo.setUser_naver(id);
            uvo.setUser_nickname(name);
            uvo.setUser_id("naver_"+id);
            uvo.setProvider(provider);
            uvo.setUser_email(email);
        } else if (provider.equals("google")){
            id = oAuth2User.getAttribute("sub");
            uvo.setUser_google(id);
            uvo.setUser_nickname(name);
            uvo.setUser_id("google_"+id);
            uvo.setProvider(provider);
            uvo.setUser_email(email);
        }
        if(uvo != null){
            // 아이디가 존재하면 DB에 있는 것, 아니면 DB에 없는 것
            UserVO uvo2 = authMapper.findUserByProvider(uvo);
            if (uvo2 == null) {
                /* RegexGenerator reqexGenerator = new RegexGenerator(); */
                // 비밀번호 정규식으로 랜덤 생성 후 해싱
                /* String pw = BCrypt.hashpw(reqexGenerator.getRandomReqex(), BCrypt.gensalt());
                uvo.setUser_pw(pw); */
                authMapper.insertUserByProvider(uvo);
            }
        }
        return new User(uvo.getUser_id(), "", new ArrayList<>());
    }
    
    
    // 아이디 찾기
    public List<UserVO> userFindByEmail(String user_email){
        return authMapper.userFindByEmail(user_email);
    }
    
    
    // 정지 유무 확인
    public DataVO getUserBan(String user_idx){
        DataVO dvo = new DataVO();
        UserBanVO ubvo = authMapper.getUserBan(user_idx);
        // end_date()가 없을때(정지가 없을때)
        if(ubvo.getStop_end_date() == null){
            dvo.setSuccess(true);
            dvo.setData(ubvo);
            return dvo;
        }else{
            dvo.setSuccess(false);
            dvo.setData(ubvo);
            return dvo;
        }
    }
    
}

