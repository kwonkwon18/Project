package com.example.demo.controller;

import java.time.*;
import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.access.prepost.*;
import org.springframework.security.core.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;

import com.example.demo.domain.*;
import com.example.demo.service.*;

@Controller
@RequestMapping("groupChat")
public class GroupChatController {
	
	@Autowired
	private GroupChatService service;
	
	@Autowired
	private MemberService memberService;
	
	@GetMapping("open")
	@ResponseBody
	@PreAuthorize("authenticated")
	public Map<String, Object> groupChatOpen(Authentication authentication) {
		var myName = authentication.getName();
		List<RunningBoard> chatRoomList = service.groupChatRoomSelectByName(myName);
		List<String> titleList = new ArrayList<>();
		List<String> lastMessageList = new ArrayList<>();
		List<LocalDateTime> insertedList = new ArrayList<>();
		List<String> timeList = new ArrayList<>();
		List<Integer> chatCount = new ArrayList<>();
		List<LocalDateTime> chatInsertedList = new ArrayList<>();
		LocalTime time;
		for (RunningBoard chatRoom : chatRoomList) {
			chatCount.add(service.getChatCount(chatRoom.getId(), authentication.getName()));
			titleList.add(chatRoom.getTitle());
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
			if(service.getChatLastInserted(chatRoom.getId()) != null) {
				chatInsertedList.add(service.getChatLastInserted(chatRoom.getId()));
				time = service.getChatLastInserted(chatRoom.getId()).toLocalTime();
			} else {
				time = LocalTime.now();
			}
			timeList.add(time.getHour() + ":" + time.getMinute());
		}
		Map<String, Object> map = new HashMap<>();
		map.put("lastMessageList", lastMessageList);
		map.put("insertedList", insertedList);
		map.put("timeList", timeList);
		map.put("chatCount", chatCount);
		map.put("chatInsertedList", chatInsertedList);
		map.put("titleList", titleList);
		return map;
	}
	
	@GetMapping("room")
	@ResponseBody
	public Map<String, Object> groupChatRoom(Authentication authentication, LocalDateTime inserted) {
		Map<String, Object> map = new HashMap<>();
		String myUserId = authentication.getName();
		Integer chatRoomId = service.getChatRoomId(inserted);
		service.resetCount(chatRoomId, myUserId);
		map.put("chatList", service.getChat(chatRoomId));
		map.put("chatRoomId", chatRoomId);
		map.put("myId", myUserId);
		return map;
	}
	
	@GetMapping("check")
	@ResponseBody
	public Map<String, Object> chatCheck(
			@RequestParam("chatRoomId") Integer chatRoomId,
			Integer lastChatId,
			Authentication authentication) {
		Map<String, Object> map = new HashMap<>();
		map.put("chatList", service.checkId(lastChatId, chatRoomId));
		map.put("myUserId", authentication.getName());
		map.put("lastSenderId", service.lastSenderIdSelectByChatRoomId(chatRoomId));
		return map;
	}
	
	@PostMapping("add")
	@ResponseBody
	public void chatAdd(
			@RequestParam String message,
			@RequestParam Integer chatRoomId,
			@RequestParam(required = false) MultipartFile[] files, 
			Authentication authentication) throws Exception {
		GroupChatMessage chat = new GroupChatMessage(); 
		chat.setSenderId(authentication.getName());
		chat.setMessage(message);
		chat.setChatRoomId(chatRoomId);
		if(files != null) {
			service.addFiles(chat, files);
		} else {
			service.addChat(chat);
		}
	}
	
	@GetMapping("deleteRoom/{chatRoomId}")
	@PreAuthorize("authenticated")
	public void deleteRoom(@PathVariable("chatRoomId") Integer chatRoomId,Authentication authentication) {
		service.delete(chatRoomId, authentication.getName());
	}
	
	@GetMapping("findRoom")
	@ResponseBody
	public Map<String, Object> findRoom(Authentication authentication, @RequestParam String search) {
		var myName = authentication.getName();
		List<RunningBoard> chatRoomList = service.findRoomSelectBySearch(search, authentication.getName());
		List<String> titleList = new ArrayList<>();
		List<String> lastMessageList = new ArrayList<>();
		List<LocalDateTime> insertedList = new ArrayList<>();
		List<String> timeList = new ArrayList<>();
		List<Integer> chatCount = new ArrayList<>();
		List<LocalDateTime> chatInsertedList = new ArrayList<>();
		LocalTime time;
		for (RunningBoard chatRoom : chatRoomList) {
				chatCount.add(service.getChatCount(chatRoom.getId(), myName));
			if(service.lastMessageSelectById(chatRoom.getId()) == null) {
				lastMessageList.add("");
			} else {
				if(service.lastMessageSelectById(chatRoom.getId()).length() > 15) {
					lastMessageList.add(service.lastMessageSelectById(chatRoom.getId()).substring(0, 13) + "...");
				} else {
					lastMessageList.add(service.lastMessageSelectById(chatRoom.getId()));
				}
			}
			titleList.add(chatRoom.getTitle());
			insertedList.add(chatRoom.getInserted());
			if(service.getChatLastInserted(chatRoom.getId()) == null) {
				chatInsertedList.add(LocalDateTime.now());
			} else {
				chatInsertedList.add(service.getChatLastInserted(chatRoom.getId()));
				time = service.getChatLastInserted(chatRoom.getId()).toLocalTime();
			}
			time = LocalTime.now();
			timeList.add(time.getHour() + ":" + time.getMinute());
		}
		Map<String, Object> map = new HashMap<>();
		map.put("titleList", titleList);
		map.put("lastMessageList", lastMessageList);
		map.put("insertedList", insertedList);
		map.put("timeList", timeList);
		map.put("chatCount", chatCount);
		map.put("chatInsertedList", chatInsertedList);
		return map;
	}
}
