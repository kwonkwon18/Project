package com.example.demo.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.demo.domain.Member;
import com.example.demo.mapper.MemberMapper;

@Component
public class CustomUserDetailsService implements UserDetailsService {
	
	// id를 통해서 회원 정보를 조회해줘야함 
	@Autowired
	private MemberMapper mapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Member member = mapper.getMemberInfoByUserId(username);
		System.out.println(member);
		if(member == null) {
			throw new UsernameNotFoundException(username + " 회원이 없습니다.");
		}
		
//		List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
//		for (String auth : member.getAuthority()) {
//			authorityList.add(new SimpleGrantedAuthority(auth));
//		}

		
		UserDetails user = User.builder()
				.username(member.getUserId())
				.password(member.getPassword())
//				.authorities(member.getAuthority().stream().map(SimpleGrantedAuthority::new).toList())
				.authorities(List.of())
				.build();
		System.out.println(user);
		return user;
	}
	

}
