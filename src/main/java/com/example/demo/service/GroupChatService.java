package com.example.demo.service;

import java.io.*;
import java.time.*;
import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.multipart.*;

import com.example.demo.domain.*;
import com.example.demo.mapper.*;

import software.amazon.awssdk.core.sync.*;
import software.amazon.awssdk.services.s3.*;
import software.amazon.awssdk.services.s3.model.*;

@Service
public class GroupChatService {

	@Autowired
	private GroupChatMapper mapper;
	
	@Autowired
	private S3Client s3;
	
	@Value("${aws.s3.bucketUrl}")
	private String bucketUrl;

	@Value("${aws.s3.bucketName}")
	private String bucketName;
	
	public List<RunningBoard> groupChatRoomSelectByName(String myName) {
		List<RunningBoard> rList = new ArrayList<>();
		List<Integer> list = mapper.getBoardIdByMyName(myName);
		for(Integer i : list) {
			rList.add(mapper.groupChatRoomSelectByBoardId(i));
		}
		return rList;
	}

	public String lastMessageSelectById(Integer id) {
		return mapper.lastMessageSelectById(id);
	}

	public LocalDateTime getChatLastInserted(Integer id) {
		return mapper.getChatLastInserted(id);
	}

	public Integer getChatCount(Integer id, String myUserId) {
		if(mapper.getChatCount(id, myUserId) == null) {
			return 0;
		} else {
			return mapper.getChatCount(id, myUserId);
		}
	}

	public void resetCount(Integer chatRoomId, String myUserId) {
		mapper.resetCount(chatRoomId, myUserId);
	}

	public Integer getChatRoomId(LocalDateTime inserted) {
		return mapper.getChatRoomId(inserted);
	}

	public List<GroupChatMessage> getChat(Integer chatRoomId) {
		List<GroupChatMessage> list = mapper.getChatSelectByChatRoomId(chatRoomId);
		for (int i = 0; i < list.size(); i++) {
			LocalTime time = list.get(i).getInserted().toLocalTime();
			list.get(i).setTime(time.getHour() + ":" + time.getMinute());
			list.get(i).setImgUrl(bucketUrl + "/GroupChatRoom/" + chatRoomId + "/" + list.get(i).getFileName());
		}
		return list;
	}

	public List<GroupChatMessage> checkId(Integer lastChatId, Integer chatRoomId) {
		List<GroupChatMessage> list = mapper.checkId(lastChatId, chatRoomId);
		for (int i = 0; i < list.size(); i++) {
			LocalDateTime dateTime = list.get(i).getInserted();
			String time = dateTime.getHour() + ":" + dateTime.getMinute();
			list.get(i).setTime(time);
			list.get(i).setImgUrl(bucketUrl + "/GroupChatRoom/" + chatRoomId + "/" + list.get(i).getFileName());
		}
		return list;
	}

	public void addChat(GroupChatMessage chat) {
		if (chat.getMessage().trim().isEmpty()) {
			return;
		} else {
			mapper.addChat(chat);
		}
	}

	public void addFiles(GroupChatMessage chat, MultipartFile[] files) throws Exception {
		for (MultipartFile file : files) {
			if (file.getSize() > 0) {

				// 이름이 될 내용
				String objectKey = "project/GroupChatRoom/" + chat.getChatRoomId() + "/" + file.getOriginalFilename();

				// s3 첫번째 파라미터
				PutObjectRequest por = PutObjectRequest.builder().bucket(bucketName).key(objectKey)
						.acl(ObjectCannedACL.PUBLIC_READ).build();

				// s3 두번째 파라미터
				RequestBody rb = RequestBody.fromInputStream(file.getInputStream(), file.getSize());

				s3.putObject(por, rb);

				chat.setFileName(file.getOriginalFilename());
				
				mapper.insertFileChat(chat);
			}
		}
	}

	public void delete(Integer chatRoomId, String myUserId) {
		if(mapper.countMember(chatRoomId) < 2 || myUserId.equals(mapper.getHostByChatRoomId(chatRoomId))) {
			mapper.chatDeleteByChatRoomId(chatRoomId);
			mapper.runningPartyDeleteByChatRoomId(chatRoomId);
			mapper.chatRoomDeleteByChatRoomId(chatRoomId);
		} else {
			mapper.runningPartyDeleteMemberByChatRoomId(chatRoomId, myUserId);
		}
	}
	
	public List<RunningBoard> findRoomSelectBySearch(String search, String myId) {
		List<Integer> chatRoomIdList = mapper.boardIdSelectBySearch(search);
		List<Integer> list = new ArrayList<>();
		List<RunningBoard> chatRoomList = new ArrayList<>();
		for(int i : chatRoomIdList) {
			if(mapper.getMyRoom(i, myId) != null) {
				list.add(mapper.getMyRoom(i, myId));
			}
		}
		for(int i : list) {
			chatRoomList.add(mapper.getRunningBoard(i));
		}
		return chatRoomList;
	}
}
