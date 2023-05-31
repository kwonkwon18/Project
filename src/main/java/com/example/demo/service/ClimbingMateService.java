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
public class ClimbingMateService {
	
	@Autowired
	private S3Client s3;
	
	@Value("${aws.s3.bucketName}")
	private String bucketName;
	
	@Autowired
	private ClimbingMateMapper mateMapper;
	
	public boolean addClimbingMate(ClimbingMate climbingMate) {
		int cnt = mateMapper.insert(climbingMate);
		return cnt == 1;
	}
	
	public List<ClimbingMate> listBoard() {
		
		return mateMapper.selectList();
	}

	public ClimbingMate getClimbingMate(Integer id) {

		return mateMapper.selectById(id);
	}

	public boolean modify(ClimbingMate climbingMate, MultipartFile[] addFiles, List<String> removeFileNames) throws Exception {

		// FileName 테이블 삭제
		if (removeFileNames != null && !removeFileNames.isEmpty()) {
			for (String fileName : removeFileNames) {
				// s3에서 파일(객체) 삭제
				String objectKey = "project/climbingMate/" + climbingMate.getId() + "/" + fileName;
				DeleteObjectRequest dor = DeleteObjectRequest.builder()
						.bucket(bucketName)
						.key(objectKey)
						.build();
				s3.deleteObject(dor);

				// 테이블에서 삭제
				mateMapper.deleteFileNameByBoardIdAndFileName(climbingMate.getId(), fileName);
			}
		}

		// 새 파일 추가
		for (MultipartFile newFile : addFiles) {
			if (newFile.getSize() > 0) {
				// 테이블에 파일명 추가
				mateMapper.insertFileName(climbingMate.getId(), newFile.getOriginalFilename());

				// s3에 파일(객체) 업로드
				String objectKey = "project/climbingMate/" + climbingMate.getId() + "/" + newFile.getOriginalFilename();
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
		int cnt = mateMapper.update(climbingMate);

		return cnt == 1;
	}

	public boolean remove(Integer id) {

	// 파일명 조회
	List<String> fileNames = mateMapper.selectFileNamesByBoardId(id);
	
	// FileName 테이블의 데이터 지우기
	mateMapper.deleteFileNameByMateId(id);
	
	// s3 bucket의 파일(객체) 지우기
	for (String fileName : fileNames) {
		String objectKey = "project/climbingMate/" + id + "/" + fileName;
		DeleteObjectRequest dor = DeleteObjectRequest.builder()
				.bucket(bucketName)
				.key(objectKey)
				.build();
		s3.deleteObject(dor);

	}
		int cnt = mateMapper.deleteById(id);
	
		return cnt == 1;
	}

	public List<ClimbingMate> getMateBoard() {

		return mateMapper.selectMate();
	}
	
	public List<ClimbingMate> getMyPageInfo(String writer) {
		
		return mateMapper.selectMyPageInfo(writer);
	}


	public List<ClimbingParty> selectMemberIdByBoardId(Integer id, String writer) {

		return mateMapper.selectMemberIdByBoardId(id, writer);
	}

	public List<ClimbingParty> selectMemberIdByBoardId() {

		return mateMapper.selectMember();
	}

}


