package com.smartfitness.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.smartfitness.demo.config.auth.Auth;
import com.smartfitness.demo.config.jwt.JwtTokenProvider;
import com.smartfitness.demo.mapper.MembersMapper;
import com.smartfitness.demo.model.Members;
import com.smartfitness.demo.model.MembersDetail;
import com.smartfitness.demo.service.MembersService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("members")
@RestController
public class MembersRestController {
	
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;
	private final MembersMapper membersMapper;
	
	Gson gson = new Gson();
	
	@Autowired
	MembersService membersService;
	
	//회원가입
	@PostMapping("/join")
	public String joinMembers(@RequestBody Members members) {
		System.out.println(members.toString());
		try {
			String rawPassword=members.getMem_pw();
			String encPassword=passwordEncoder.encode(rawPassword);
			members.setMem_pw(encPassword);
			members.setMem_type("M"); // 일반M / 관리자A
			System.out.println(members);
			membersService.join(members);
			return "success";
		} catch(Exception e) {
			e.printStackTrace();
			return "fail";
		}
	}
	
	//로그인
	@PostMapping("/login")
	public String login(@RequestBody Map<String, String> param) throws Exception{
		String jsonStr=gson.toJson(param);
		
		Members user=gson.fromJson(jsonStr, Members.class);
		System.out.println(user.toString());
		
		MembersDetail members = membersMapper.findByUserId(user.getMem_id());
		
		if(members ==null) {
			throw new UsernameNotFoundException("유효하지 않은 로그인 정보입니다.");
		}
		
		if(!passwordEncoder.matches(user.getMem_pw(), members.getMem_pw())) {
			throw new IllegalAccessException("잘못된 비밀번호입니다.");
		}
		
		String token= jwtTokenProvider.createToken(members.getMem_id(), members.getMem_name());
		String user_name=members.getMem_name();
		String user_id=members.getMem_id();
		Auth auth=new Auth(token, user_id, user_name);
		String result = gson.toJson(auth);
		System.out.println(result);
		return result;
	}
	
	//토큰이 가지고 있는 값에 따라 권한 확인할 수 있음
	@GetMapping("/user/test")
	public String ResponseTest() {
		return "user ok";
	}
	
	@GetMapping("/admin/test")
	public String adminResponseTest() {
		return "admin ok";
	}

}
