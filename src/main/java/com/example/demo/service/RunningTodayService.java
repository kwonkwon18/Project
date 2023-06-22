package com.example.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.domain.Member;
import com.example.demo.domain.RunningLike;
import com.example.demo.domain.RunningToday;
import com.example.demo.mapper.RunningLikeMapper;
import com.example.demo.mapper.RunningMapper;
import com.example.demo.mapper.RunningTodayMapper;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
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

	@Autowired
	private RunningMapper mapper;

	@Autowired
	private RunningLikeMapper likeMapper;

	public List<RunningToday> listBoard(String search) {

		return todayMapper.selectList(search);

	}

	// ** After
	public Map<String, Object> like(RunningLike like, Authentication auth) {

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

	public boolean addRunningToday(Authentication authentication, RunningToday runningToday, MultipartFile[] files)
			throws Exception {

		Member member = mapper.selectMemberById(authentication.getName());
		runningToday.setWriter(member.getNickName());

		Integer cnt = todayMapper.insertRunningToday(runningToday);

		for (MultipartFile file : files) {
			if (file.getSize() > 0) {

				// 이름이 될 내용
				String objectKey = "project/runningToday/" + runningToday.getId() + "/" + file.getOriginalFilename();

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

	public RunningToday getBoard(Integer id, Authentication authentication) {
		RunningToday runningToday = todayMapper.selectFileNameById(id);

		// like 여부가 표시되게 하기 위함
		if (authentication != null) {
			RunningLike like = likeMapper.select(id, authentication.getName());
			if (like != null) {
				runningToday.setLiked(true);
			}
		}
		return runningToday;
	}

	public boolean todayModify(RunningToday runningToday, List<String> removeFileNames, MultipartFile[] addFiles)
			throws Exception {
		// FileName 테이블 삭제
		// List<String> removeFileNamse에 뭐라도 있다면..
		if (removeFileNames != null && !removeFileNames.isEmpty()) {
			for (String fileName : removeFileNames) {

				String objectKey = "project/runningToday/" + runningToday.getId() + "/" + fileName;

				DeleteObjectRequest dor = DeleteObjectRequest.builder().bucket(bucketName).key(objectKey).build();

				s3.deleteObject(dor);

				// 먼저 파일을 지우고 테이블을 지워준다.
				mapper.deleteFileNameByBoardIdAndFileName(runningToday.getId(), fileName);
			}
		}

		// 새 파일 추가
		for (MultipartFile newFile : addFiles) {
			if (newFile.getSize() > 0) {
				// 테이블에 파일명 추가
				todayMapper.insertFileName(runningToday.getId(), newFile.getOriginalFilename());

				// s3에 파일(객체) 업로드
				String objectKey = "project/runningToday/" + runningToday.getId() + "/" + newFile.getOriginalFilename();

				// s3 첫번째 파라미터
				PutObjectRequest por = PutObjectRequest.builder().bucket(bucketName).key(objectKey)
						.acl(ObjectCannedACL.PUBLIC_READ).build();

				// s3 두번째 파라미터
				RequestBody rb = RequestBody.fromInputStream(newFile.getInputStream(), newFile.getSize());

				s3.putObject(por, rb);

			}
		}

		// Board 테이블을 업데이트 해줌
		int cnt = todayMapper.update(runningToday);

		return cnt == 1;
	}

	public boolean removeById(Integer id) {

		Integer cnt = todayMapper.deletefileNameById(id);

		return todayMapper.deleteTodayById(id);
	}
}
