package com.ict.edu.common.handler;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import com.ict.edu.domain.auth.vo.DataVO;

public class FileUploadHandler {
    
    // 최종 경로
    private String path;
    
    // upload 기본 경로
    @Value("${file.upload-dir}")
    private String uploadDir;
    
    // 파일
    private MultipartFile file;
    
    // 업로드된 경로
    private String uploadPath;
    
    public FileUploadHandler(MultipartFile file, String uploadPath){
        this.file = file;
        this.uploadPath = uploadPath;
        path = uploadDir+"/"+uploadPath;
    }
    
    public DataVO FileUpload(){
        DataVO dvo = new DataVO();
        try {
            
            // 현재 날짜와 시간 가져오기
            LocalDateTime now = LocalDateTime.now();
            
            // 원하는 형식으로 포맷하기
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            String nowstr = now.format(formatter);
            
            // 업로드시간_파일명 의 형태로 저장
            String filename = nowstr+"_"+file.getOriginalFilename();
            
            file.transferTo(new File(path, filename));
            
            dvo.setSuccess(true);
            dvo.setMessage("업로드 성공");
            
            return dvo;
        } catch (Exception e) {
            dvo.setSuccess(false);
            dvo.setMessage("업로드 실패");
            return dvo;
        }
    }
    
    // 업로드 후 성공시 기존 파일 삭제
    public DataVO FileUpdate(String oldfile){
        DataVO dvo = new DataVO();
        try {
            // 업로드 실패시 리턴
            dvo = FileUpload();
            if(!dvo.isSuccess()){
                return dvo;
            }
            // 업로드 성공하면 기존 파일 삭제 시도
            File existingFile = new File(path, oldfile);
            if(existingFile.exists()){
                boolean isDeleted = existingFile.delete();
                if(!isDeleted){
                    dvo.setSuccess(false);
                    dvo.setMessage("기존 파일 삭제 실패");
                    return dvo;
                }
            }
            return dvo;
        } catch (Exception e) {
            dvo.setSuccess(false);
            dvo.setMessage("업데이트 실패");
            return dvo;
        }
    }
}
