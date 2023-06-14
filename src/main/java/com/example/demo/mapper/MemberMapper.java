package com.example.demo.mapper;

import java.util.*;


import org.apache.ibatis.annotations.*;


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
			(userId, password, name, nickName, birth, gender, address, phone, email, introduce)
			values (#{userId}, #{password}, #{name} ,#{nickName}, #{birth}, #{gender}, #{address}, #{phone}, #{email}, #{introduce})
			""")
	Integer insertMember(Member member);
	////////////////
	@Select("""
			SELECT *
			FROM Member
			ORDER BY inserted DESC
			""")
	List<Member> selectAll();

	@Select("""
			SELECT *
			FROM Member
			WHERE userId = #{userId}
			""")
	Member selectById(String integer);	

	@Delete("""
			DELETE FROM Member
			WHERE id=#{id}
			""")

	Member deleteById(String integer);
	@Update("""
			UPDATE Member
			SET password = #{password},
				name 	 = #{name},
				nickName = #{nickName},
				birth 	 = #{birth},
				gender 	 = #{gender},
				address  = #{address},
				phone	 = #{phone},
				email	 = #{email},
				introduce= #{introduce}
			WHERE
				userId = #{userId};
			""")
	Integer update(Member member);

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