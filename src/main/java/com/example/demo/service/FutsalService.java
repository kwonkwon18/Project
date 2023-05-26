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

	@Value("${aws.s3.bucketName}")
	private String bucketName;

	@Autowired
	private FutsalMapper futsalMapper;

	public List<FutsalBoard> listBoard() {
		List<FutsalBoard> list = futsalMapper.selectAll();
		return list;
	}

	public FutsalBoard getFutsalBoard(Integer id) {
		FutsalBoard board = futsalMapper.selectById(id);
		return board;
	}

	public boolean addBoard(FutsalBoard futsalBoard, MultipartFile[] files) throws Exception {

		// 게시물 insert

		int cnt = futsalMapper.insert(futsalBoard);

		for (MultipartFile file : files) {
			if (file.getSize() > 0) {
				String objectKey = "project/futsalBoard/" + futsalBoard.getId() + "/" + file.getOriginalFilename();


				PutObjectRequest por = PutObjectRequest.builder()
						.bucket(bucketName)
						.key(objectKey)
						.acl(ObjectCannedACL.PUBLIC_READ)
						.build();
				RequestBody rb = RequestBody.fromInputStream(file.getInputStream(), file.getSize());

				// db에 관련 정보 저장(insert)
				s3.putObject(por, rb);
				futsalMapper.insertFileName(futsalBoard.getId(), file.getOriginalFilename());

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

	public boolean modify(FutsalBoard futsalBoard, MultipartFile[] addFiles, List<String> removeFileNames) throws Exception {
		
		// FutsalFileName 테이블 삭제
		if(removeFileNames != null && !removeFileNames.isEmpty()) {
			for(String fileName : removeFileNames) {
				// s3에서 파일 삭제
				String objectKey = "project/futsalBoard/" + futsalBoard.getId() + "/" + fileName;
				DeleteObjectRequest dor = DeleteObjectRequest.builder()
						.bucket(bucketName)
						.key(objectKey)
						.build();
				s3.deleteObject(dor);
				
				// 테이블에서 삭제
				futsalMapper.deleteFileNameByBoardIdAndFileName(futsalBoard.getId(), fileName);
			}
		}
		
		// 새 파일 추가
		for (MultipartFile newFile : addFiles) {
			if(newFile.getSize() > 0) {
				// 테이블에 파일명 추가
				futsalMapper.insertFileName(futsalBoard.getId(), newFile.getOriginalFilename());
				
				// s3에 파일 업로드
				String objectKey = "project/futsalBoard/" + futsalBoard.getId() + "/" + newFile.getOriginalFilename();
				PutObjectRequest por = PutObjectRequest.builder()
						.acl(ObjectCannedACL.PUBLIC_READ)
						.bucket(bucketName)
						.key(objectKey)
						.build();
				RequestBody rb = RequestBody.fromInputStream(newFile.getInputStream(), newFile.getSize());
				s3.putObject(por, rb);
			}
		}
		
		
		// 게시물 (futsalBoard) 테이블 수정
		int cnt = futsalMapper.update(futsalBoard);
		return cnt == 1;
	}

	public boolean remove(Integer id) {

		// 파일명 조회
		List<String> fileNames = futsalMapper.selectFileNameByBoardId(id);

		// FileName 테이블의 데이터 지우기
		futsalMapper.deleteFileNameByBoardId(id);

		for (String fileName : fileNames) {
			String objectKey = "project/futsalBoard/" + id + "/" + fileName;
			DeleteObjectRequest dor = DeleteObjectRequest.builder()
					.bucket(bucketName)
					.key(objectKey)
					.build();
			s3.deleteObject(dor);

		}
		int cnt = futsalMapper.deleteById(id);
		
		return cnt == 1;

	}
}
