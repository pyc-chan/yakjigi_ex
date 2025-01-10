package com.ict.edu.domain.comment.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ict.edu.common.util.FileUploadController;
import com.ict.edu.common.util.UserInfoService;
import com.ict.edu.domain.auth.vo.DataVO;
import com.ict.edu.domain.auth.vo.UserVO;
import com.ict.edu.domain.comment.service.CommentService;
import com.ict.edu.domain.comment.vo.CommentVO;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private UserInfoService userInfoService;
    
    @Autowired
    private CommentService commentService;
    
    @PostMapping("/join")
    public DataVO joinComment(@ModelAttribute CommentVO comvo, @RequestParam MultipartFile file){
        DataVO dvo = new DataVO();
        UserVO uvo = userInfoService.getUserVO();
        String user_idx = uvo.getUser_idx();
        if(user_idx == null || user_idx.isEmpty()){
            dvo.setMessage("로그인이 필요합니다.");
            dvo.setSuccess(false);
            return dvo;
        }
        comvo.setUser_idx(user_idx);
        // 파일 업로드를 했을때
        if(file != null && file.isEmpty()){
            // 생성자에 파일과 경로(폴더명)
            FileUploadController fileUploadController = new FileUploadController(file, "comment");
            // 파일 업로드 (결과는 datavo로 받음)
            dvo = fileUploadController.fileUpload();
            // 업로드 성공시
            if(dvo.isSuccess()){
                // 파일명을 넣는다.
                comvo.setComment_file_name(dvo.getData().toString());
                // comment_file_name 은 file명이 직접들어간다.
                
                // 파일경로를 넣는다.
                comvo.setComment_file("http://localhost8080/api/comment/"+dvo.getData().toString());
                // comment_file은 file의 url 주소가 들어간다.
                // 프론트에서 src 속성에 comment_file의 값을 넣으면 화면에 이미지 출력됨.
            }else{
                return dvo;
            }
        }
        dvo = new DataVO();
        if(commentService.postCommentJoin(comvo)>0){
            dvo.setSuccess(true);
            dvo.setMessage("댓글 작성 완료");
        }else{
            dvo.setSuccess(false);
            dvo.setMessage("댓글 작성 실패");
            dvo.setData(comvo);
        }
        return dvo;
    }
    
    @PutMapping("/update")
    public DataVO updateComment(@ModelAttribute CommentVO comvo, @RequestParam MultipartFile file){
        DataVO dvo = new DataVO();
        String comment_idx = comvo.getComment_idx();
        CommentVO oldcvo = commentService.getCommentDetail(comment_idx);
        if(file != null && !file.isEmpty()){
            if(file.getOriginalFilename().equals(oldcvo.getComment_file())){
                FileUploadController fileUploadController = new FileUploadController(file, "comment");
                if(oldcvo.getComment_file() != null){
                    dvo = fileUploadController.fileUpdate(oldcvo.getComment_file());
                }else{
                    dvo = fileUploadController.fileUpload();
                }
                comvo.setComment_file(dvo.getData().toString());
            }
        }
        if(!dvo.isSuccess()){
            return dvo;
        }
        dvo = new DataVO();
        if(commentService.putCommentUpdate(comvo)>0){
            dvo.setSuccess(true);
            dvo.setMessage("댓글 수정 완료");
        }else{
            dvo.setSuccess(false);
            dvo.setMessage("댓글 수정 실패");
        }
        return dvo;
    }
    
    // 댓글 삭제
    @PutMapping("/delete")
    public DataVO deleteComment(@RequestBody CommentVO comvo){
        DataVO dvo = new DataVO();
        if(commentService.putCommentDelete(comvo)>0){
            if(comvo.getComment_file_name() != null || !comvo.getComment_file_name().isBlank()){
                FileUploadController fileUploadController = new FileUploadController(null, "comment");
                dvo = fileUploadController.fileDelete(comvo.getComment_file_name());
                if(dvo.isSuccess()){
                    dvo.setMessage("댓글 및 파일 삭제 완료");
                    return dvo;
                }
                dvo.setSuccess(true);
                // 수정 요망 !!!!!!!!!!
                dvo.setMessage("댓글 삭제 성공 & 파일 삭제 실패");
            }
            dvo.setSuccess(true);
            dvo.setMessage("댓글 삭제 완료");
            return dvo;
        }
        dvo.setSuccess(false);
        dvo.setMessage("댓글 삭제 실패");
        return dvo;
    }
}
