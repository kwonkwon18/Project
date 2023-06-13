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
@RequestMapping("groupChat")
public class GroupChatController {
	
	@Autowired
	private GroupChatService service;
	
	@Autowired
	private MemberService memberService;
	
	@GetMapping("groupOpen")
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
			chatInsertedList.add(service.getChatLastInserted(chatRoom.getId()));
			time = service.getChatLastInserted(chatRoom.getId()).toLocalTime();
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
}
