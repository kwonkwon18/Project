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
		int chatRoomId = mapper.getChatRoomIdByInserted(inserted, myUserId);
		List<Chat> list = mapper.getChatSelectByChatRoomId(chatRoomId);
		List<LocalDateTime> dateTimeList = mapper.getinsertedByChatRoomId(chatRoomId);
		for(int i = 0; i < dateTimeList.size(); i++) {
			LocalTime time = dateTimeList.get(i).toLocalTime();
			list.get(i).setTime(time.getHour() + ":" + time.getMinute());
		}
		return list;
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
		List<Chat> list = mapper.checkId(lastChatId, chatRoomId);
		for(int i = 0; i < list.size(); i++) {
			LocalDateTime dateTime = list.get(i).getInserted();
			String time = dateTime.getHour() + ":" + dateTime.getMinute();
			list.get(i).setTime(time);
		}
		return list;
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
		return mapper.getChatRoomIdByInserted(dateInserted, myUserId);
	}

	public void resetCount(Integer chatRoomId, String myUserId) {
		if(myUserId.equals(mapper.getCreaterByChatRoomId(chatRoomId))) {
			mapper.resetInvitedChatCount(chatRoomId);
		} else {
			mapper.resetCreaterChatCount(chatRoomId);
		}
		// TODO Auto-generated method stub
		
	}

	public boolean checkChatRoom(String yourId, String myId) {
		int cnt = 0;
		for(int i : mapper.checkChatRoom(yourId, myId)) {
			cnt += i;
		}
		return cnt == 1;
	}

	public LocalDateTime getChatLastInserted(Integer id) {
		if(mapper.getChatLastInserted(id) == null) {
			LocalDateTime time = LocalDateTime.now();
			return time;
		} else {
			return mapper.getChatLastInserted(id);
		}
	}

	public List<Chat> getChat(String myId, String yourId) {
		Integer chatRoomId = mapper.getChatRoomIdByYourId(myId, yourId);
		List<Chat> list = mapper.getChatSelectByChatRoomId(chatRoomId);
		List<LocalDateTime> dateTimeList = mapper.getinsertedByChatRoomId(chatRoomId);
		for(int i = 0; i < dateTimeList.size(); i++) {
			LocalTime time = dateTimeList.get(i).toLocalTime();
			list.get(i).setTime(time.getHour() + ":" + time.getMinute());
		}
		return list;
	}

	public Integer getChatRoomId(String yourId, String myId) {
		return mapper.getChatRoomIdByYourId(myId, yourId);
	}

	public void createChatRoom(String myId, String yourNickName) {
		System.out.println(yourNickName);
		String yourId = memberMapper.getUserIdSelectByNickName(yourNickName);
		System.out.println(yourId);
		mapper.createChatRoom(myId, yourId);
		
	}


}
