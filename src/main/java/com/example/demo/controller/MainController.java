package com.example.demo.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import com.example.demo.domain.*;
import com.example.demo.service.*;

@Controller
@RequestMapping("/")
public class MainController {

	@Autowired
	private ChatService chatService;
	
	@Autowired
	private MemberService memberService;
	
	@GetMapping({"/", "main"})
	public void main(Authentication authentication, Model model) {
//		if(authentication.isAuthenticated()) {
//			var myName = authentication.getName();
//			List<ChatRoom> invitedList = chatService.invitedSelectByName(myName);
//			List<String> nickNameList = new ArrayList<>();
//			List<String> lastMessageList = new ArrayList<>();
//			for(ChatRoom chatRoom : invitedList) {
//				nickNameList.add(memberService.getNickName(chatRoom.getInvited()));
//				lastMessageList.add(chatService.lastMessageSelectById(chatRoom.getId()));
//			}
//			Map<String, Object> map = new HashMap<>();
//			map.put("nickNameList", nickNameList);
//			map.put("lastMessageList", lastMessageList);
//			model.addAttribute(map);
//		}
	}
}
