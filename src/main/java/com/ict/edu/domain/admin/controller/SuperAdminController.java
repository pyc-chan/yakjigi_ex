package com.ict.edu.domain.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ict.edu.domain.admin.service.AdminService;
import com.ict.edu.domain.auth.vo.AdminVO;
import com.ict.edu.domain.auth.vo.DataVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/super")
public class SuperAdminController {
    
    @Autowired
    private AdminService adminService;
    
    // 관리자 리스트
    @GetMapping("/list")
    public DataVO getAdminList(){
        DataVO dvo = new DataVO();
        log.info("Super 컨트롤러 도착");
        List<AdminVO> list = adminService.getAdminList();
        if(list != null && !list.isEmpty()){
            dvo.setData(list);
            dvo.setSuccess(true);
            dvo.setMessage("관리자 리스트 불러오기 성공");
        }else{
            dvo.setSuccess(false);
            dvo.setMessage("관리자 리스트 불러오기 실패");
        }
        return dvo;
    }
    
    @GetMapping("/detail")
    public DataVO getAdminDetail(@RequestParam String admin_idx){
        DataVO dvo = new DataVO();
        AdminVO avo = adminService.getAdminDetail(admin_idx);
        if(avo!=null){
            dvo.setData(avo);
            dvo.setSuccess(true);
            dvo.setMessage("관리자 상세정보");
        }else{
            dvo.setSuccess(false);
            dvo.setMessage("관리자 상세정보 불러오기 실패");
        }
        return dvo;
    }
    
    @PostMapping("/join")
    public DataVO postAdminJoin(@RequestBody AdminVO avo){
        DataVO dvo = new DataVO();
        log.info("컨트롤러 도착");
        if(adminService.postAdminJoin(avo)>0){
            dvo.setSuccess(true);
            dvo.setMessage("가입 성공");
        }else{
            dvo.setMessage("가입 실패");
            dvo.setSuccess(false);
        }
        return dvo;
    }
    
    @PutMapping("/update")
    public DataVO putAdminUpdate(@RequestBody AdminVO avo){
        DataVO dvo = new DataVO();
        if(adminService.putAdminUpdate(avo)>0){
            dvo.setMessage("수정 성공");
            dvo.setSuccess(true);
        }else{
            dvo.setMessage("수정 실패");
            dvo.setSuccess(false);
        }
        return dvo;
    }
    
    @DeleteMapping("/delete")
    public DataVO deleteAdmin(@RequestParam String admin_idx){
        DataVO dvo = new DataVO();
        if(adminService.deleteAdmin(admin_idx)>0){
            dvo.setMessage("삭제 성공");
            dvo.setSuccess(true);
        }else{
            dvo.setSuccess(false);
            dvo.setMessage("삭제 실패");
        }
        return dvo;
    }
    
}
