package com.example.demo.service;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.*;
import org.springframework.stereotype.*;

import com.example.demo.domain.*;
import com.example.demo.mapper.*;


@Service
public class FutsalPartyService {

	@Autowired
	private FutsalPartyMapper partyMapper;

	@Autowired
	private FutsalMapper mapper;

	public Map<String, Object> join(FutsalParty futsalParty, Authentication authentication) {

		
		Member member = mapper.selectMemberById(authentication.getName());
		

		FutsalBoard board = mapper.selectById(futsalParty.getBoardId());
		int currentNum = partyMapper.countByBoardId(futsalParty.getBoardId());

		Map<String, Object> result = new HashMap<>();

		System.out.println(board.getPeople() + "총인원");
		System.out.println(currentNum + "현재인원");

		if (board.getPeople() >= currentNum) {

			futsalParty.setMemberId(member.getNickName());

			System.out.println(futsalParty);

			result.put("join", false);

			Integer deleteCnt = partyMapper.delete(futsalParty);

			if (deleteCnt != 1) {
				Integer insertCnt = partyMapper.insert(futsalParty);
				result.put("join", true);
			}

			// 참여인원갯수 넘겨주기
			Integer count = partyMapper.countByBoardId(futsalParty.getBoardId());

			result.put("count", count);
			System.out.println("****" + result);
			return result;

		} else {
			// 신청 불가능한 경우
			result.put("join", false);
			result.put("message", "신청이 불가능합니다."); // 메시지 추가
			System.out.println(result);
			return result;
		}
		
		

	}

	public Map<String, Object> reject(FutsalParty futsalParty, Authentication authentication) {

		futsalParty.setMemberId(authentication.getName());
		System.out.println(futsalParty.getMemberId());

		Map<String, Object> result = new HashMap<>();

		result.put("join", false);
		System.out.println("futsalParty" + futsalParty);
		Integer deleteCnt = partyMapper.delete(futsalParty);
		System.out.println("deleteCnt" + deleteCnt);

		// 참여인원갯수 넘겨주기
		Integer count = partyMapper.countByBoardId(futsalParty.getBoardId());

		System.out.println(futsalParty.getBoardId());
		System.out.println(count);
		result.put("count", count);

		return result;
	}

}
