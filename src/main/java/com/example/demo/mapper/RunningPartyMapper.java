package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.domain.RunningParty;

@Mapper
public interface RunningPartyMapper {

	@Delete("""
			delete from RunningParty
			where boardId = #{boardId} and userId = #{userId} and memberId = #{memberId}
			""")
	Integer delete(RunningParty runningParty);

	@Insert("""
			insert into RunningParty (boardId, userId, memberId, host, guest)
			values (#{boardId}, #{userId}, #{memberId}, #{host}, #{guest})
			""")
	Integer insert(Integer boardId, String userId, String memberId, String host, String guest);

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

	@Select("""
			SELECT
				boardId,
				userId,
				memberId,
			    r.title
			  FROM RunningParty p
			  LEFT JOIN RunningBoard r on r.id = p.boardId
			WHERE userId = #{userId} AND participation = 0 and confirmation = 1
			""")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	List<RunningParty> selectAlarmList(RunningParty runningParty);

	@Select("""
			        SELECT
				count(*)
			  FROM RunningParty
			WHERE boardId = #{boardId} AND userId = #{userId} AND participation = 0;
			""")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	Integer checkAlarm(RunningParty runningParty);

	@Update("""
			UPDATE RunningParty
			SET
				participation = 1
			WHERE
				userId = #{userId} and memberId = #{memberId} and boardId = #{boardId}
			""")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	Integer updateMember(RunningParty runningParty);

	@Update("""
			UPDATE RunningParty
			SET
				participation = 2
			WHERE
				userId = #{userId} and memberId = #{memberId} and boardId = #{boardId}
			""")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	Integer updateMemberDisagree(RunningParty runningParty);

//	@Insert("""
//	        insert into RunningParty (boardId, userId, memberId, host, participation)
//	        values (#{boardId}, #{userId}, #{memberId}, #{host}, 1)
//	        """)
//	Integer makeMate(RunningParty runningParty, String host);

	@Insert("""
			insert into RunningParty (boardId, memberId, userId, host, guest, participation)
			values (#{boardId}, #{userId}, #{userId}, #{host}, #{host}, 1)
			""")
	Integer makeMate(Integer boardId, String userId, String host);

	@Select("""
			SELECT
				p.boardId,
				p.userId,
				p.memberId,
				p.participation,
			    r.title
			  FROM RunningParty p
			  LEFT JOIN RunningBoard r on r.id = p.boardId
			WHERE memberId = #{userId} AND (participation = 1 or participation = 2) and confirmation = 1 ;
			""")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	List<RunningParty> selectMemberAlarmList(RunningParty runningParty);

	@Update("""
			UPDATE RunningParty
			SET
				confirmation = 0
			WHERE
				userId = #{userId} and memberId = #{userId} and boardId = #{boardId}
			""")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	Integer confirmationHost(RunningParty runningParty);

	
	@Update("""
			UPDATE RunningParty
			SET
				confirmation = 0
			WHERE
				userId = #{userId} and memberId = #{memberId} and boardId = #{boardId}
			""")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	Integer confirmationGuest(RunningParty runningParty);

}
