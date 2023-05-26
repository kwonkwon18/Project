package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public boolean addBoard(RunningBoard runningBoard) {
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



}
