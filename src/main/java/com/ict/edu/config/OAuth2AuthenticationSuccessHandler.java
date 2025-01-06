package com.ict.edu.config;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.ict.edu.common.util.JwtUtil;
import com.ict.edu.domain.auth.service.UserDetailService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
    
    private final JwtUtil jwtUtil;
    private final UserDetailService userDetailService;

    public OAuth2AuthenticationSuccessHandler(JwtUtil jwtUtil, UserDetailService userDetailService) {
        this.jwtUtil = jwtUtil;
        this.userDetailService = userDetailService;
    }

    // 실제 성공한 다음에 클라이언트로 리다이렉트 해주는 메서드
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {
        log.info("OAth2AuthenticationSuccessHandler");
            // authentication.getPrincipal가 OAuth2User(사용자정보를 가지고 있는객체)타입인지 확인한다.
            // authentication 객체는 현재 사용자의 인증상태
            // getPrincipal() 가지고 있는 사용자 정보를 반환
        if (authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
            String uri = request.getRequestURI();
            String provider = "";
            if (uri.contains("kakao")) {
                provider = "kakao";
            } else if (uri.contains("naver")) {
                provider = "naver";
            } else if (uri.contains("google")){
                provider = "google";
            } else{
                provider = null;
            }

            // 성공 후 토큰을 만들어서 클라이언트에게 리다이렉트 한다.
            // 사용자 정보를 DB에 넣자
            // UserDetails userDetails =
            // userDetailService.loadUserByOAuth2User(oAuth2User,provider);
            // String token = jwtUtil.generateToken(userDetails);
            if(provider != null){
                String id = oAuth2User.getAttribute("id").toString();
                String token = jwtUtil.generateToken(id);

                // 클라이언트에 토큰, 이름, email 등 정보를 가지고 간다.
                // response.setCharacterEncoding("UTF-8");
                String redirectUrl = String.format(
                        "http://localhost:3000/login?token=%s&username=%s&name=%s&email=%s",
                        URLEncoder.encode(token, StandardCharsets.UTF_8),
                        URLEncoder.encode(id, StandardCharsets.UTF_8),
                        URLEncoder.encode(oAuth2User.getAttribute("name"), StandardCharsets.UTF_8),
                        URLEncoder.encode(oAuth2User.getAttribute("email"), StandardCharsets.UTF_8));
                
                response.sendRedirect(redirectUrl);
            }
        }
    }
}