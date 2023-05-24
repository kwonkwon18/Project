package com.example.demo.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.domain.RunningBoard;
import com.example.demo.domain.RunningToday;
import com.example.demo.mapper.RunningMapper;
import com.example.demo.mapper.RunningTodayMapper;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

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



}
