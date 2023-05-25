package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.RunningBoard;
import com.example.demo.domain.RunningParty;
import com.example.demo.mapper.RunningPartyMapper;

@Service
public class RunningPartyService {
	
	@Autowired
	private RunningPartyMapper partyMapper;

	public Map<String, Object> join(RunningParty runningParty) {
		
		 
		//runningParty.setMemberId("test");
		//runningParty.setMemberId("test2");
		 runningParty.setMemberId("test3");
		 
		
		Map<String, Object> result = new HashMap<>();
		
		result.put("join", false);
		
		Integer deleteCnt = partyMapper.delete(runningParty);
		
		if(deleteCnt != 1) {
			Integer insertCnt = partyMapper.insert(runningParty);
			result.put("join", true);
		}
		
		// 참여인원갯수 넘겨주기
		Integer count = partyMapper.countByBoardId(runningParty.getBoardId());
		result.put("count", count);
		
		return result;
	}



	



}
