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
import com.example.demo.mapper.RunningTodayMapper;

@Service
public class RunningService {

	@Autowired
	private RunningMapper mapper;

	@Autowired
	private RunningTodayMapper todayMapper;

	public boolean addBoard(RunningBoard runningBoard, Authentication authentication) {
		
		Member member = mapper.selectMemberById(authentication.getName());
		runningBoard.setWriter(member.getNickName());
		
		int cnt = mapper.insert(runningBoard);
		return cnt == 1;
	}

	public List<RunningBoard> listBoard() {

		return mapper.selectList();
	}

	public RunningBoard getBoard(Integer id) {

		return mapper.selectById(id);
	}

	public List<RunningBoard> getMyPageInfo(String writer) {
		
		return mapper.selectMyPageInfo(writer);
	}

	public List<RunningParty> getJoinMember(String writer) {
		
		return mapper.selectMemberId(writer);
	}

	public List<RunningBoard> getMateBoard() {
		
		return mapper.selectMate();
	}

	public List<RunningParty> selectMemberIdByBoardId(Integer id, String writer) {
		
		return mapper.selectMemberIdByBoardId(id, writer);
	}

	public List<RunningParty> selectMemberIdByBoardId() {
		// TODO Auto-generated method stub
		return mapper.selectMember();
	}

	public List<Member> getUserId(String userId) {
		// TODO Auto-generated method stub
		return  mapper.selectUserId(userId);
	}

	public Map<String, Object> getBoardForModal(Integer boardId, Authentication authentication) {
		
		Map<String, Object> getMemberList = new HashMap<>();

		// board에 대한 정보가 들어감 
		RunningBoard getList = mapper.selectById(boardId);
		getMemberList.put("board", getList);

		
		// 신청자가 들어감
		List<RunningParty> members = mapper.selectForMemberIdByBoardId(boardId);
		getMemberList.put("members", members);
		
		
		// 중복 신청 방지용
		List<Member> memberList = getUserId(authentication.getName());
		getMemberList.put("memberList", memberList);
		
		
		// 로그인한 사람 확인용 (닉네임)
		Member myNickName = mapper.getNickName(authentication.getName());
		getMemberList.put("myNickName", myNickName);
		
		return getMemberList;
	}



}
