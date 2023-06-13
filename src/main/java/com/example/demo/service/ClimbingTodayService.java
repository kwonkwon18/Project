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
public class ClimbingTodayService {

	@Autowired
	private S3Client s3;

	@Value("${aws.s3.bucketName}")
	private String bucketName;

	@Autowired
	private ClimbingTodayMapper todayMapper;

	public List<ClimbingToday> listBoard() {
		
		return todayMapper.selectListForList();
	}

	public List<ClimbingToday> listBoard(String todaySearch) {

		return todayMapper.selectListByTodaySearch(todaySearch);
	}

	public ClimbingToday getClimbingToday(Integer id) {

		return todayMapper.selectById(id);
	}
	
	public List<ClimbingToday> searchToday(String searchTerm) {

		return todayMapper.selectBySearchTerm(searchTerm);
	}

	public boolean modify(ClimbingToday climbingToday, MultipartFile[] addFiles, List<String> removeFileNames)
			throws Exception {

		// FileName 테이블 삭제
		if (removeFileNames != null && !removeFileNames.isEmpty()) {
			for (String fileName : removeFileNames) {
				// s3에서 파일(객체) 삭제
				String objectKey = "project/climbingToday/" + climbingToday.getId() + "/" + fileName;
				DeleteObjectRequest dor = DeleteObjectRequest.builder()
						.bucket(bucketName)
						.key(objectKey)
						.build();
				s3.deleteObject(dor);

				// 테이블에서 삭제
				todayMapper.deleteFileNameByBoardIdAndFileName(climbingToday.getId(), fileName);
			}
		}

		// 새 파일 추가
		for (MultipartFile newFile : addFiles) {
			if (newFile.getSize() > 0) {
				// 테이블에 파일명 추가
				todayMapper.insertFileName(climbingToday.getId(), newFile.getOriginalFilename());

				// s3에 파일(객체) 업로드
				String objectKey = "project/climbingToday/" + climbingToday.getId() + "/"
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
		int cnt = todayMapper.update(climbingToday);

		return cnt == 1;
	}

	public boolean remove(Integer id) {

		// 파일명 조회
		List<String> fileNames = todayMapper.selectFileNamesByBoardId(id);

		// FileName 테이블의 데이터 지우기
		todayMapper.deleteFileNameByTodayId(id);

		// s3 bucket의 파일(객체) 지우기
		for (String fileName : fileNames) {
			String objectKey = "project/climbingToday/" + id + "/" + fileName;
			DeleteObjectRequest dor = DeleteObjectRequest.builder()
					.bucket(bucketName)
					.key(objectKey)
					.build();
			s3.deleteObject(dor);

		}
		// 게시물 테이블의 데이터 지우기
		int cnt = todayMapper.deleteById(id);

		return cnt == 1;
	}

	public boolean addClimbingToday(ClimbingToday climbingToday, MultipartFile[] files, Authentication authentication)
			throws Exception {

		Member member = todayMapper.selectMemberById(authentication.getName());
		climbingToday.setWriter(member.getNickName());

		int cnt = todayMapper.insert(climbingToday);
		for (MultipartFile file : files) {
			if (file.getSize() > 0) {
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

//	public Map<String, Object> listBoard(Integer page, String search, String type) {
//		// 페이지당 행의 수
//		Integer rowPerPage = 15;
//
//		// 쿼리 LIMIT 절에 사용할 시작 인덱스
//		Integer startIndex = (page - 1) * rowPerPage;
//
//		// 페이지네이션이 필요한 정보
//		// 전체 레코드 수
//		Integer numOfRecords = todayMapper.countAll(search, type);
//		// 마지막 페이지 번호
//		Integer lastPageNumber = (numOfRecords - 1) / rowPerPage + 1;
//		// 페이지네이션 왼쪽번호
//		Integer leftPageNum = page - 5;
//		// 1보다 작을 수 없음
//		leftPageNum = Math.max(leftPageNum, 1);
//
//		// 페이지네이션 오른쪽번호
//		Integer rightPageNum = leftPageNum + 9;
//		// 마지막페이지보다 클 수 없음
//		rightPageNum = Math.min(rightPageNum, lastPageNumber);
//
//		Map<String, Object> pageInfo = new HashMap<>();
//		pageInfo.put("rightPageNum", rightPageNum);
//		pageInfo.put("leftPageNum", leftPageNum);
//		pageInfo.put("currentPageNum", page);
//		pageInfo.put("lastPageNum", lastPageNumber);
//
//		// 게시물 목록
//		List<ClimbingToday> list = todayMapper.selectAllPaging(startIndex, rowPerPage, search, type);
//
//		return Map.of("pageInfo", pageInfo,
//				"boardList", list);
//	}
}