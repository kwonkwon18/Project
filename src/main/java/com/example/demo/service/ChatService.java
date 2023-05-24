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
	
	public List<ChatRoom> invitedSelectByName(String myName) {
		return mapper.invitedSelectByCreater(myName);
		
	}

	public String lastMessageSelectById(Integer id) {
		return mapper.lastMessageSelectById(id);
	}

}
