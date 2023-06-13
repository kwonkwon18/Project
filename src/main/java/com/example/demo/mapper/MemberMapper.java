package com.example.demo.mapper;

import java.util.*;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.demo.domain.Member;

@Mapper
public interface MemberMapper {

	@Select("""
			SELECT nickName FROM Member
			WHERE userId = #{userId}
			""")
	String getNickNameByUserId(String userId);
	
	// ** 서재권 추가내용
	@Select("""
			SELECT * FROM Member
			WHERE userId = #{userId}
			""")
	Member getMemberInfoByUserId(String userId);
	

	
	@Insert("""
			insert into Member
			(userId, password, name, nickName, birth, gender, career, address, phone, email, introduce)
			values (#{userId}, #{password}, #{name} ,#{nickName}, #{birth}, #{gender}, #{career}, #{address}, #{phone}, #{email}, #{introduce})
			""")
	Integer insertMember(Member member);
	////////////////

	@Select("""
			SELECT userId FROM Member
			WHERE nickName = #{invitedNickName}
			""")
	String getUserIdSelectByNickName(String invitedNickName);

	@Select("""
			SELECT userId FROM Member
			WHERE nickName LIKE CONCAT('%', #{search}, '%')
			""")
	List<String> UserIdSelectBySearch(String search);
	
}
