package com.ict.edu.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.ict.edu.common.util.JwtUtil;
import com.ict.edu.domain.auth.service.UserDetailService;
import com.ict.edu.jwt.JwtRequestFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    // 유효성 검사
    private final JwtRequestFilter jwtRequestFilter;
    // 토큰 생성 및 검사
    private final JwtUtil jwtUtil;
    
    private final UserDetailService userDetailService;
    
    // 생성자에서 매개변수 받은 값을 클래스의 필드에 할당
    public SecurityConfig(JwtRequestFilter jwtRequestFilter, JwtUtil jwtUtil, UserDetailService userDetailService){
        this.jwtRequestFilter = jwtRequestFilter;
        this.jwtUtil = jwtUtil;
        this.userDetailService = userDetailService;
    }
    
    
    // security filter chain
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        log.info("SecurityFilterChain 호출\n");
        http
                // cors 정책 설정
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // csrf (cross-site request forgery)보호 비활성화
                .csrf(csrf -> csrf.disable())
                // url 접근 권한 설정
                .authorizeHttpRequests(authorize -> authorize
                    // 인증 없이 접근
                    .requestMatchers(
                        "/api/**", "/", "/oauth/**", 
                        "/fna/**", "/download/**", "/auth/**")
                        .permitAll()
                    // 인증 필요
                    .requestMatchers("/admin/**").hasAnyAuthority("admin", "super")
                    .anyRequest().authenticated())
                    
                    
                // oauth2 설정
                .oauth2Login(oauth2 -> oauth2
                    // 인증 성공시 호출
                    .successHandler(oAuth2AuthenticationSuccessHandler())
                    // 사용자 정보 엔드포인트를 설정하고 정보를 처리할 서비스 지정
                    .userInfoEndpoint(userInfo -> userInfo.userService(oAuth2UserService())))
                    // UsernamePasswordAuthenticationFilter 이전에 실행
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
    @Bean
    OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
        return new OAuth2AuthenticationSuccessHandler(jwtUtil, userDetailService);
    }

    @Bean
    OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService() {
        return new CustomerOAuth2UserService();
    }
    
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        
        // 허용할 Origin 설정
        corsConfig.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        // 허용할 http 메서드 설정
        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // 허용할 헤더 설정
        corsConfig.setAllowedHeaders(Arrays.asList("*"));
        // 인증정보 허용
        corsConfig.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        return source;
    }
    
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    
}
