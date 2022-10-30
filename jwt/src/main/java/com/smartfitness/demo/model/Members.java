package com.smartfitness.demo.model;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
@NoArgsConstructor
public class Members {

	// 회원 아이디
	@NonNull
    private String mem_id;

    // 회원 비밀번호
	@NonNull
    private String mem_pw;

    // 회원 등급
    private String mem_grade;

    // 회원 가입일
    private Date mem_joindate;

    // 회원 이름
	@NonNull
    private String mem_name;

    // 회원 주소
    private String mem_addr;

    // 회원 생년월일
    private String mem_birthdate;

    // 회원 전화번호
	@NonNull
    private String mem_phone;

    // 회원 유형 일반회원:'M', 관리자:'A'
	private String mem_type;

    // 소속 센터 순번 
	@NonNull
	private Integer ctr_seq;

}
