package com.example.demo.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.Member;
import com.example.demo.mapper.MemberMapper;

@Service
@Transactional(rollbackFor = Exception.class)
public class MemberService {
	
	@Autowired
	private MemberMapper mapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	public String getNickName(String userId) {
		
		return mapper.getNickNameByUserId(userId);
	}

	
	
	// 서재권 추가내용 *********** 
	public boolean signup(Member member) {
		// 암호를 새롭게 세팅해 준다.
		// plain은 입력해서 받아들여지는 암호
		// setPasswordEncoder를 통해서 다시 password를 set
		String plain = member.getPassword();
		member.setPassword(passwordEncoder.encode(plain));

		int cnt = mapper.insertMember(member);

		return cnt == 1;
		
	}



	public String getUserId(String yourNickName) {
		return mapper.getUserIdSelectByNickName(yourNickName);
	}
	
	public List<Member> listMember(){
		

		return mapper.selectAll();
	}
}
