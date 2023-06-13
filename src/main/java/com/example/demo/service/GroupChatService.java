package com.example.demo.service;

import java.time.*;
import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.example.demo.domain.*;
import com.example.demo.mapper.*;

@Service
public class GroupChatService {

	@Autowired
	private GroupChatMapper mapper;
	
	public List<RunningBoard> groupChatRoomSelectByName(String myName) {
		List<RunningBoard> rList = new ArrayList<>();
		List<Integer> list = mapper.getBoardIdByMyName(myName);
		for(Integer i : list) {
			rList.add(mapper.groupChatRoomSelectByBoardId(i));
		}
		return rList;
	}

	public String lastMessageSelectById(Integer id) {
		return mapper.lastMessageSelectById(id);
	}

	public LocalDateTime getChatLastInserted(Integer id) {
		return mapper.getChatLastInserted(id);
	}

	public Integer getChatCount(Integer id, String myUserId) {
		return mapper.getChatCount(id, myUserId);
	}

}
