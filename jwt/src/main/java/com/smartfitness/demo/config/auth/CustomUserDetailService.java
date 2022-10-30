package com.smartfitness.demo.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.smartfitness.demo.mapper.MembersMapper;


@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService{
	private MembersMapper membersMapper;
	
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException{
		UserDetails userDetails = membersMapper.findByUserId(userId);
		
		if(userDetails == null) {
			throw new UsernameNotFoundException("유효하지 않은 로그인 정보입니다.");
		}
		
		return userDetails;
	}
}
