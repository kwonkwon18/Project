package com.example.demo.mapper;

import java.util.*;

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
			select count(*) from ClimbingParty
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

	@Select("""
			SELECT
				boardId,
				userId,
				memberId,
			    c.title
			  FROM ClimbingParty p
			  LEFT JOIN ClimbingMate c on c.id = p.boardId
			WHERE userId = #{userId} AND participation = 0;
			""")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	List<ClimbingParty> selectAlarmList(ClimbingParty climbingParty);

	@Select("""
			SELECT
				p.boardId,
				p.userId,
				p.memberId,
				p.participation,
			    c.title
			  FROM ClimbingParty p
			  LEFT JOIN ClimbingMate c on c.id = p.boardId
			WHERE userId = #{userId} AND participation = 1 or participation = 2 ;
			""")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	List<ClimbingParty> selectMemberAlarmList(ClimbingParty climbingParty);

	@Update("""
			UPDATE ClimbingParty
			SET
				participation = 1
			WHERE
				userId = #{userId} and memberId = #{memberId} and boardId = #{boardId}
			""")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	Integer updateMember(ClimbingParty climbingParty);

	@Update("""
			UPDATE ClimbingParty
			SET
				participation = 2
			WHERE
				userId = #{userId} and memberId = #{memberId} and boardId = #{boardId}
			""")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	Integer updateMemberDisagree(ClimbingParty climbingParty);

	@Update("""
			UPDATE ClimbingParty
			SET
				confirmation = 0
			WHERE
				userId = #{userId} and memberId = #{memberId} and boardId = #{boardId}
			""")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	Integer confirmationGuest(ClimbingParty climbingParty);

	@Select("""
			SELECT
				count(*)
			  FROM ClimbingParty p
			  LEFT JOIN ClimbingMate c on c.id = p.boardId
			WHERE host = #{userId} AND participation = 0 and confirmation = 1
			""")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	Integer countOfAlarmHost(String name);

	@Select("""
			SELECT
				count(*)
			  FROM ClimbingParty p
			  LEFT JOIN ClimbingMate c on c.id = p.boardId
			WHERE guest = #{userId} AND (participation = 1 or participation = 2) and confirmation = 1 ;
			""")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	Integer countOfAlarmGuest(String name);




	
}
