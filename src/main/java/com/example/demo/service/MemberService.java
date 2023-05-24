package com.example.demo.service;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.example.demo.mapper.*;

@Service
public class MemberService {
	
	@Autowired
	private MemberMapper mapper;

	public String getNickName(String userId) {
		
		return mapper.getNickNameByUserId(userId);
	}

}
