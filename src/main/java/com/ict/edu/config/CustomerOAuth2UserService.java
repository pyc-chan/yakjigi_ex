package com.ict.edu.config;

import java.util.Map;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

// lombok 어노테이션, log객체를 자동으로 생성한다.
@Slf4j
// spring 어노테이션, 비즈니스 로직을 처리하는 클래스임을 나타낸다.
@Service

public class CustomerOAuth2UserService extends DefaultOAuth2UserService{
    
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        
        log.info("CustomerOAuth2UserService");
        
        // 부모클래스의 loadUser 메서드를 호출하여 기본 사용자 정보를 가져온다.
        OAuth2User oAuth2User = super.loadUser(userRequest);
        // 사용자 속성을 가져온다.
        Map<String, Object> attributes = oAuth2User.getAttributes();

        // 어떤 제공자 인지 알수 있다.(kakao, naver, google)
        String provider = userRequest.getClientRegistration().getRegistrationId();
        
        // 제공자가 kakao이면 실행
        if (provider.equals("kakao")) {
            // attributes 에서 kakao_account값을 kakaoAccount 라는 map에 넣는다
            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
            if (kakaoAccount == null) {
                throw new OAuth2AuthenticationException("Kakao error");
            }
            // 계정 정보에서 email값을 받는다.
            String email = (String) kakaoAccount.get("email");
            
            // properties 값은 properties map에 넣는다.
            Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
            if (properties == null) {
                throw new OAuth2AuthenticationException("Kakao error");
            }
            // nickname 값을 name에 넣는다.
            String name = (String) properties.get("nickname");

            log.info("kakao email : " + email);
            log.info("kakao name : " + name);
            
            // DefaultOAuth2User 객체를 생성해서 이메일, 이름, 아이디를 포함한 정보를 반환한다.
            return new DefaultOAuth2User(oAuth2User.getAuthorities(), Map.of(
                    "email", email,
                    "name", name,
                    "id", attributes.get("id")), "email");
            
            // 제공자가 네이버인 경우 실행
        } else if (provider.equals("naver")) {
            // 
            Map<String, Object> response = (Map<String, Object>) attributes.get("response");
            if (response == null) {
                throw new OAuth2AuthenticationException("Naver error");
            }
            String name = (String) response.get("name");
            String email = (String) response.get("email");
            String phone = (String) response.get("phone");

            log.info("naver email : " + email);
            log.info("naver name : " + name);
            return new DefaultOAuth2User(oAuth2User.getAuthorities(), Map.of(
                    "email", email,
                    "name", name,
                    "id", response.get("id")), "email");

        }
        return oAuth2User;
        
        
    }
    
}
