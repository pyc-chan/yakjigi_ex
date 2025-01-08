package com.ict.edu.domain.userdose.controller;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ict.edu.domain.userdose.service.UserdoseService;
import com.ict.edu.domain.userdose.vo.UserdoseVO;

@RestController
@RequestMapping("/api") // 기본 매핑 경로 설정
@CrossOrigin(origins = "http://localhost:3000") // CORS 허용
public class UserdoseController {

    @Autowired
    private UserdoseService mybasicboardlogService;

    // JSON 이미지 렌더링
    @GetMapping("/proxy/image")
    public ResponseEntity<byte[]> proxyImage(@RequestParam String url) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            byte[] imageBytes = restTemplate.getForObject(url, byte[].class);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // 이미지 타입에 맞게 설정

            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // JSON 데이터 기반으로 약 이름 매칭 후 이미지 URL 반환 API
    @GetMapping("/medi-data/match")
    public ResponseEntity<List<Map<String, Object>>> getMediData(
            @RequestParam(required = false, defaultValue = "") String mediNames) {

        try {
            System.out.println("데이터 값: "+ mediNames);
            ClassPathResource resource = new ClassPathResource("data/all_medi_data_2.json");
            String data = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

            ObjectMapper objectMapper = new ObjectMapper();
            List<Map<String, Object>> items = objectMapper.readValue(data, new TypeReference<List<Map<String, Object>>>() {});


             // 문자열을 리스트로 변환
            List<String> targetItemNames = Arrays.stream(mediNames.split(","))
                                                 .map(String::trim) // 앞뒤 공백 제거
                                                 .collect(Collectors.toList());

            // 이름 목록에 해당하는 항목 필터링
            List<Map<String, Object>> matchedItems = items.stream()
                    .filter(item -> targetItemNames.contains(item.get("item_name")))
                    .collect(Collectors.toList());

            // 결과 출력
            if (!matchedItems.isEmpty()) {
                return ResponseEntity.ok(matchedItems);
            } else {
                System.out.println("일치하는 항목을 찾을 수 없습니다.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(List.of());
            }
           // return ResponseEntity.ok(medicines);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        
    }

    // 캘린더(디비 리스트)
    @GetMapping("/mybasicboardlog")
    public List<UserdoseVO> getUserDoses(@RequestParam String userId) {
        return mybasicboardlogService.getUserDoses(userId);
    }

    // 디비 삭제
    @DeleteMapping("/mybasicboardlog")
    public ResponseEntity<Void> deleteDose(@RequestParam String userId, @RequestParam String date) {
        try {
            mybasicboardlogService.deleteDose(userId, date); // 삭제 서비스 호출
            return ResponseEntity.ok().build(); // 성공 응답 반환
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 상세 보기
    @GetMapping("/mybasicboardlog/details")
    public List<UserdoseVO> getDetailsByDate(@RequestParam String date, @RequestParam String userId) {
        try {
            return mybasicboardlogService.getDetailsByDateAndUser(date, userId);
        } catch (Exception e) {
            e.printStackTrace();
            return null; // 에러 처리 필요
        }
    }

    
    // 디비 저장
    @PostMapping("/mybasicboardlog/save")
    public ResponseEntity<Void> saveMyBasicBoardLog(@RequestBody UserdoseVO requestData) {
        try {
            System.out.println("Received data: " + requestData); // 디버깅 로그
            mybasicboardlogService.saveMyBasicBoardLog(requestData);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
