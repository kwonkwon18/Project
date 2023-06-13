package com.example.demo.mapper;

import java.time.*;
import java.util.*;

import org.apache.ibatis.annotations.*;

import com.example.demo.domain.*;

@Mapper
public interface GroupChatMapper {
	
	@Select("""
			SELECT message FROM GroupChatMessage
			WHERE chatRoomId = #{id}
			ORDER BY id DESC
			LIMIT 1
			""")
	String lastMessageSelectById(Integer id);

	@Select("""
			SELECT boardId FROM RunningParty
			WHERE guest = #{myName}
			""")
	List<Integer> getBoardIdByMyName(String myName);

	@Select("""
			SELECT * FROM RuuningBoard
			WHERE id = #{id}
			""")
	RunningBoard groupChatRoomSelectByBoardId(Integer id);

	@Select("""
			SELECT inserted FROM GroupChatMessage
			WHERE chatRoomId = #{id}
			ORDER BY id DESC
			LIMIT 1
			""")
	LocalDateTime getChatLastInserted(Integer id);

	@Select("""
			SELECT SUM(chatCount) FROM RunningParty
			WHERE boardId = #{id} AND guest != #{myUserId}  
			""")
	Integer getChatCount(Integer id, String myUserId);

	
}
