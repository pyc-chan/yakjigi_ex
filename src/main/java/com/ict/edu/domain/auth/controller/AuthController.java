package com.ict.edu.domain.auth.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ict.edu.common.util.FileUploadController;
import com.ict.edu.common.util.GravatarService;
import com.ict.edu.domain.admin.service.AdminService;
import com.ict.edu.domain.auth.service.AuthAPIService;
import com.ict.edu.domain.auth.service.EmailService;
import com.ict.edu.domain.auth.service.UserDetailService;
import com.ict.edu.domain.auth.vo.AdminVO;
import com.ict.edu.domain.auth.vo.DataVO;
import com.ict.edu.domain.auth.vo.UserBanVO;
import com.ict.edu.domain.auth.vo.UserVO;
import com.ict.edu.domain.user.service.UserService;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private AuthAPIService authAPIService;
    
    @Autowired 
    private UserService userService;
    
    @Autowired
    private UserDetailService userDetailService;
    
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private AdminService adminService;
    
    // 로그인
    @PostMapping("/login")
    public DataVO userLogin(@RequestBody UserVO uvo){
        DataVO dvo = new DataVO();
        AdminVO avo = adminService.getAdminLogin(uvo.getUser_id());
        Map<String, String> request = new HashMap<>();
        if(avo != null && !avo.getAdmin_idx().isEmpty()){
            if(BCrypt.checkpw(uvo.getUser_pw(), avo.getAdmin_pw())){
                if(avo.getAdmin_level_idx().equals("3")){
                    dvo.setMessage("활동이 정지된 계정입니다.");
                    dvo.setSuccess(false);
                    return dvo;
                }else if(avo.getAdmin_level_idx().equals("2")){
                    request.put("role","GeneralApr");
                }else if(avo.getAdmin_level_idx().equals("1")){
                    request.put("role","Super");
                }
                request.put("user_id", avo.getAdmin_id());
                request.put("admin_idx", avo.getAdmin_idx());
                log.info("어드민 idx : "+request.get("admin_idx"));
                String token = authAPIService.generateToken(request);
                dvo.setData(token);
                dvo.setSuccess(true);
                dvo.setMessage("관리자 계정 로그인 성공");
                return dvo;
            }else{
                dvo.setSuccess(false);
                dvo.setMessage("아이디 혹은 비밀번호가 다릅니다.");
            }
        }
        UserVO dbUvo = userDetailService.getUserDetail(uvo.getUser_id());
        // 아이디 존재여부 확인
        if(dbUvo != null){
            // 비밀번호 맞는지 확인
            if(BCrypt.checkpw(uvo.getUser_pw(), dbUvo.getUser_pw())){
                // 정지 여부 확인
                dvo = userDetailService.getUserBan(dbUvo.getUser_level_idx());
                List<UserBanVO> listubvo = (List<UserBanVO>)dvo.getData();
                // 정지 상태라면
                if(!dvo.isSuccess()){
                    dvo.setSuccess(false);
                    LocalDate userbandate = LocalDate.now();
                    for (UserBanVO userBanVO : listubvo) {
                        if(userBanVO.getStop_end_date().isAfter(userbandate)){
                            userbandate = userBanVO.getStop_end_date();
                        }
                    }
                    dvo.setMessage("정지된 상태입니다. 정지 기간 : "+userbandate+" 까지");
                    return dvo;
                }
                request.put("user_id", uvo.getUser_id());
                if(dbUvo.getUser_level_idx().equals("1")){
                    request.put("role", "General");
                }else if(dbUvo.getUser_level_idx().equals("2")){
                    request.put("role", "ProPen");
                }else if(dbUvo.getUser_level_idx().equals("3")){
                    request.put("role", "ProApr");
                }else if(dbUvo.getUser_level_idx().equals("4")){
                    request.put("role", "ProDecl");
                }
                request.put("user_id", dbUvo.getUser_id());
                request.put("user_idx", dbUvo.getUser_idx());
                String token = authAPIService.generateToken(request);
                dvo.setData(token);
                dvo.setSuccess(true);
                dvo.setMessage("토큰 생성 완료");
            }
        }else{
            log.info("관리자 로그인 실패");
            dvo.setSuccess(false);
            dvo.setMessage("아이디 혹은 비밀번호가 올바르지 않습니다.");
        }
        return dvo;
    }
    
    // 회원가입/아이디 찾기/비밀번호 찾기시 이메일 확인
    @PostMapping("/emailchk")
	public DataVO emailchk(@RequestBody UserVO uvo) {
        DataVO dvo = new DataVO();
        // 임시번호 6자리 만들기
        Random random = new Random();
        // 0 ~ 1000000 미만의 정수를 무작위로 생성 (6자리 숫자 중 하나를 랜덤으로 만듬
        String randomNumber = String.valueOf(random.nextInt(1000000));
        // 길이가 6자리가 안되면 0으로 채움
        if(randomNumber.length() < 6) {
            int substract = 6-randomNumber.length();
            StringBuffer sb = new StringBuffer();
            for(int i=0; i<substract; i++) {
                sb.append("0");
            }
            sb.append(randomNumber);
            randomNumber = sb.toString();
        }
        // 임시번호 서버에 출력
        System.out.println("임시번호 : "+randomNumber);
        dvo = emailService.sendEmail(uvo, randomNumber);
        Map<String, Object> map = new HashMap<>();
        map.put("uvo", uvo);
        map.put("randomNumber", randomNumber);
        dvo.setData(map);
        return dvo;
	}
    
    
    // 회원가입
    @PostMapping("/join")
    public DataVO userJoin(@RequestParam(value = "file", required = false) MultipartFile file, @ModelAttribute UserVO uvo){
        DataVO dvo = new DataVO();
        System.out.println("회원가입 컨트롤러 도착");
        uvo.setUser_pw(BCrypt.hashpw(uvo.getUser_pw(), BCrypt.gensalt()));
        GravatarService gravatarService = new GravatarService();
        String profileUrl = gravatarService.getGravatarUrl(uvo.getUser_id());
        uvo.setUser_profile(profileUrl);
        if(uvo.getUser_level_idx().equals("2")){
            if(file != null && !file.isEmpty()){
                FileUploadController fileUploadController = new FileUploadController(file, "lisence");
                dvo = fileUploadController.fileUpload();
                if(!dvo.isSuccess()){
                    System.out.println("면허 업로드 실패");
                    return dvo;
                }
                uvo.setUser_lisence_path("/download/api/lisence/"+dvo.getData().toString());
            }
        }
        
        
        /* System.out.println("DB가기 전");
        System.out.println("사용자 인덱스: " + uvo.getUser_idx());
        System.out.println("사용자 ID: " + uvo.getUser_id());
        System.out.println("사용자 레벨 인덱스: " + uvo.getUser_level_idx());
        System.out.println("사용자 비밀번호: " + uvo.getUser_pw());
        System.out.println("사용자 프로필: " + uvo.getUser_profile());
        System.out.println("사용자 프로필 이름: " + uvo.getUser_profile_name());
        System.out.println("사용자 등록일: " + uvo.getUser_reg_date());
        System.out.println("사용자 이름: " + uvo.getUser_name());
        System.out.println("사용자 닉네임: " + uvo.getUser_nickname());
        System.out.println("제공자: " + uvo.getProvider());
        System.out.println("사용자 카카오: " + uvo.getUser_kakao());
        System.out.println("사용자 네이버: " + uvo.getUser_naver());
        System.out.println("사용자 구글: " + uvo.getUser_google());
        System.out.println("사용자 생일: " + uvo.getUser_birth_date());
        System.out.println("사용자 성별: " + uvo.getUser_gender().getGender());
        System.out.println("사용자 레벨: " + uvo.getUser_level_name().getLevelName());
        System.out.println("사용자 면허: " + uvo.getUser_license().getLicense());
        System.out.println("사용자 이메일: " + uvo.getUser_email());
        System.out.println("사용자 전화번호: " + uvo.getUser_phone());
        System.out.println("관리자 인덱스: " + uvo.getAdmin_idx());
        System.out.println("사용자 퇴사일: " + uvo.getUser_out_date());
        System.out.println("사용자 퇴사 사유: " + uvo.getUser_out_reason());
        System.out.println("사용자 마지막 로그인: " + uvo.getUser_last_login());
        System.out.println("사용자 권한: " + uvo.getAuthorities()); */
        int num = userService.postUserJoin(uvo);
        /* System.out.println("DB갔다옴"); */
        if(num >0){
            dvo.setMessage("회원가입 성공");
            dvo.setSuccess(true);
        }else{
            dvo.setMessage("회원가입중 오류 발생");
            dvo.setSuccess(false);
        }
        return dvo;
    }
    
    // 아이디 찾기
    @PostMapping("/findid")
    public DataVO userFindById(String user_email){
        DataVO dvo = new DataVO();
        List<UserVO> list = userDetailService.userFindByEmail(user_email);
        if(list != null){
            dvo.setSuccess(true);
            dvo.setMessage("아이디 찾기 성공");
            dvo.setData(list);
        }else{
            dvo.setSuccess(false);
            dvo.setMessage("가입된 아이디가 없습니다.");
        }
        return dvo;
    }
    
    // 비밀번호 찾기
    @PostMapping("/findpw")
    public DataVO findPw(@RequestBody UserVO uvo){
        DataVO dvo = new DataVO();
        uvo.setUser_pw(BCrypt.hashpw(uvo.getUser_pw(), BCrypt.gensalt()));
        UserVO dbuvo = userDetailService.getUserDetail(uvo.getUser_id());
        if(dbuvo != null && !dbuvo.getUser_idx().isEmpty()){
            dvo.setSuccess(true);
            dvo.setMessage("비밀번호 찾기 성공");
        }else{
            dvo.setData(uvo);
            dvo.setMessage("아이디 혹은 이메일이 다릅니다.");
            dvo.setSuccess(false);
        }
        return dvo;
    }
    
    // 아이디 중복 확인
    @PostMapping("/idchk")
    public DataVO getIDChk(@RequestBody UserVO uvo){
        String user_id = uvo.getUser_id();
        DataVO dvo = userDetailService.getIDChk(user_id);
        return dvo;
    }
    
}
