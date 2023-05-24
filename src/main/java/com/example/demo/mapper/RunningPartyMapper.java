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
			where boardId = #{boardId} and
			memberId = #{memberId}
			""")
	Integer delete(RunningParty runningParty);

	
	@Insert("""
			insert into RunningParty (boardId, userId, memberId)
			values (#{boardId}, #{userId}, #{memberId})
			""")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	Integer insert(RunningParty runningParty);

	@Select("""
			select count(*) from RunningParty
			where boardId = #{boardId}
			""")
	Integer countByBoardId(Integer boardId);


	



}
