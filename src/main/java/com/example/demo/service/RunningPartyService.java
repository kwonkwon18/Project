package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Member;
import com.example.demo.domain.RunningBoard;
import com.example.demo.domain.RunningParty;
import com.example.demo.mapper.RunningMapper;
import com.example.demo.mapper.RunningPartyMapper;

@Service
public class RunningPartyService {

	@Autowired
	private RunningPartyMapper partyMapper;

	@Autowired
	private RunningMapper mapper;

	public Map<String, Object> join(RunningParty runningParty, Authentication authentication) {

		// 현재 접속한 로그인 아이디 찾기
		Member member = mapper.selectMemberById(authentication.getName());
		
		// 총 인원 파악용
		RunningBoard board = mapper.selectById(runningParty.getBoardId());
		
		// 0일 때와 2일 때는 카운트 해주면 안되므로 수정 해줘야함 
		int currentNum = partyMapper.countByBoardId(runningParty.getBoardId());
		
		Map<String, Object> result = new HashMap<>();

		System.out.println(board.getPeople() + "총인원");
		System.out.println(currentNum + "현재인원");

		if (board.getPeople() >= currentNum) {

			runningParty.setMemberId(member.getNickName());

			System.out.println(runningParty);

			result.put("join", false);

			Integer deleteCnt = partyMapper.delete(runningParty);

			
			
			if (deleteCnt != 1) {
				Integer insertCnt = partyMapper.insert(runningParty);
				result.put("join", true);
			}

			// 참여인원갯수 넘겨주기
			Integer count = partyMapper.countByBoardId(runningParty.getBoardId());
			
			System.out.println("************" + count);

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

	public Map<String, Object> reject(RunningParty runningParty, Authentication authentication) {

		runningParty.setMemberId(authentication.getName());
		System.out.println(runningParty.getMemberId());

		Map<String, Object> result = new HashMap<>();

		result.put("join", false);
		System.out.println("runningParty" + runningParty);
		Integer deleteCnt = partyMapper.delete(runningParty);
		System.out.println("deleteCnt" + deleteCnt);

		// 참여인원갯수 넘겨주기
		Integer count = partyMapper.countByBoardId(runningParty.getBoardId());

		System.out.println(runningParty.getBoardId());
		System.out.println(count);
		result.put("count", count);

		return result;
	}

}
