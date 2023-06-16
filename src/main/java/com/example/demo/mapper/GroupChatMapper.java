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
			WHERE guest = #{myName} AND participation = 1;
			""")
	List<Integer> getBoardIdByMyName(String myName);

	@Select("""
			SELECT * FROM RunningBoard
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
			SELECT chatCount FROM RunningParty
			WHERE boardId = #{id} AND guest = #{myUserId}
			""")
	Integer getChatCount(Integer id, String myUserId);

	@Update("""
			UPDATE RunningParty
			SET
				chatCount = 0
			WHERE
				boardId = #{chatRoomId} AND guest = #{myUserId}
			""")
	void resetCount(Integer chatRoomId, String myUserId);

	@Select("""
			SELECT id FROM RunningBoard
			WHERE inserted = #{inserted}
			""")
	Integer getChatRoomId(LocalDateTime inserted);

	@Select("""
			SELECT * FROM GroupChatMessage
			WHERE chatRoomId = #{chatRoomId}
			""")
	List<GroupChatMessage> getChatSelectByChatRoomId(Integer chatRoomId);

	@Select("""
			SELECT * FROM GroupChatMessage
			WHERE id > #{lastChatId} AND chatRoomId = #{chatRoomId}
			""")
	List<GroupChatMessage> checkId(Integer lastChatId, Integer chatRoomId);

	@Insert("""
			INSERT INTO GroupChatMessage(chatRoomId, senderId, message)
			VALUES(#{chatRoomId}, #{senderId}, #{message})
			""")
	void addChat(GroupChatMessage chat);

	@Insert("""
			INSERT INTO GroupChatMessage(chatRoomId, senderId, message, fileName)
			VALUES(#{chatRoomId}, #{senderId}, #{message}, #{fileName})
			""")
	void insertFileChat(GroupChatMessage chat);

	@Select("""
			SELECT COUNT(guest) FROM RunningParty
			WHERE boardId = #{chatRoomId}
			""")
	Integer countMember(Integer chatRoomId);

	@Delete("""
			DELETE FROM GroupChatMessage
			WHERE chatRoomId = #{chatRoomId}
			""")
	void chatDeleteByChatRoomId(Integer chatRoomId);

	@Delete("""
			DELETE FROM RunningParty
			WHERE boardId = #{chatRoomId}
			""")
	void runningPartyDeleteByChatRoomId(Integer chatRoomId);

	@Delete("""
			DELETE FROM RunningBoard
			WHERE id = #{chatRoomId}
			""")
	void chatRoomDeleteByChatRoomId(Integer chatRoomId);

	@Delete("""
			DELETE FROM RunningParty
			WHERE boardId = #{chatRoomId} AND guest = #{myUserId}
			""")
	void runningPartyDeleteMemberByChatRoomId(Integer chatRoomId, String myUserId);

	@Select("""
			SELECT host FROM RunningParty
			WHERE boardId = #{chatRoomId}
			LIMIT 1
			""")
	String getHostByChatRoomId(Integer chatRoomId);

	@Select("""
			SELECT id FROM RunningBoard
			WHERE title LIKE CONCAT('%', #{search}, '%')
			""")
	List<Integer> boardIdSelectBySearch(String search);

	@Select("""
			SELECT boardId FROM RunningParty
			WHERE boardId = #{id} AND guest = #{myId} AND participation = 1;
			""")
	Integer getMyRoom(int id, String myId);

	@Select("""
			SELECT * FROM RunningBoard
			WHERE id = #{i}
			""")
	RunningBoard getRunningBoard(int i);

	@Select("""
			SELECT senderId FROM GroupChatMessage
			WHERE chatRoomId = #{chatRoomId}
			ORDER BY id DESC
			LIMIT 1 OFFSET 1
			""")
	String lastSenderIdSelectByChatRoomId(Integer chatRoomId);

	@Select("""
			SELECT memberId FROM RunningParty
			WHERE boardId = #{lastChatRoomId}
			""")
	List<String> getMemberByChatRoomId(Integer lastChatRoomId);

	@Select("""
			SELECT id FROM GroupChatMessage
			WHERE message LIKE CONCAT('%', #{search}, '%') AND chatRoomId = #{chatRoomId}
			""")
	List<Integer> searchGroupChatId(String search, Integer chatRoomId);

}
