package com.example.demo.service;

import java.util.HashMap;
import java.util.List;
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

	// 여기서 auth 는 신청하는 사람이 됨
	public Map<String, Object> join(RunningParty runningParty, Authentication authentication) {

		// 현재 접속한 로그인 아이디 찾기
		Member member = mapper.selectMemberById(authentication.getName());

		// 총 인원 파악용
		RunningBoard board = mapper.selectById(runningParty.getBoardId());

		String hostNickName = board.getWriter();
		String host = mapper.findHost(hostNickName);

		int currentNum = partyMapper.countByBoardId(runningParty.getBoardId());
		Integer boardId = runningParty.getBoardId();
		String userId = runningParty.getUserId();
		String memberId = member.getNickName();

		Map<String, Object> result = new HashMap<>();

		System.out.println(board.getPeople() + "총인원");
		System.out.println(currentNum + "현재인원");

		if (board.getPeople() >= currentNum) {

			runningParty.setMemberId(member.getNickName());

			System.out.println(runningParty);

			result.put("join", false);

			Integer deleteCnt = partyMapper.delete(runningParty);
			System.out.println("deleteCnt @!#!@#" + deleteCnt);

			if (deleteCnt != 1) {
				Integer insertCnt = partyMapper.insert(boardId, userId, memberId, host, authentication.getName());
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

	public Map<String, Object> alarm(Authentication authentication) {
//		// 현재 접속한 로그인 아이디 찾기
//		Member member = mapper.selectMemberById(authentication.getName());

		Map<String, Object> result = new HashMap<>();

//		runningParty.setUserId(member.getNickName());

		// System.out.println("%%" + runningParty);

		// 호스트 마이페이지
		List<RunningParty> alarmList = partyMapper.selectAlarmList(authentication.getName());
		result.put("alarmList", alarmList);

		// 게스트 마이페이지
		List<RunningParty> memberAlarmList = partyMapper.selectMemberAlarmList(authentication.getName());
		result.put("memberAlarmList", memberAlarmList);
		System.out.println("^^" + memberAlarmList);

		return result;

	}

	public Integer makeMate(Integer boardId, String userId, Authentication authentication) {
		// TODO Auto-generated method stub
		Integer cnt = partyMapper.makeMate(boardId, userId, authentication.getName());
		return cnt;
	}

	public Map<String, Object> agreeParty(RunningParty runningParty, Authentication authentication) {

		Map<String, Object> result = new HashMap<>();
		// System.out.println("접근 1");
		Integer agreeMember = partyMapper.updateMember(runningParty);
		// System.out.println("접근 2");
		Integer confirmation = partyMapper.confirmationHost(runningParty);

		System.out.println(runningParty);

		if (agreeMember == 1) {
			result.put("join", true);
			return result;
		} else {
			result.put("join", false);
			return result;
		}

	}

	public Map<String, Object> disagreeParty(RunningParty runningParty, Authentication authentication) {
		Map<String, Object> result = new HashMap<>();

		Integer agreeMember = partyMapper.updateMemberDisagree(runningParty);
		System.out.println("^^^^" + runningParty);

		Integer confirmation = partyMapper.confirmationHost(runningParty);

		System.out.println(runningParty);

		if (agreeMember == 1) {
			result.put("out", true);
			return result;
		} else {
			result.put("out", false);
			return result;
		}
	}

	public Map<String, Object> confirmation(RunningParty runningParty, Authentication authentication) {
		Map<String, Object> result = new HashMap<>();
		System.out.println("asdjfasdklf" + runningParty);
		Integer confirmation = partyMapper.confirmationGuest(runningParty);

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
		
		Integer confirmationHost = partyMapper.countOfAlarmHost(authentication.getName()) ;
		
		

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

//	public Map<String, Object> confirmationBad(RunningParty runningParty, Authentication authentication) {
//		Map<String, Object> result = new HashMap<>();
//
//		Integer agreeMember = partyMapper.updateMemberDisagree(runningParty);
//
//		Integer confirmation = partyMapper.confirmation(runningParty);
//
//		System.out.println(runningParty);
//
//		if (agreeMember == 1) {
//			result.put("out", true);
//			return result;
//		} else {
//			result.put("out", false);
//			return result;
//		}
//	}
//	

	/*
	 * public Map<String, Object> reject(RunningParty runningParty, Authentication
	 * authentication) {
	 * 
	 * runningParty.setMemberId(authentication.getName());
	 * System.out.println(runningParty.getMemberId());
	 * 
	 * Map<String, Object> result = new HashMap<>();
	 * 
	 * result.put("join", false); System.out.println("runningParty" + runningParty);
	 * Integer deleteCnt = partyMapper.delete(runningParty);
	 * System.out.println("deleteCnt" + deleteCnt);
	 * 
	 * // 참여인원갯수 넘겨주기 Integer count =
	 * partyMapper.countByBoardId(runningParty.getBoardId());
	 * 
	 * System.out.println(runningParty.getBoardId()); System.out.println(count);
	 * result.put("count", count);
	 * 
	 * return result; }
	 */

}
