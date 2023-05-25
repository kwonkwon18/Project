package com.example.demo.service;

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

	public List<Chat> getChatByYourNickName(String yourNickName, String myUserId) {
		String yourUserId = memberMapper.getUserIdSelectByNickName(yourNickName);
		
		int chatRoomId = mapper.getChatRoomId(yourUserId, myUserId);
		
		return mapper.getChatSelectByChatRoomId(chatRoomId);
	}

	public void addChat(Chat data) {
		for(String s : mapper.getChatRoomUserId(data.getChatRoomId())) {
			if(!s.equals(data.getSenderId())) {
				data.setRecipientId(s);
			}
		}
		mapper.addChat(data);
	}


}
