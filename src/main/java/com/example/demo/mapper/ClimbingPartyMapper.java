package com.example.demo.mapper;

import org.apache.ibatis.annotations.*;

import com.example.demo.domain.*;

@Mapper
public interface ClimbingPartyMapper {

	@Delete("""
			delete from ClimbingParty
			where boardId = #{boardId} and userId = #{userId} and memberId = #{memberId}
			""")
	Integer delete(ClimbingParty climbingParty);

	
	@Insert("""
			insert into ClimbingParty (boardId, userId, memberId)
			values (#{boardId}, #{userId}, #{memberId})
			""")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	Integer insert(ClimbingParty climbingParty);

	
	@Select("""
			select count(*) from ClibmingParty
			where boardId = #{boardId}
			""")
	Integer countByBoardId(Integer boardId);

	@Delete("""
			DELETE FROM ClimbingParty
			WHERE boardId = #{boardId}
			""")
	Integer deleteByBoardId(Integer boardId);

	@Select("""
			select * from Member where userId = #{userId}
			""")
	Member selectMemberById(String name);




	
}
