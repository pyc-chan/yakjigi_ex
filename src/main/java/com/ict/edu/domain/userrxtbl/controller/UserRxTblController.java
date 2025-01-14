package com.ict.edu.domain.userrxtbl.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ict.edu.common.util.FileUploadController;
import com.ict.edu.domain.auth.vo.DataVO;
import com.ict.edu.domain.userrxtbl.service.UserRxTblService;
import com.ict.edu.domain.userrxtbl.vo.UserRxTblVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/userrxtbl")
public class UserRxTblController {
    @Autowired
    private UserRxTblService userRxTblService;

    @GetMapping("/list")
    public DataVO getUserRxTblList() {
        DataVO dataVO = new DataVO();
    
        try {
            log.info("Controller: getUserRxTblList 호출");
            List<UserRxTblVO> list = userRxTblService.getUserRxTblList();
            log.info("Controller: list : " + list);
            dataVO.setSuccess(true);
            dataVO.setMessage("진료 기록 조회 성공");
            dataVO.setData(list);
        } catch (Exception e) {
            dataVO.setSuccess(false);
            dataVO.setMessage("진료 기록 조회 실패: " + e.getMessage());
            dataVO.setData(null);
            e.printStackTrace();
        }
    
        return dataVO;
    }
    
    // 요청 파라미터로 post_num
    
    
    @PostMapping("/detail")
    public DataVO getUserRxTblDetail(@RequestBody Map<String, String> requestBody) {
        DataVO dataVO = new DataVO();
        String post_num = requestBody.get("post_num"); // POST 요청 본문에서 rx_idx를 가져옴
        try {
            log.info("post_num : " + post_num);
            
            List<UserRxTblVO> urvo = userRxTblService.getUserRxTblById(post_num);

            if (urvo == null) {

                log.info("brvonull1 : " + urvo);
                log.info("brvonull2 : " + dataVO);

                dataVO.setSuccess(false);
                dataVO.setMessage("진료 기록 상세보기 실패1");
                return dataVO;
            }

            log.info("brvoTrue1 : " + urvo);
            log.info("brvoTrue2 : " + dataVO);

            dataVO.setSuccess(true);
            dataVO.setMessage("진료 기록 상세보기 성공");
            dataVO.setData(urvo);
        } catch (Exception e) {
            dataVO.setSuccess(false);
            dataVO.setMessage("진료 기록 상세보기 실패2: " + e.getMessage());
        }
        return dataVO;
    }
    
    // 그냥 파라미터로 변경
    @DeleteMapping("/delete")
    public DataVO getUserRxTblDelete(String post_num) {
        DataVO dataVO = new DataVO();

        try {
            int result = userRxTblService.getUserRxTblDelete(post_num);
            if (result == 0) {
                dataVO.setSuccess(false);
                dataVO.setMessage("진료 기록 삭제 실패");
                return dataVO;
            }
            dataVO.setSuccess(true);
            dataVO.setMessage("진료 기록 삭제 성공");
            // dataVO.setData(result);
        } catch (Exception e) {
            dataVO.setSuccess(false);
            dataVO.setMessage("진료 기록 삭제 오류 발생");
        }

        return dataVO;
    }
    
    @PostMapping("/write")
    public DataVO createBoardRecord(
        @RequestParam("data") String data,
        @RequestParam("file") MultipartFile file) {

        DataVO dataVO = new DataVO();

        log.info("환영합니다. 사실 환영 못해요ㅜㅜ");

        // JSON 문자열을 파싱하여 필요한 데이터 추출
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // JSON 문자열을 Map으로 변환
            Map<String, Object> payload = objectMapper.readValue(data, new TypeReference<Map<String, Object>>() {});
    
            // editorContent 값 추출
            String editorContent = (String) payload.get("editorContent");
            System.out.println("Editor Content: " + editorContent); // 로그로 출력
    
            // editorContent가 null인 경우 처리
            if (editorContent != null) {
                // p태그 없애는 메서드
                editorContent = editorContent.replaceAll("(?i)<p>|</p>", "");
                System.out.println("Editor Content after cleaning: " + editorContent); // 로그로 출력
            } else {
                // editorContent가 null일 경우 처리 (예: 빈 문자열로 초기화)
                editorContent = "";
                System.out.println("Editor Content is null, set to empty string");
            }
    
            // 파일이 넘어왔는지 확인
            String rx_photo = "";
            if (file != null && !file.isEmpty()) {
                System.out.println("File received: " + file.getOriginalFilename());
                System.out.println("File size: " + file.getSize() + " bytes");
                System.out.println("File type: " + file.getContentType());
    
                // 파일 업로드 방법
                FileUploadController fileUploadController = new FileUploadController(file, "user_rx");
                dataVO = fileUploadController.fileUpload();
                if (dataVO.isSuccess()) {
                    rx_photo = "http://localhost8080/api/user_rx/" + dataVO.getData().toString();
                    System.out.println("File uploaded successfully. URL: " + rx_photo);
                } else {
                    System.out.println("File upload failed: " + dataVO.getMessage()); // 실패한 경우 출력
                    return dataVO;  // 업로드 실패 시 리턴
                }
            } else {
                System.out.println("No file uploaded.");
            }
    
            // UserRxTblVO 객체 생성 및 데이터 설정
            UserRxTblVO urvo = new UserRxTblVO();
            urvo.setRx_photo(rx_photo);  // 업로드된 파일 URL 설정
            urvo.setRx_photo_name(file.getOriginalFilename());  // 파일 이름 설정
            System.out.println("Created urvo: " + urvo);  // urvo 값 출력
    
            // 데이터 저장
            System.out.println("Calling getUserRxTblWrite with urvo: " + urvo);
            int result = userRxTblService.getUserRxTblWrite(urvo);

            System.out.println("result : " + result);

            if (result == 0) {
                dataVO.setSuccess(false);
                dataVO.setMessage("게스트북 쓰기 실패");
                return dataVO;
            }
    
            // 성공 시 editorContent를 DataVO에 설정
            dataVO.setSuccess(true);
            dataVO.setMessage("게스트북 쓰기 성공");
    
        } catch (Exception e) {
            // 예외가 발생하면 먼저 로그를 찍고
            System.out.println("Exception caught: " + e.getMessage());
            log.error("오류 발생:", e);
            e.printStackTrace();  // 예외 스택 트레이스 출력
            dataVO.setSuccess(false);
            dataVO.setMessage("오류 발생: " + e.getMessage());
        }
    
        return dataVO;
    }
}
