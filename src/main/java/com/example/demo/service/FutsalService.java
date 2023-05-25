package com.example.demo.service;

import java.io.*;
import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;

import com.example.demo.domain.*;
import com.example.demo.mapper.*;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.*;
import software.amazon.awssdk.services.s3.model.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class FutsalService {

	@Autowired
	private S3Client s3;
	
	@Value("{aws.s3.bucketName}")
	private String bucketName;
	
	@Autowired
	private FutsalMapper mapper;
	
	public List<FutsalBoard> listBoard() {
		List<FutsalBoard> list = mapper.selectAll();
		return list;
	}
	
	public FutsalBoard getFutsalBoard(Integer id) {
		FutsalBoard board = mapper.selectById(id);
		return board;
	}

	public boolean addBoard(FutsalBoard futsalBoard, MultipartFile[] files) throws Exception {
		
		// 게시물 insert
		int cnt = mapper.insert(futsalBoard);
		
			for(MultipartFile file : files) {
				if (file.getSize() > 0) {
					String objectKey = "project/" + futsalBoard.getId() + "/" + file.getOriginalFilename();
					
				PutObjectRequest por = PutObjectRequest.builder()
						.bucket(bucketName)
						.key(objectKey)
						.acl(ObjectCannedACL.PUBLIC_READ)
						.build();
				RequestBody rb = RequestBody.fromInputStream(file.getInputStream(), file.getSize());
				
				// db에 관련 정보 저장(insert)
				mapper.insertFileName(futsalBoard.getId(), file.getOriginalFilename());
				
				s3.putObject(por, rb);
				}
				
			}
		
		return cnt == 1;
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

	public boolean modify(FutsalBoard futsalBoard) {
		int cnt = mapper.update(futsalBoard);
		return cnt == 1;
	}

	public boolean remove(Integer id) {
		int cnt = mapper.deleteById(id);
		
		return cnt == 1;
	}


}
