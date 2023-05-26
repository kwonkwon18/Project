package com.example.demo.controller;

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
		List<ChatRoom> invitedList = service.invitedSelectByName(myName);
		List<String> nickNameList = new ArrayList<>();
		List<String> lastMessageList = new ArrayList<>();
		for (ChatRoom chatRoom : invitedList) {
			if(memberService.getNickName(authentication.getName()).equals(chatRoom.getInvited())) {
				nickNameList.add(memberService.getNickName(chatRoom.getCreater()));
			} else {
				nickNameList.add(memberService.getNickName(chatRoom.getInvited()));
			}
			lastMessageList.add(service.lastMessageSelectById(chatRoom.getId()));
		}
		Map<String, Object> map = new HashMap<>();
		map.put("nickNameList", nickNameList);
		map.put("lastMessageList", lastMessageList);
		return map;
	}

	
	@GetMapping("room")
	@ResponseBody
	@PreAuthorize("authenticated")
	public Map<String, Object> chatRoom(Authentication authentication, String yourNickName) {
		
		Map<String, Object> map = new HashMap<>();
		String myUserId = authentication.getName();
		map.put("chatList", service.getChatByYourNickName(yourNickName, myUserId));
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
}
