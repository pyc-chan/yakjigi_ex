package com.ict.edu.common.util;

import lombok.Data;

@Data
public class Paging {
	
	// 현재 페이지
	private int nowPage = 1 ; //
	
	// 현재 블럭
	private int nowBlock = 1 ; //
	
	// 한 페이지당 10줄
	private int numPerPage = 10; //
	
	// 한 블록당 5개
	private int pagePerBlock = 5; // 고정
	
	// DB의 게시물의 수	
	private int totalRecord = 0 ; //
	
	// 게시물의 수를 이용해서 전체 페이지의 수
	private int totalPage = 0 ; //
	
	private int totalBlock = 0 ; //
	
	
	// 한번에 가져올 게시물의 시작번호와 끝번호 
	private int beginBlock = 0 ; //
	private int endBlock = 0 ; //
	
	private int offset = 0 ; //


}