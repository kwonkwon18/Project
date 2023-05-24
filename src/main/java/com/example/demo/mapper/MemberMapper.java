package com.example.demo.mapper;

import org.apache.ibatis.annotations.*;

@Mapper
public interface MemberMapper {

	@Select("""
			SELECT nickName FROM Member
			WHERE userId = #{userId}
			""")
	String getNickNameByUserId(String userId);

}
