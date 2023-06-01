package com.example.demo.controller;

import java.time.*;
import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.access.prepost.*;
import org.springframework.security.core.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import com.example.demo.domain.*;
import com.example.demo.service.*;

@Controller
@RequestMapping("chat")
public class ChatController {

	@Autowired
	private ChatService service;

	@Autowired
	private MemberService memberService;

	@GetMapping("list")
	public void list() {

	}

	@GetMapping("open")
	@ResponseBody
	@PreAuthorize("authenticated")
	public Map<String, Object> chatOpen(Authentication authentication) {
		var myName = authentication.getName();
		List<ChatRoom> chatRoomList = service.invitedSelectByName(myName);
		List<String> nickNameList = new ArrayList<>();
		List<String> lastMessageList = new ArrayList<>();
		List<LocalDateTime> insertedList = new ArrayList<>();
		List<String> timeList = new ArrayList<>();
		List<Integer> chatCount = new ArrayList<>();
		List<LocalDateTime> chatInsertedList = new ArrayList<>();
		LocalTime time;
		for (ChatRoom chatRoom : chatRoomList) {
			if(memberService.getNickName(authentication.getName()).equals(chatRoom.getInvited())) {
				nickNameList.add(memberService.getNickName(chatRoom.getCreater()));
				chatCount.add(chatRoom.getCreaterChatCount());
			} else {
				nickNameList.add(memberService.getNickName(chatRoom.getInvited()));
				chatCount.add(chatRoom.getInvitedChatCount());
			}
			if(service.lastMessageSelectById(chatRoom.getId()) == null) {
				lastMessageList.add("");
			} else {
				if(service.lastMessageSelectById(chatRoom.getId()).length() > 15) {
					lastMessageList.add(service.lastMessageSelectById(chatRoom.getId()).substring(0, 13) + "...");
				} else {
					lastMessageList.add(service.lastMessageSelectById(chatRoom.getId()));
				}
			}
			insertedList.add(chatRoom.getInserted());
			chatInsertedList.add(service.getChatLastInserted(chatRoom.getId()));
			time = service.getChatLastInserted(chatRoom.getId()).toLocalTime();
			timeList.add(time.getHour() + ":" + time.getMinute());
		}
		Map<String, Object> map = new HashMap<>();
		map.put("nickNameList", nickNameList);
		map.put("lastMessageList", lastMessageList);
		map.put("insertedList", insertedList);
		map.put("timeList", timeList);
		map.put("chatCount", chatCount);
		map.put("chatInsertedList", chatInsertedList);
		return map;
	}

	
	@GetMapping("room")
	@ResponseBody
	@PreAuthorize("authenticated")
	public Map<String, Object> chatRoom(Authentication authentication, LocalDateTime inserted) {
		
		System.out.println(inserted.getClass().getName());
		System.out.println(authentication.getName());
		Map<String, Object> map = new HashMap<>();
		String myUserId = authentication.getName();
		Integer chatRoomId = service.getChatRoomId(myUserId, inserted);
		service.resetCount(chatRoomId, myUserId);
		map.put("chatList", service.getChat(inserted, myUserId));
		map.put("chatRoomId", chatRoomId);
		map.put("myId", authentication.getName());
		return map;
	}
	
	@PostMapping("add")
	@ResponseBody
	@PreAuthorize("authenticated")
	public void chatAdd(@RequestBody Chat data, Authentication authentication) {
		data.setSenderId(authentication.getName());
		service.addChat(data);
	}
	
	@GetMapping("check")
	@ResponseBody
	@PreAuthorize("authenticated")
	public Map<String, Object> chatCheck(
			@RequestParam("chatRoomId") Integer chatRoomId,
			Integer lastChatId,
			Authentication authentication) {
		Map<String, Object> map = new HashMap<>();
		map.put("chatList", service.checkId(lastChatId, chatRoomId));
		map.put("myUserId", authentication.getName());
		return map;
	}
	
	@GetMapping("deleteRoom/{chatRoomId}")
	@PreAuthorize("authenticated")
	public void deleteRoom(@PathVariable("chatRoomId") Integer chatRoomId,Authentication authentication) {
		service.delete(chatRoomId, authentication.getName());
	}
	
	@GetMapping("roomCheck")
	@ResponseBody
	public Map<String, Object> checkRoom(@RequestBody String yourNickName, Authentication authentication) {
		String yourId = memberService.getUserId(yourNickName);
		boolean check = service.checkChatRoom(yourId, authentication.getName());
		return Map.of("check", check);
	}
}
