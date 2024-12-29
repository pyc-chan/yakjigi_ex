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
            // nickname 값을 추출한다.
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
            // attributes 에서 respons값을 map에 넣는다
            Map<String, Object> response = (Map<String, Object>) attributes.get("response");
            if (response == null) {
                throw new OAuth2AuthenticationException("Naver error");
            }
            
            // respons에서 이름/이메일/전화번호 정보를 추출한다.
            String name = (String) response.get("name");
            String email = (String) response.get("email");
            String phone = (String) response.get("phone");
            
            log.info("naver email : " + email);
            log.info("naver name : " + name);
            // DefaultOAuth2User 객체를 생성해서 이메일, 이름, 아이디를 포함한 정보를 반환한다.
            return new DefaultOAuth2User(oAuth2User.getAuthorities(), Map.of(
                    "email", email,
                    "name", name,
                    "phone", phone,
                    "id", response.get("id")), "email");
            // 제공자가 구글인 경우
        }else if(provider.equals("google")){
            String email = (String) attributes.get("email");
            String name = (String) attributes.get("name");
            String picture = (String) attributes.get("picture"); // 프로필 사진 URL
            
            log.info("google email : " + email);
            log.info("google name : " + name);
            log.info("google picture : " + picture);

            // DefaultOAuth2User 객체를 생성해서 이메일, 이름, 프로필 사진 URL을 포함한 정보를 반환한다.
            return new DefaultOAuth2User(oAuth2User.getAuthorities(), Map.of(
                    "email", email,
                    "name", name,
                    "picture", picture,
                    "id", attributes.get("sub")), "email");
        }
        return oAuth2User;
    }
}
// 구글 OAuth 2.0 인증 과정
// 1. 클라이언트가 OAuth Server에 허가 코드를 요청한다.
// 2. OAuth Server가 클라이언트에 Redirect URL을 이용해서 허가코드를 응답한다.
// 3. 클라이언트에서 BackEnd Server에 허가코드를 전달한다.
// 4. BackEnd Server에서 OAuth Server에 허가 코드를 전달하고 필요한 유저의 정보를 요청한다.
// 5. OAuth 에서 BackEnd Server로 접근코드와 유저정보를 응답한다.
// 6. BackEnd Server에서 클라이언트로 접근코드와 유저정보를 보낸다.
// 참고자료 링크 ctrl 클릭하면 페이지 열립니다.
// 프론트 참고자료 https://kg-dlife.tistory.com/248
// 백엔드 참고자료 https://velog.io/@iamtaehoon/OAuth-%EC%9D%B8%EC%A6%9D-%EA%B5%AC%ED%98%84%ED%95%98%EA%B8%B0-Backend-API-%EA%B4%80%EC%A0%90%EC%97%90%EC%84%9C-%EC%9E%91%EC%84%B1%EC%A4%91
