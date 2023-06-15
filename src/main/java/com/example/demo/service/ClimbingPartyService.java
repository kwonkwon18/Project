package com.example.demo.service;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.*;
import org.springframework.stereotype.*;

import com.example.demo.domain.*;
import com.example.demo.mapper.*;

@Service
public class ClimbingPartyService {

	@Autowired
	private ClimbingPartyMapper partyMapper;

	@Autowired
	private ClimbingMateMapper mateMapper;

	
	public Map<String, Object> join(ClimbingParty climbingParty, Authentication authentication) {

		Member member = partyMapper.selectMemberById(authentication.getName());
		
		ClimbingMate board = mateMapper.selectById(climbingParty.getBoardId());
		int currentNum = partyMapper.countByBoardId(climbingParty.getBoardId());

		Map<String, Object> result = new HashMap<>();

		System.out.println(board.getPeople() + "총인원");
		System.out.println(currentNum + "현재인원");

		if (board.getPeople() > currentNum) {

			climbingParty.setMemberId(member.getNickName());

			System.out.println(climbingParty);

			result.put("join", false);

			Integer deleteCnt = partyMapper.delete(climbingParty);

			if (deleteCnt != 1) {
				Integer insertCnt = partyMapper.insert(climbingParty);
				result.put("join", true);
			}

			// 참여인원갯수 넘겨주기
			Integer count = partyMapper.countByBoardId(climbingParty.getBoardId());

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

	public Map<String, Object> reject(ClimbingParty climbingParty, Authentication authentication) {

		climbingParty.setMemberId(authentication.getName());
		System.out.println(climbingParty.getMemberId());

		Map<String, Object> result = new HashMap<>();

		result.put("join", false);

		Integer deleteCnt = partyMapper.delete(climbingParty);
		System.out.println(deleteCnt);

		// 참여인원갯수 넘겨주기
		Integer count = partyMapper.countByBoardId(climbingParty.getBoardId());

		System.out.println(climbingParty.getBoardId());
		System.out.println(count);
		result.put("count", count);

		return result;
	}

	public Map<String, Object> alarm(ClimbingParty climbingParty, Authentication authentication) {
		// 현재 접속한 로그인 아이디 찾기
		Member member = mateMapper.selectMemberById(authentication.getName());
		
		Map<String, Object> result = new HashMap<>();

		climbingParty.setUserId(member.getNickName());

		// System.out.println("%%" + runningParty);

		// 호스트 마이페이지 
		List<ClimbingParty> alarmList = partyMapper.selectAlarmList(climbingParty);
		result.put("alarmList", alarmList);
		
		// 게스트 마이페이지
		List<ClimbingParty> memberAlarmList = partyMapper.selectMemberAlarmList(climbingParty);
		result.put("memberAlarmList", memberAlarmList);

		return result;
	}

	public Map<String, Object> agreeParty(ClimbingParty climbingParty, Authentication authentication) {
		Map<String, Object> result = new HashMap<>();
		// System.out.println("접근 1");
		Integer agreeMember = partyMapper.updateMember(climbingParty);
		// System.out.println("접근 2");

		System.out.println(climbingParty);

		if (agreeMember == 1) {
			result.put("join", true);
			return result;
		} else {
			result.put("join", false);
			return result;
		}
	}

	public Map<String, Object> disagreeParty(ClimbingParty climbingParty, Authentication authentication) {
		Map<String, Object> result = new HashMap<>();

		Integer agreeMember = partyMapper.updateMemberDisagree(climbingParty);

		System.out.println(climbingParty);

		if (agreeMember == 1) {
			result.put("out", true);
			return result;
		} else {
			result.put("out", false);
			return result;
		}
	}

	public Map<String, Object> confirmation(ClimbingParty climbingParty, Authentication authentication) {
		Map<String, Object> result = new HashMap<>();
		Integer confirmation = partyMapper.confirmationGuest(climbingParty);

		if (confirmation == 1) {
			result.put("confirmation", true);
			return result;
		} else {
			result.put("confirmation", false);
			return result;
		}
	}

	public Map<String, Object> countOfAlarm(Authentication authentication) {
		Map<String, Object> result = new HashMap<>();
		
		// host와 guest
		
		// 1 빼줄수 있는데.. 봐야함 
		Integer confirmationHost = partyMapper.countOfAlarmHost(authentication.getName());

		if (confirmationHost < 0) {
			confirmationHost = 0;
		}

		Integer confirmationGuest = partyMapper.countOfAlarmGuest(authentication.getName());
		if (confirmationGuest < 0) {
			confirmationGuest = 0;
		}
		
		
		System.out.println("confirmationGuest == " + confirmationGuest);
		Integer confirmationTotal = confirmationHost + confirmationGuest;

		result.put("confirmationTotal", confirmationTotal);

		return result;
	}

}
