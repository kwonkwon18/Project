package com.example.demo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.Member;
import com.example.demo.mapper.MemberMapper;

@Service
@Transactional(rollbackFor = Exception.class)
public class MemberService {

	@Autowired
	private MemberMapper mapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public String getNickName(String userId) {

		return mapper.getNickNameByUserId(userId);
	}

	// 서재권 추가내용 ***********
	public boolean signup(Member member) {
		// 암호를 새롭게 세팅해 준다.
		// plain은 입력해서 받아들여지는 암호
		// setPasswordEncoder를 통해서 다시 password를 set
		String plain = member.getPassword();
		member.setPassword(passwordEncoder.encode(plain));

		int cnt = mapper.insertMember(member);

		return cnt == 1;

	}

	public String getUserId(String yourNickName) {
		return mapper.getUserIdSelectByNickName(yourNickName);
	}

	public List<Member> listMember() {

		return mapper.selectAll();
	}

	public Member get(String userId) {
		return mapper.selectById(userId);
	}

	public boolean modify(Member member, String oldPassword) {
		Member oldMember = mapper.selectById(member.getUserId());

		int cnt = 0;
		if (oldMember.getPassword().equals(oldPassword)) {

			cnt = mapper.update(member);
		}

		return cnt == 1;
	}

	public boolean remove(Member member) {

		System.out.println(member);

		// 채팅 지우기
		mapper.deleteChatByUserId(member.getUserId());
		// 채팅방 지우기
		mapper.deleteChatRoomByUserId(member.getUserId());
		// 그룹 채팅방 지우기
		mapper.deleteGroupChatRoomByUserId(member.getUserId());

		// 러닝메이트 관련 ---
		// 러닝 ==========
		// 신청 목록 지우기
		mapper.deleteRunningPartyById(member.getUserId());
		System.out.println(1);

		// 러닝 today 관련 --
		// 좋아요 지우기
		mapper.deleteRunningTodayLikeById(member.getUserId());
		System.out.println(3);
		// 댓글 지우기
		mapper.deleteRunningTodayCommentById(member.getUserId());
		System.out.println(4);
		// 러닝 게시물 파일 지우기
		List<Integer> idList = mapper.selectIdByWriter(member.getNickName());
		for (Integer id : idList) {
			mapper.deleteRunningFileNameById(id);
		}
		System.out.println(5);

		// 러닝 today 지우기
		mapper.deleteRunningTodayById(member.getNickName());
		System.out.println(6);

		// 등산 =========
		// 등산메이트 관련
		// 신청 목록 지우기
		mapper.deleteClimbingPartyById(member.getUserId());
		System.out.println(7);

		// 등산 today 관련
		// 좋아요 지우기
		mapper.deleteClimbingTodayLikeById(member.getUserId());
		System.out.println(9);
		// 댓글 지우기
		mapper.deleteClimbingTodayCommentById(member.getUserId());
		System.out.println(10);

		// 등산 today 파일 지우기
		List<Integer> climbingTodayIdList = mapper.selectClimbingTodayByWriter(member.getNickName());
		for (Integer id : climbingTodayIdList) {
			mapper.deleteClimbingTodayFileNameById(id);
		}
		System.out.println(11);

		// 등산 today 지우기
		mapper.deleteClimbingTodayById(member.getNickName());
		System.out.println(12);

		// 등산 course 관련
		// 등산 코스 like 지우기
		mapper.deleteClimbingCourseLikeById(member.getUserId());
		System.out.println(13);
		// 등산코스 댓글 지우기
		mapper.deleteClimbingCourseCommentById(member.getUserId());
		System.out.println(14);
		// 등산 코스 파일 지우기
		List<Integer> climbingCourseIdList = mapper.selectClimbCourseIdByWriter(member.getNickName());
		for (Integer id : climbingCourseIdList) {
			mapper.deleteClimbingCourseFileNameById(id);
		}
		System.out.println(15);
		// 등산 코스 지우기
		mapper.deleteClimbingCourseById(member.getNickName());
		System.out.println(16);

		// 러닝 게시물 지우기
		mapper.deleteRunningBoardById(member.getNickName());
		System.out.println(2);

		int cnt = mapper.deleteMember(member.getUserId());

		return cnt == 1;
	}

	public boolean modify(Member member) {
		System.out.println(member);
		member.setId(mapper.getId(member.getUserId()));
		int cnt = mapper.updateMember(member);
		System.out.println("@@@" + cnt);

		return cnt == 1;
	}

	public Map<String, Object> IDCheck(String userId) {
		Member member = mapper.selectById(userId);

		return Map.of("available", member == null);
	}

	public Map<String, Object> checkNickName(String nickName) {
		Member member = mapper.selectByNickName(nickName);
		return Map.of("available", member == null);
	}

	public Map<String, Object> checkEmail(String email) {
		Member member = mapper.selectByEmail(email);

		return Map.of("available", member == null);
	}
}