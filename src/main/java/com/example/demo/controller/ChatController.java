package com.example.demo.controller;

import java.time.*;
import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.access.prepost.*;
import org.springframework.security.core.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
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
		for (ChatRoom chatRoom : chatRoomList) {
			if(memberService.getNickName(authentication.getName()).equals(chatRoom.getInvited())) {
				nickNameList.add(memberService.getNickName(chatRoom.getCreater()));
			} else {
				nickNameList.add(memberService.getNickName(chatRoom.getInvited()));
			}
			if(service.lastMessageSelectById(chatRoom.getId()) == null) {
				lastMessageList.add("");
			} else {
				lastMessageList.add(service.lastMessageSelectById(chatRoom.getId()));
			}
			insertedList.add(chatRoom.getInserted());
		}
		Map<String, Object> map = new HashMap<>();
		map.put("nickNameList", nickNameList);
		map.put("lastMessageList", lastMessageList);
		map.put("insertedList", insertedList);
		return map;
	}

	
	@GetMapping("room")
	@ResponseBody
	@PreAuthorize("authenticated")
	public Map<String, Object> chatRoom(Authentication authentication, LocalDateTime inserted) {
		
		System.out.println(inserted);
		System.out.println(authentication.getName());
		Map<String, Object> map = new HashMap<>();
		String myUserId = authentication.getName();
		map.put("chatList", service.getChatByYourNickName(inserted, myUserId));
		map.put("chatRoomId", service.getChatRoomId(myUserId, inserted));
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
	public Map<String, Object> chatCheck(@RequestParam("chatRoomId") Integer chatRoomId, Integer lastChatId) {
		return Map.of("chatList", service.checkId(lastChatId, chatRoomId));
		
		
	}
	
	@GetMapping("deleteRoom/{chatRoomId}")
	@PreAuthorize("authenticated")
	public void deleteRoom(@PathVariable("chatRoomId") Integer chatRoomId,Authentication authentication) {
		service.delete(chatRoomId, authentication.getName());
	}
}
