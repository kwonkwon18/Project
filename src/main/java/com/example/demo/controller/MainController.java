package com.example.demo.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	
	//  서재권 작업 내용***********************
	@GetMapping("login")
	public void loginForm() {

	}
	

	@GetMapping("signup")
	public void signupForm() {

	}

	@PostMapping("signup")
	public String signupProcess(Member member, RedirectAttributes rttr) {

		try {
			memberService.signup(member);
			// 정보 제공을 위한 것 
			rttr.addFlashAttribute("member", member);
			// alert를 위한 것 
			rttr.addFlashAttribute("message", "회원 가입되었습니다 ⭕⭕");
			return "redirect:/login";

		} catch (Exception e) {
			e.printStackTrace();
			rttr.addFlashAttribute("member", member);
			rttr.addFlashAttribute("message", "회원 가입 실패 ❌❌");
			return "redirect:/login";
		}
	}
	
	
	
	
	
	
	
}
