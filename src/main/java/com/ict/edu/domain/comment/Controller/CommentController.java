package com.ict.edu.domain.comment.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ict.edu.common.util.FileUploadController;
import com.ict.edu.domain.auth.vo.DataVO;
import com.ict.edu.domain.comment.service.CommentService;
import com.ict.edu.domain.comment.vo.CommentVO;

@RestController
@RequestMapping("/comment")
public class CommentController {
    
    @Autowired
    private CommentService commentService;
    
    @PostMapping("/join")
    public DataVO joinComment(CommentVO comvo){
        DataVO dvo = new DataVO();
        if(comvo.getFile() != null && !comvo.getFile().isEmpty()){
            FileUploadController fileUploadController = new FileUploadController(comvo.getFile(), "comment");
            dvo = fileUploadController.FileUpload();
            if(dvo.getData() != null){
                comvo.setComment_file(dvo.getData().toString());
                comvo.setFile(null);
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
        }
        return dvo;
    }
    
    @PutMapping("/update")
    public DataVO updateComment(CommentVO comvo){
        DataVO dvo = new DataVO();
        String comment_idx = comvo.getComment_idx();
        CommentVO oldcvo = commentService.getCommentDetail(comment_idx);
        if(comvo.getFile() != null){
            if(!comvo.getFile().getOriginalFilename().equals(oldcvo.getComment_file())){
                FileUploadController fileUploadController = new FileUploadController(comvo.getFile(), "comment");
                if(oldcvo.getComment_file() != null){
                    dvo = fileUploadController.FileUpdate(oldcvo.getComment_file());
                }else{
                    dvo = fileUploadController.FileUpload();
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
}
