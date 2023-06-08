package com.example.demo.mapper;

import java.time.*;
import java.util.*;

import org.apache.ibatis.annotations.*;

import com.example.demo.domain.*;

@Mapper
public interface ChatMapper {

	@Select("""
			SELECT * FROM ChatRoom
			WHERE creater = #{myName} or invited = #{myName}
			""")
	List<ChatRoom> chatRoomSelectByMyName(String creater);

	@Select("""
			SELECT Message FROM Chat
			WHERE chatRoomId = #{id}
			ORDER BY inserted DESC
			LIMIT 1;
			""")
	String lastMessageSelectById(Integer id);

	@Select("""
			SELECT id FROM ChatRoom
			WHERE (invited = #{myUserId} OR creater = #{myUserId}) AND inserted = #{inserted}
			""")
	Integer getChatRoomId(LocalDateTime inserted, String myUserId);

	@Select("""
			SELECT * FROM Chat
			WHERE chatRoomId = #{chatRoomId}
			""")
	List<Chat> getChatSelectByChatRoomId(int chatRoomId);

	@Insert("""
			INSERT INTO Chat(chatRoomId, senderId, recipientId, message)
			VALUES(#{chatRoomId}, #{senderId}, #{recipientId}, #{message})
			
			""")
	void addChat(Chat data);

	@Select("""
			SELECT recipientId FROM 
			""")
	String getRecipientId(Chat data);

	@Select("""
			SELECT creater, invited FROM ChatRoom
			WHERE id = #{id}
			""")
	Map<String, String> getChatRoomUserId(Integer id);

	@Select("""
			SELECT * FROM Chat
			WHERE chatRoomId = #{chatRoomId} AND id > #{lastChatId}
			""")
	List<Chat> checkId(Integer lastChatId, Integer chatRoomId);

	@Delete("""
			DELETE FROM ChatRoom
			WHERE id = #{chatRoomId} 
			""")
	void chatRoomDeleteByChatRoomId(Integer chatRoomId);

	@Delete("""
			DELETE FROM Chat
			WHERE chatRoomId = #{chatRoomId}
			""")
	void chatDeleteByChatRoomId(Integer chatRoomId);

	@Update("""
			Update ChatRoom
			SET
				creater = null
			WHERE id = #{chatRoomId}
			""")
	void removeChatRoomCreater(Integer chatRoomId);

	@Update("""
			Update ChatRoom
			SET
				invited = null
			WHERE id = #{chatRoomId}
			""")
	void removeChatRoomInvited(Integer chatRoomId);

	@Select("""
			SELECT creater FROM ChatRoom
			WHERE id = #{chatRoomId}
			""")
	String getCreaterByChatRoomId(Integer chatRoomId);

	@Update("""
			UPDATE ChatRoom
			SET
				invitedChatCount = 0
			WHERE
				id = #{chatRoomId}
			""")
	void resetInvitedChatCount(Integer chatRoomId);
	
	@Update("""
			UPDATE ChatRoom
			SET
				createrChatCount = 0
			WHERE
				id = #{chatRoomId}
			""")
	void resetCreaterChatCount(Integer chatRoomId);

	@Select("""
			SELECT inserted FROM Chat
			WHERE chatRoomId = #{chatRoomId}
			""")
	List<LocalDateTime> getinsertedByChatRoomId(int chatRoomId);

	@Select("""
			SELECT IF((creater = {yourId} AND invited = {myId}) OR (creater = {myId} AND invited = {yourId}), 1, 0) FROM ChatRoom
			""")
	int checkChatRoom(String yourId, String myId);

	@Select("""
			SELECT inserted FROM Chat
			WHERE chatRoomId = #{chatRoomId}
			ORDER BY id DESC
			LIMIT 1
			""")
	LocalDateTime getChatLastInserted(Integer chatRoomId);
	

}
