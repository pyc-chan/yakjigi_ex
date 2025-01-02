package com.ict.edu.common.util;

import java.io.File;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/download")
public class FileDownloadController {
    
    // upload 기본 경로
    @Value("${file.upload-dir}")
    private String uploadDir;
    
    // uploadpath는 업로드된 경로(notice, user, qna, comment 등등)
    @GetMapping("/{uploadpath}/{filename}")
    public ResponseEntity<FileSystemResource> downloadFile(@PathVariable String uploadpath,@PathVariable String filename){
        // 경로 설정
        File file = new File((uploadDir+"/"+uploadpath), filename);
        
        // 파일이 존재하는지 확인
        if(!file.exists()){
            // 없으면 404 오류 리턴
            return ResponseEntity.notFound().build();
        }
        
        FileSystemResource resource = new FileSystemResource(file);
        
        // HTTP 헤더에 들어가기 때문에 utf-8 인코딩이 필요
        String encodedFilename;
        
        try {
            encodedFilename = URLEncoder.encode(filename, StandardCharsets.UTF_8.toString());
        } catch (Exception e) {
            // 인코딩 실패 시 기본 파일 이름 사용
            encodedFilename = filename; 
        }
        
        // http 응답을 생성하는 responseentity 빌더를 사용해 http 상태를 200으로 설정한다.
        // header에 content-disposition을 설정하며 attachment; filename="filename" 을 사용하여 브라우저가 파일을 다운로드 하도록 지시한다.
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+encodedFilename+"\"")
        .body(resource);
        
    }
}
