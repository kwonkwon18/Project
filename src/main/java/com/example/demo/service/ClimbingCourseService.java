package com.example.demo.service;

import java.io.*;
import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.*;
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

	public List<ClimbingCourse> listBoard(String courseSearch) {
		
		return courseMapper.selectListByCourseSearch(courseSearch);
	}
	
	public ClimbingCourse getClimbingCourse(Integer id) {

		return courseMapper.selectById(id);
	}


	public boolean addClimbingCourse(ClimbingCourse climbingCourse, MultipartFile[] files, Authentication authentication) throws Exception {

		Member member = courseMapper.selectMemberById(authentication.getName());
		climbingCourse.setWriter(member.getNickName());
		
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




	public boolean modify(ClimbingCourse climbingCourse, MultipartFile[] addFiles, List<String> removeFileNames) throws Exception {

			// FileName 테이블 삭제
			if (removeFileNames != null && !removeFileNames.isEmpty()) {
				for (String fileName : removeFileNames) {
					// s3에서 파일(객체) 삭제
					String objectKey = "project/climbingCourse/" + climbingCourse.getId() + "/" + fileName;
					DeleteObjectRequest dor = DeleteObjectRequest.builder()
							.bucket(bucketName)
							.key(objectKey)
							.build();
					s3.deleteObject(dor);

					// 테이블에서 삭제
					courseMapper.deleteFileNameByBoardIdAndFileName(climbingCourse.getId(), fileName);
				}
			}

			// 새 파일 추가
			for (MultipartFile newFile : addFiles) {
				if (newFile.getSize() > 0) {
					// 테이블에 파일명 추가
					courseMapper.insertFileName(climbingCourse.getId(), newFile.getOriginalFilename());

					// s3에 파일(객체) 업로드
					String objectKey = "project/climbingCourse/" + climbingCourse.getId() + "/"
							+ newFile.getOriginalFilename();
					PutObjectRequest por = PutObjectRequest.builder()
							.acl(ObjectCannedACL.PUBLIC_READ)
							.bucket(bucketName)
							.key(objectKey)
							.build();
					RequestBody rb = RequestBody.fromInputStream(newFile.getInputStream(), newFile.getSize());
					s3.putObject(por, rb);
				}
			}

			// 게시물(Board) 테이블 수정
			int cnt = courseMapper.update(climbingCourse);

			return cnt == 1;
		}

		public boolean remove(Integer id) {

			// 파일명 조회
			List<String> fileNames = courseMapper.selectFileNamesByBoardId(id);

			// FileName 테이블의 데이터 지우기
			courseMapper.deleteFileNameByCourseId(id);

			// s3 bucket의 파일(객체) 지우기
			for (String fileName : fileNames) {
				String objectKey = "project/climbingCourse/" + id + "/" + fileName;
				DeleteObjectRequest dor = DeleteObjectRequest.builder()
						.bucket(bucketName)
						.key(objectKey)
						.build();
				s3.deleteObject(dor);

			}
			// 게시물 테이블의 데이터 지우기
			int cnt = courseMapper.deleteById(id);

			return cnt == 1;
		}
	}



