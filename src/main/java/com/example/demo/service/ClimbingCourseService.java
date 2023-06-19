package com.example.demo.service;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.*;
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
public class ClimbingCourseService {
	
	@Autowired
	private S3Client s3;
	
	@Value("${aws.s3.bucketName}")
	private String bucketName;
	
	@Autowired
	private ClimbingCourseMapper courseMapper;

	@Autowired
	private ClimbingLikeMapper likeMapper;
	
	@Autowired
	private ClimbingCourseCommentMapper commentMapper;
	
	public List<ClimbingCourse> listBoard() {
		
		return courseMapper.selectList();
	}

	public List<ClimbingCourse> listBoard(String courseSearch) {
		
		return courseMapper.selectListByCourseSearch(courseSearch);
	}
	
	public ClimbingCourse getClimbingCourse(Integer id, String myUserId) {

		ClimbingCourse climbingCourse = courseMapper.selectById(id);
		if (myUserId != null) {
			ClimbingCourseLike like = likeMapper.select1(id, myUserId);
			if (like != null) {
				climbingCourse.setLiked(true);
			}
		}
		return climbingCourse;
	}

	public List<ClimbingCourse> searchCourse(String searchTerm) {

		return courseMapper.selectBySearchTerm(searchTerm);
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



		public Map<String, Object> like(ClimbingCourseLike like, Authentication auth) {
			// json을 보내줄 때는 ResponseEntity와 Map의 타입을 활용한다.
			Map<String, Object> result = new HashMap<>();

			// 기본 값으로 key = like, value = false를 넣어준다. 
			result.put("like", false);
				
			// memberid를 인증된 사람의 id로 바꿔준다.
			like.setMemberId(auth.getName());
			
			// 로직은 먼저 삭제를 하고 삭제가 만약 삭제가 안되었으면 
			// like 에 해당 정보를 insert 하고 json { "like" : true} 를 반환해준다.
			Integer deleteCnt = likeMapper.delete1(like);

			if (deleteCnt != 1) {
				Integer insertCnt = likeMapper.insert1(like);
				result.put("like", true);
			}
			
			// 좋아요 갯수 넘겨주기
			Integer count = likeMapper.countByBoardId1(like.getBoardId());
			result.put("count", count);

			return result;
		}
	}



