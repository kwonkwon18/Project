package com.example.demo.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.*;
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

	public List<Member> listMember() {

		return mapper.selectAll();
	}

	public Member get(String userId) {
		return mapper.selectById(userId);
	}

	public boolean modify(Member member, String oldPassword) {
		Member oldMember = mapper.selectById(member.getUserId());

		int cnt = 0;
		if (oldMember.getPassword().equals(oldPassword)) {

			cnt = mapper.update(member);
		}

		return cnt == 1;
	}

	public boolean remove(Member member) {
		Member oldMember = mapper.selectById(member.getUserId());
		int cnt = 0;
		if (oldMember.getPassword().equals(member.getPassword())) {
			// 암호가 같으면?
			cnt = mapper.deleteById(member.getUserId());

		} else {
			// 암호가 같지 않으면?

		}

		return cnt == 1;
	}

	public boolean modify(Member member) {
		System.out.println(member);
		member.setId(mapper.getId(member.getUserId()));
		int cnt = mapper.updateMember(member);
		System.out.println("@@@" + cnt);
		
		return cnt == 1;
	}
	
	public Map<String, Object> IDCheck(String userId) {
		Member member = mapper.selectById(userId);
		
		return Map.of("available", member == null);
	}
	
	public Map<String, Object> checkNickName(String nickName) {
		Member member = mapper.selectByNickName(nickName);
		return Map.of("available", member == null);
	}
	
	public Map<String, Object> checkEmail(String email) {
		Member member = mapper.selectByEmail(email);
		
		return Map.of("available", member == null);
	}
}
