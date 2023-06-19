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
	
	@Autowired
	private ClimbingLikeMapper likeMapper;
	
	@Autowired
	private ClimbingTodayCommentMapper commentMapper;

	public List<ClimbingToday> listBoard() {
		
		return todayMapper.selectListForList();
	}

	public List<ClimbingToday> listBoard(String todaySearch) {

		return todayMapper.selectListByTodaySearch(todaySearch);
	}

	public ClimbingToday getClimbingToday(Integer id, String myUserId) {

		ClimbingToday climbingToday = todayMapper.selectById(id);
		if (myUserId != null) {
			ClimbingTodayLike like = likeMapper.select(id, myUserId);
			if (like != null) {
				climbingToday.setLiked(true);
			}
		}
		return climbingToday;
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
		likeMapper.deleteByBoardId(id);
		commentMapper.deleteByBoardId(id);
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

	public Map<String, Object> like(ClimbingTodayLike like, Authentication auth) {

		// json을 보내줄 때는 ResponseEntity와 Map의 타입을 활용한다.
		Map<String, Object> result = new HashMap<>();

		// 기본 값으로 key = like, value = false를 넣어준다. 
		result.put("like", false);
			
		// memberid를 인증된 사람의 id로 바꿔준다.
		like.setMemberId(auth.getName());
		
		// 로직은 먼저 삭제를 하고 삭제가 만약 삭제가 안되었으면 
		// like 에 해당 정보를 insert 하고 json { "like" : true} 를 반환해준다.
		Integer deleteCnt = likeMapper.delete(like);

		if (deleteCnt != 1) {
			Integer insertCnt = likeMapper.insert(like);
			result.put("like", true);
		}
		
		// 좋아요 갯수 넘겨주기
		Integer count = likeMapper.countByBoardId(like.getBoardId());
		result.put("count", count);

		return result;
	}

	public List<ClimbingToday> todayListBoard() {
		return todayMapper.selectTodayList();
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