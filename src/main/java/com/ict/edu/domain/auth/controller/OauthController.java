package com.ict.edu.domain.auth.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ict.edu.common.util.JwtUtil;
import com.ict.edu.domain.auth.service.UserDetailService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
public class OauthController {

    private final JwtUtil jwtUtil;
    private final UserDetailService userDetailService;

    
    // OAuth2 인증 성공 후 리다이렉션 처리
    
    @GetMapping("/success")
    public void handleSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException {
        log.info("OAuth2 Authentication Success");

        // Authentication에서 사용자 정보를 가져옵니다.
        if (authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

            String uri = request.getRequestURI();
            String provider = getProviderFromUri(uri);

            if (provider != null) {
                // 사용자 ID를 가져오고 JWT 토큰을 생성합니다.
                String id = oAuth2User.getAttribute("id").toString();
                String token = jwtUtil.generateToken(id);

                // 리다이렉션 URL을 생성합니다.
                String redirectUrl = createRedirectUrl(oAuth2User, token, id);
                log.info("Redirecting to: " + redirectUrl);

                response.sendRedirect(redirectUrl);
            } else {
                log.error("Provider not recognized.");
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid provider.");
            }
        }
    }

    
    // URI에서 OAuth2 제공자를 추출합니다.
    
    private String getProviderFromUri(String uri) {
        if (uri.contains("kakao")) {
            return "kakao";
        } else if (uri.contains("naver")) {
            return "naver";
        } else if (uri.contains("google")) {
            return "google";
        }
        return null;
    }

    
    // 리다이렉션 URL을 생성합니다.
    
    private String createRedirectUrl(OAuth2User oAuth2User, String token, String id) throws IOException {
        return String.format(
                "http://localhost:3000/login?token=%s&username=%s&name=%s&email=%s",
                URLEncoder.encode(token, StandardCharsets.UTF_8),
                URLEncoder.encode(id, StandardCharsets.UTF_8),
                URLEncoder.encode(oAuth2User.getAttribute("name"), StandardCharsets.UTF_8),
                URLEncoder.encode(oAuth2User.getAttribute("email"), StandardCharsets.UTF_8)
        );
    }
}
