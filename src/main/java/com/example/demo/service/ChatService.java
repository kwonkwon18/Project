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
public class ChatService {

	@Autowired
	private ChatMapper mapper;

	@Autowired
	private MemberMapper memberMapper;

	@Autowired
	private S3Client s3;

	@Value("${aws.s3.bucketUrl}")
	private String bucketUrl;

	@Value("${aws.s3.bucketName}")
	private String bucketName;

	public List<ChatRoom> invitedSelectByName(String myName) {
		return mapper.chatRoomSelectByMyName(myName);

	}

	public String lastMessageSelectById(Integer id) {
		return mapper.lastMessageSelectById(id);
	}

	public List<Chat> getChat(LocalDateTime inserted, String myUserId) {
		int chatRoomId = mapper.getChatRoomIdByInserted(inserted, myUserId);
		List<Chat> list = mapper.getChatSelectByChatRoomId(chatRoomId);
		List<LocalDateTime> dateTimeList = mapper.getinsertedByChatRoomId(chatRoomId);
		for (int i = 0; i < dateTimeList.size(); i++) {
			LocalTime time = dateTimeList.get(i).toLocalTime();
			list.get(i).setTime(time.getHour() + ":" + time.getMinute());
			list.get(i).setImgUrl(bucketUrl + "/ChatRoom/" + chatRoomId + "/" + list.get(i).getFileName());
		}

		return list;
	}

	public void addChat(Chat data) {
//		for(String s : mapper.getChatRoomUserId(data.getChatRoomId())) {
//			if(!s.equals(data.getSenderId())) {
//				data.setRecipientId(s);
//			}
//		}
		Map<String, String> map = mapper.getChatRoomUserId(data.getChatRoomId());
		if (!map.get("creater").equals(data.getSenderId())) {
			data.setRecipientId(map.get("creater"));
		} else {
			data.setRecipientId(map.get("invited"));
		}
		if (data.getMessage().trim().isEmpty()) {
			return;
		} else {
			mapper.addChat(data);
		}
	}

	public List<Chat> checkId(Integer lastChatId, Integer chatRoomId) {
		List<Chat> list = mapper.checkId(lastChatId, chatRoomId);
		for (int i = 0; i < list.size(); i++) {
			LocalDateTime dateTime = list.get(i).getInserted();
			String time = dateTime.getHour() + ":" + dateTime.getMinute();
			list.get(i).setTime(time);
			list.get(i).setImgUrl(bucketUrl + "/ChatRoom/" + chatRoomId + "/" + list.get(i).getFileName());
		}
		return list;
	}

	public void delete(Integer chatRoomId, String myUserId) {
		if (mapper.getChatRoomUserId(chatRoomId).get("creater") == null
				|| mapper.getChatRoomUserId(chatRoomId).get("invited") == null) {
			mapper.chatDeleteByChatRoomId(chatRoomId);
			mapper.chatRoomDeleteByChatRoomId(chatRoomId);
		} else {
			if (mapper.getChatRoomUserId(chatRoomId).get("creater").equals(myUserId)) {
				mapper.removeChatRoomCreater(chatRoomId);
			} else {
				mapper.removeChatRoomInvited(chatRoomId);
			}
		}
	}

	public Integer getChatRoomId(String myUserId, LocalDateTime dateInserted) {
		return mapper.getChatRoomIdByInserted(dateInserted, myUserId);
	}

	public void resetCount(Integer chatRoomId, String myUserId) {
		if (myUserId.equals(mapper.getCreaterByChatRoomId(chatRoomId))) {
			mapper.resetInvitedChatCount(chatRoomId);
		} else {
			mapper.resetCreaterChatCount(chatRoomId);
		}
		// TODO Auto-generated method stub

	}

	public boolean checkChatRoom(String yourId, String myId) {
		int cnt = 0;
		for (int i : mapper.checkChatRoom(yourId, myId)) {
			cnt += i;
		}
		return cnt > 0;
	}

	public LocalDateTime getChatLastInserted(Integer id) {
		if (mapper.getChatLastInserted(id) == null) {
			LocalDateTime time = LocalDateTime.now();
			return time;
		} else {
			return mapper.getChatLastInserted(id);
		}
	}

	public List<Chat> getChat(String myId, String yourId) {
		Integer chatRoomId = mapper.getChatRoomIdByYourId(myId, yourId);
		List<Chat> list = mapper.getChatSelectByChatRoomId(chatRoomId);
		List<LocalDateTime> dateTimeList = mapper.getinsertedByChatRoomId(chatRoomId);
		for (int i = 0; i < dateTimeList.size(); i++) {
			LocalTime time = dateTimeList.get(i).toLocalTime();
			list.get(i).setTime(time.getHour() + ":" + time.getMinute());
			list.get(i).setImgUrl(bucketUrl + "/ChatRoom/" + chatRoomId + "/" + list.get(i).getFileName());
		}
		return list;
	}

	public Integer getChatRoomId(String yourId, String myId) {
		return mapper.getChatRoomIdByYourId(myId, yourId);
	}

	public void createChatRoom(String myId, String yourNickName) {
		String yourId = memberMapper.getUserIdSelectByNickName(yourNickName);
		if (yourId == null) {
			return;
		} else {
			mapper.createChatRoom(myId, yourId);
		}

	}

	public void addFiles(Chat chat, MultipartFile[] files) throws Exception {
		Map<String, String> map = mapper.getChatRoomUserId(chat.getChatRoomId());
		if (!map.get("creater").equals(chat.getSenderId())) {
			chat.setRecipientId(map.get("creater"));
		} else {
			chat.setRecipientId(map.get("invited"));
		}
		for (MultipartFile file : files) {
			if (file.getSize() > 0) {

				// 이름이 될 내용
				String objectKey = "project/ChatRoom/" + chat.getChatRoomId() + "/" + file.getOriginalFilename();

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

	public List<ChatRoom> findRoomSelectBySearch(String search, String myId) {
		List<String> searchId = memberMapper.UserIdSelectBySearch(search);
		List<ChatRoom> list = new ArrayList<>();
		for (String s : searchId) {
			if (mapper.findRoomSelectBySearch(s, myId) != null) {
				list.add(mapper.findRoomSelectBySearch(s, myId));
			}
		}
		return list;
	}

	public List<Integer> searchChatId(String search, Integer chatRoomId) {
		return mapper.searchChatId(search, chatRoomId);
	}

	public Integer getMyCount(String name) {
		List<ChatRoom> list = mapper.chatRoomSelectByMyName(name);
		Integer count = 0;
		for(ChatRoom chatRoom : list) {
			if(chatRoom.getCreater().equals(name)) {
				count += chatRoom.getInvitedChatCount();
			} else {
				count += chatRoom.getCreaterChatCount();
			}
		}
		return count;
	}

}
