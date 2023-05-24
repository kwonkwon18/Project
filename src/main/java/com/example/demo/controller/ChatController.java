package com.example.demo.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import com.example.demo.service.*;

@Controller
@RequestMapping("chat")
public class ChatController {
	
	@Autowired
	private ChatService service;

	@GetMapping("list")
	public void list() {
		
	}
	
	@GetMapping("room")
	@ResponseBody
	public void chatRoom() {
		
	}
	
}
