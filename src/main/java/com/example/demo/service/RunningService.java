package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.RunningBoard;
import com.example.demo.mapper.RunningMapper;

@Service
public class RunningService {

	@Autowired
	private RunningMapper mapper;

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

}
