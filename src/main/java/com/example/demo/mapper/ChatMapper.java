package com.example.demo.mapper;

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
			WHERE (invited = #{yourUserId} AND creater = #{myUserId}) OR (invited = #{myUserId} AND creater = #{yourUserId})
			""")
	Integer getChatRoomId(String yourUserId, String myUserId);

	@Select("""
			SELECT * FROM Chat
			WHERE chatRoomId = #{chatRoomId}
			""")
	List<Chat> getChatSelectByChatRoomId(int chatRoomId);

	@Insert("""
			INSERT INTO Chat(chatRoomId, senderId, recipientId, message)
			VALUES(#{data.getChatRoomId}, #{data.getSenderId}, #{data.getRecipientId}, #{data.getMessage})
			
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
	String[] getChatRoomUserId(Integer id);


}
