package com.example.demo.service;

import java.time.*;
import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.example.demo.domain.*;
import com.example.demo.mapper.*;

@Service
public class ChatService {

	@Autowired
	private ChatMapper mapper;
	
	@Autowired
	private MemberMapper memberMapper;
	
	public List<ChatRoom> invitedSelectByName(String myName) {
		return mapper.chatRoomSelectByMyName(myName);
		
	}

	public String lastMessageSelectById(Integer id) {
		return mapper.lastMessageSelectById(id);
	}

	public List<Chat> getChat(LocalDateTime inserted, String myUserId) {
		int chatRoomId = mapper.getChatRoomId(inserted, myUserId);
		
		return mapper.getChatSelectByChatRoomId(chatRoomId);
	}

	public void addChat(Chat data) {
//		for(String s : mapper.getChatRoomUserId(data.getChatRoomId())) {
//			if(!s.equals(data.getSenderId())) {
//				data.setRecipientId(s);
//			}
//		}
		Map<String, String> map = mapper.getChatRoomUserId(data.getChatRoomId());
			if(!map.get("creater").equals(data.getSenderId())) {
				data.setRecipientId(map.get("creater"));
			} else {
				data.setRecipientId(map.get("invited"));
		}
		mapper.addChat(data);
	}

	public List<Chat> checkId(Integer lastChatId, Integer chatRoomId) {
		return mapper.checkId(lastChatId, chatRoomId);
	}

	public void delete(Integer chatRoomId, String myUserId) {
		if(mapper.getChatRoomUserId(chatRoomId).get("creater") == null || mapper.getChatRoomUserId(chatRoomId).get("invited") == null) {
			mapper.chatDeleteByChatRoomId(chatRoomId);
			mapper.chatRoomDeleteByChatRoomId(chatRoomId);
		} else {
			if (mapper.getChatRoomUserId(chatRoomId).get("creater").equals(myUserId)) {
				mapper.removeChatRoomCreater(chatRoomId);
			} else {
				mapper.removeChatRoomInvited(chatRoomId);
			}
		}
	}

	public Integer getChatRoomId(String myUserId, LocalDateTime dateInserted) {
		return mapper.getChatRoomId(dateInserted, myUserId);
	}

	public void resetCount(Integer chatRoomId, String myUserId) {
		if(myUserId.equals(mapper.getCreaterByChatRoomId(chatRoomId))) {
			mapper.resetInvitedChatCount(chatRoomId);
		} else {
			mapper.resetCreaterChatCount(chatRoomId);
		}
		// TODO Auto-generated method stub
		
	}


}
