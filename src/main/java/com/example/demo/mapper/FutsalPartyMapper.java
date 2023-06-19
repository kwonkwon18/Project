package com.example.demo.mapper;

import org.apache.ibatis.annotations.*;

import com.example.demo.domain.*;


@Mapper
public interface FutsalPartyMapper {

	
	@Delete("""
			delete from FutsalParty
			where boardId = #{boardId} and userId = #{userId} and memberId = #{memberId}
			""")
	Integer delete(FutsalParty futsalParty);

	
	@Insert("""
			insert into FutsalParty (boardId, userId, memberId)
			values (#{boardId}, #{userId}, #{memberId})
			""")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	Integer insert(FutsalParty futsalParty);

	
	@Select("""
			select count(*) from FutsalParty
			where boardId = #{boardId}
			""")
	Integer countByBoardId(Integer boardId);

	@Delete("""
			DELETE FROM FutsalParty
			WHERE boardId = #{boardId}
			""")
	Integer deleteByBoardId(Integer boardId);


	



}
