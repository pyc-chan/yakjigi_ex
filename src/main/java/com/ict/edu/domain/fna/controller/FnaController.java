package com.ict.edu.domain.fna.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ict.edu.domain.auth.vo.DataVO;
import com.ict.edu.domain.fna.service.FnaService;
import com.ict.edu.domain.fna.vo.FnaVO;

@RestController
@RequestMapping("/fna")
public class FnaController {
    
    @Autowired
    private FnaService fnaService;
    
    @GetMapping("/list")
    public List<FnaVO> getFnaList(){
        return fnaService.getFnaList();
    }
    
}
