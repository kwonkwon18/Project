package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.domain.RunningBoard;
import com.example.demo.domain.RunningToday;
import com.example.demo.mapper.RunningTodayMapper;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class RunningTodayService {
	
	@Autowired
	private S3Client s3;

	@Value("${aws.s3.bucketName}")
	private String bucketName;
	
	@Autowired
	private RunningTodayMapper todayMapper;

	public List<RunningToday> listBoard() {
		
		return todayMapper.selectList();

	}
	
	public boolean addRunningToday(RunningToday runningToday, MultipartFile[] files) throws Exception {

		Integer cnt = todayMapper.insertRunningToday(runningToday);

		for (MultipartFile file : files) {
			if (file.getSize() > 0) {

				// 이름이 될 내용
				String objectKey = "project/" + runningToday.getId() + "/" + file.getOriginalFilename();

				// s3 첫번째 파라미터
				PutObjectRequest por = PutObjectRequest.builder().bucket(bucketName).key(objectKey)
						.acl(ObjectCannedACL.PUBLIC_READ).build();

				// s3 두번째 파라미터
				RequestBody rb = RequestBody.fromInputStream(file.getInputStream(), file.getSize());

				s3.putObject(por, rb);

				todayMapper.insertFileName(runningToday.getId(), file.getOriginalFilename());
			}
		}

		return cnt == 1;

	}

	public RunningToday getBoard(Integer id) {
		
		return todayMapper.selectById(id);
	}
}
