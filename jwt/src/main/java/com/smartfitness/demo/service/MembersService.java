package com.smartfitness.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartfitness.demo.mapper.MembersMapper;
import com.smartfitness.demo.model.Members;

@Service
public class MembersService {
	
	@Autowired
	MembersMapper membersMapper;
	
	public int join(Members members) {
		return membersMapper.join(members);
	}
	
	public Members login(Members members) {
		return membersMapper.login(members);
	}
}
