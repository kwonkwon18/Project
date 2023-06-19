package com.example.demo.service;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.*;
import org.springframework.stereotype.*;
import org.springframework.web.multipart.*;

import com.example.demo.domain.*;
import com.example.demo.mapper.*;

import software.amazon.awssdk.core.sync.*;
import software.amazon.awssdk.services.s3.*;
import software.amazon.awssdk.services.s3.model.*;

@Service
public class FutsalTodayService {

	@Autowired
	private S3Client s3;

	@Value("${aws.s3.bucketName}")
	private String bucketName;

	@Autowired
	private FutsalTodayMapper todayMapper;

	@Autowired
	private FutsalMapper mapper;

	public List<FutsalToday> listBoard() {

		return todayMapper.selectList();

	}

	public boolean addFutsalToday(Authentication authentication, FutsalToday futsalToday, MultipartFile[] files)
			throws Exception {

		Member member = mapper.selectMemberById(authentication.getName());
		futsalToday.setWriter(member.getNickName());
		
		Integer cnt = todayMapper.insertFutsalToday(futsalToday);

		for (MultipartFile file : files) {
			if (file.getSize() > 0) {

				// 이름이 될 내용
				String objectKey = "project/futsalToday/" + futsalToday.getId() + "/" + file.getOriginalFilename();

				// s3 첫번째 파라미터
				PutObjectRequest por = PutObjectRequest.builder().bucket(bucketName).key(objectKey)
						.acl(ObjectCannedACL.PUBLIC_READ).build();

				// s3 두번째 파라미터
				RequestBody rb = RequestBody.fromInputStream(file.getInputStream(), file.getSize());

				s3.putObject(por, rb);

				todayMapper.insertFileName(futsalToday.getId(), file.getOriginalFilename());
			}
		}

		return cnt == 1;

	}

	public FutsalToday getBoard(Integer id) {
		System.out.println("***" + todayMapper.selectFileNameById(id));
		return todayMapper.selectFileNameById(id);
	}

	public boolean todayModify(FutsalToday futsalToday, List<String> removeFileNames, MultipartFile[] addFiles) throws Exception {
		// FileName 테이블 삭제
		// List<String> removeFileNamse에 뭐라도 있다면..
		if (removeFileNames != null && !removeFileNames.isEmpty()) {
			for (String fileName : removeFileNames) {

				String objectKey = "project/futsalToday/" + futsalToday.getId() + "/" + fileName;

				DeleteObjectRequest dor = DeleteObjectRequest.builder().bucket(bucketName).key(objectKey).build();

				s3.deleteObject(dor);

				// 먼저 파일을 지우고 테이블을 지워준다.
				mapper.deleteFileNameByBoardIdAndFileName(futsalToday.getId(), fileName);
			}
		}

		// 새 파일 추가
		for (MultipartFile newFile : addFiles) {
			if (newFile.getSize() > 0) {
				// 테이블에 파일명 추가
				todayMapper.insertFileName(futsalToday.getId(), newFile.getOriginalFilename());

				// s3에 파일(객체) 업로드
				String objectKey = "project/futsalToday/" + futsalToday.getId() + "/" + newFile.getOriginalFilename();

				// s3 첫번째 파라미터
				PutObjectRequest por = PutObjectRequest.builder().bucket(bucketName).key(objectKey)
						.acl(ObjectCannedACL.PUBLIC_READ).build();

				// s3 두번째 파라미터
				RequestBody rb = RequestBody.fromInputStream(newFile.getInputStream(), newFile.getSize());

				s3.putObject(por, rb);

			}
		}

		// Board 테이블을 업데이트 해줌
		int cnt = todayMapper.update(futsalToday);

		return cnt == 1;
	}

	public boolean removeById(Integer id) {
		
		Integer cnt = todayMapper.deletefileNameById(id);
		
		return todayMapper.deleteTodayById(id);
	}
}
