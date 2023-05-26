package com.example.demo.service;

import java.io.*;
import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.springframework.web.multipart.*;

import com.example.demo.domain.*;
import com.example.demo.mapper.*;

import ch.qos.logback.core.recovery.*;
import software.amazon.awssdk.core.sync.*;
import software.amazon.awssdk.services.s3.*;
import software.amazon.awssdk.services.s3.model.*;


@Service
@Transactional(rollbackFor = Exception.class)
public class ClimbingCourseService {
	
	@Autowired
	private S3Client s3;
	
	@Value("${aws.s3.bucketName}")
	private String bucketName;
	
	
	@Autowired
	private ClimbingCourseMapper courseMapper;

	public List<ClimbingCourse> listBoard() {
		
		return courseMapper.selectList();
	}


	public boolean addClimbingCourse(ClimbingCourse climbingCourse, MultipartFile[] files) throws Exception {

		int cnt = courseMapper.insert(climbingCourse);
		for(MultipartFile file : files) {
			if(file.getSize() > 0) {
				String objectKey = "project/climbingCourse/" + climbingCourse.getId() + "/" + file.getOriginalFilename();
				PutObjectRequest por = PutObjectRequest.builder()
						.acl(ObjectCannedACL.PUBLIC_READ)
						.bucket(bucketName)
						.key(objectKey)
						.build();
				RequestBody rb = RequestBody.fromInputStream(file.getInputStream(), file.getSize());
				
				s3.putObject(por, rb);
				courseMapper.insertFileName(climbingCourse.getId(), file.getOriginalFilename());
				
			}
		}
		return cnt == 1;
	}

	public ClimbingCourse getBoard(Integer id) {

		return courseMapper.selectById(id);
	}


}