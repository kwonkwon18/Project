package com.example.demo.service;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.springframework.web.multipart.*;

import com.example.demo.domain.*;
import com.example.demo.mapper.*;

import software.amazon.awssdk.core.sync.*;
import software.amazon.awssdk.services.s3.*;
import software.amazon.awssdk.services.s3.model.*;


@Service
@Transactional(rollbackFor = Exception.class)
public class ClimbingTodayService {
	
	@Autowired
	private S3Client s3;
	
	@Value("${aws.s3.bucketName}")
	private String bucketName;
	
	@Autowired
	private ClimbingTodayMapper todayMapper;

	public List<ClimbingToday> listBoard() {
		
		return todayMapper.selectList();
	}


	public boolean addClimbingToday(ClimbingToday climbingToday, MultipartFile[] files) throws Exception {
		
		int cnt = todayMapper.insert(climbingToday);
		for(MultipartFile file : files) {
			if(file.getSize() > 0) {
				String objectKey = "project/climbingToday/" + climbingToday.getId() + "/" + file.getOriginalFilename();
				PutObjectRequest por = PutObjectRequest.builder()
						.acl(ObjectCannedACL.PUBLIC_READ)
						.bucket(bucketName)
						.key(objectKey)
						.build();
				RequestBody rb = RequestBody.fromInputStream(file.getInputStream(), file.getSize());
				
				s3.putObject(por, rb);
				todayMapper.insertFileName(climbingToday.getId(), file.getOriginalFilename());
				
			}
		}
		return cnt == 1;
	}


	public ClimbingToday getBoard(Integer id) {

		return todayMapper.selectById(id);
	}

}