package com.ict.edu.domain.userdose.controller;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
import com.ict.edu.domain.userdose.vo.PayloadVO;
import com.ict.edu.domain.userdose.vo.UserdoseVO;

@RestController
@RequestMapping("/page") // 기본 매핑 경로 설정
public class UserdoseController {
    
    @Autowired
    private UserdoseService userdoseService;

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

    // json 데이터 읽어오기
    @GetMapping("/medi-data/all")
    public ResponseEntity<List<Map<String, Object>>> getAllMediData() {
        try {
            ClassPathResource resource = new ClassPathResource("data/all_medi_data_2.json");
            String data = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

            ObjectMapper objectMapper = new ObjectMapper();
            List<Map<String, Object>> items = objectMapper.readValue(data, new TypeReference<List<Map<String, Object>>>() {});

            return ResponseEntity.ok(items);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // 캘린더(디비 리스트)
    @GetMapping("/mybasicboardlog")
    public List<UserdoseVO> getUserDoses(@RequestParam String userId) {
        List<UserdoseVO> doses = userdoseService.getUserDoses(userId);
        System.out.println("반환값: " + doses);
        return doses;
    }

    // 디비 삭제
    @DeleteMapping("/mybasicboardlog")
    public ResponseEntity<Void> deleteDose(@RequestParam String userId, @RequestParam String date) {
        try {
            userdoseService.deleteDose(userId, date); // 삭제 서비스 호출
            return ResponseEntity.ok().build(); // 성공 응답 반환
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 상세 보기
    @GetMapping("/mybasicboardlog/details")
    public List<UserdoseVO> getDetailsByDateAndUser(
        @RequestParam String date,
        @RequestParam String userId
    ) {
        return userdoseService.getDetailsByDateAndUser(date, userId);
    }


    // 저장하기
    @PostMapping("/receivepayload")
    public ResponseEntity<String> receivePayload(@RequestBody PayloadVO payloadVO) {
        try {
            System.out.println("Received Payload: " + payloadVO);

            payloadVO.getMedications().forEach(medication -> {
                System.out.println("Medication: " + medication);
            });

            userdoseService.saveMyBasicBoardLog(payloadVO);

            return ResponseEntity.ok("Payload received successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing payload");
        }
    }

    // 복용 기록 수정
    @PutMapping("/mybasicboardlog/edit")
    public ResponseEntity<String> updateDose(@RequestBody PayloadVO payloadVO) {
        try {
            userdoseService.updateDose(payloadVO);
            return ResponseEntity.ok("Data updated successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error updating data: " + e.getMessage());
        }
    }
}
