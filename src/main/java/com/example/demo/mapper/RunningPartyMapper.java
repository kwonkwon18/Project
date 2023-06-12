package com.example.demo.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.example.demo.domain.RunningBoard;
import com.example.demo.domain.RunningParty;

@Mapper
public interface RunningPartyMapper {

	
	@Delete("""
			delete from RunningParty
			where boardId = #{boardId} and userId = #{userId} and memberId = #{memberId}
			""")
	Integer delete(RunningParty runningParty);

	
	@Insert("""
			insert into RunningParty (boardId, userId, memberId)
			values (#{boardId}, #{userId}, #{memberId})
			""")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	Integer insert(RunningParty runningParty);

	// 숫자가 perticipate 가 1인 것들만 해줘야함
	@Select("""
			SELECT COUNT(*) FROM RunningParty
			WHERE boardId = #{boardId} AND participation = 1;
			""")
	Integer countByBoardId(Integer boardId);

	@Delete("""
			DELETE FROM RunningParty
			WHERE boardId = #{boardId}
			""")
	Integer deleteByBoardId(Integer boardId);


	



}
