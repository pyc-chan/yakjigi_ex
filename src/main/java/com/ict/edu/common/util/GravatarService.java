package com.ict.edu.common.util;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;

@Service
public class GravatarService {

    // ID를 MD5로 해싱하여 Gravatar URL을 반환하는 메소드
    public String getGravatarUrl(String user_id) {
        // 사용자 ID를 해싱 (공백 제거 후 MD5 해싱)
        String userIdHash = md5(user_id.trim());

        // Gravatar URL 생성
        return "https://www.gravatar.com/avatar/" + userIdHash + "?s=200";
    }

    // MD5 해싱을 수행하는 헬퍼 메소드
    private String md5(String user_id) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(user_id.getBytes());

            // 해시값을 16진수로 변환
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not found", e);
        }
    }
}
