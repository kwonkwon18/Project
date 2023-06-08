package com.example.demo.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.domain.Member;
import com.example.demo.domain.RunningToday;
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

	public List<RunningToday> listBoard() {

		return todayMapper.selectList();

	}

	public boolean addRunningToday(Authentication authentication, RunningToday runningToday, MultipartFile[] files)
			throws Exception {

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

	public RunningToday getBoard(Integer id) {
		System.out.println("***" + todayMapper.selectFileNameById(id));
		return todayMapper.selectFileNameById(id);
	}

	public boolean todayModify(RunningToday runningToday, List<String> removeFileNames, MultipartFile[] addFiles) throws Exception {
		// FileName 테이블 삭제
		// List<String> removeFileNamse에 뭐라도 있다면..
		if (removeFileNames != null && !removeFileNames.isEmpty()) {
			for (String fileName : removeFileNames) {

				String objectKey = "project/runningToday/" + runningToday.getId() + "/" + fileName;

				DeleteObjectRequest dor = DeleteObjectRequest.builder().bucket(bucketName).key(objectKey).build();

				s3.deleteObject(dor);
///////////////////////////
				// 로컬 하드디스크에서 삭제
//				String path = "C:\\study\\upload\\" + board.getId() + "\\" + fileName;
//				File file = new File(path);
//				if (file.exists()) {
//					file.delete();
//				}

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
}
