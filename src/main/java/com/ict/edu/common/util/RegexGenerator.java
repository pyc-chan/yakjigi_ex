package com.ict.edu.common.util;

import java.util.regex.Pattern;
import java.security.SecureRandom;
import java.util.regex.Matcher;

public class RegexGenerator {
    // 영어 대문자
    private final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    // 영어 소문자
    private final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    // 숫자
    private final String DIGITS = "0123456789";
    // 특수문자
    private final String SPECIAL = "!@#$%^&*()-_=+[]{}|;:,.<>?";
    
    private final String ALL_CHARACTERS = UPPER + LOWER + DIGITS + SPECIAL;
    
    // 패턴 검사
    public boolean useRegex(final String input) {
        // 패턴 컴파일
        final Pattern pattern = 
        Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*])(?=.*[0-9])[A-Za-z\\d!@#$%^&*]{8,15}$");
        // 패턴 검사
        final Matcher matcher = pattern.matcher(input);
        // 결과 확인
        return matcher.matches();
    }
    
    
    public String getRandomReqex(){
        SecureRandom random = new SecureRandom();
        
        StringBuilder sb = new StringBuilder();
        String result;
        while (true) {
            int randomnumber = random.nextInt(8,16);
            sb.setLength(0);
            for(int i = 0; i<randomnumber; i++){
                int randomint = random.nextInt(ALL_CHARACTERS.length());
                sb.append(ALL_CHARACTERS.charAt(randomint));
            }
            if(useRegex(sb.toString())){
                result = sb.toString();
                break;
            }
        }
        return result;
    }
}
