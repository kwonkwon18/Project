package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.domain.Member;
import com.example.demo.domain.RunningBoard;
import com.example.demo.domain.RunningParty;
import com.example.demo.domain.RunningToday;
import com.example.demo.mapper.RunningMapper;
import com.example.demo.mapper.RunningPartyMapper;
import com.example.demo.mapper.RunningTodayMapper;

import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;

@Service
public class RunningService {

	@Autowired
	private RunningMapper mapper;

	@Autowired
	private RunningTodayMapper todayMapper;

	@Autowired
	private RunningPartyMapper partyMapper;

	public boolean addBoard(RunningBoard runningBoard, Authentication authentication) {

		Member member = mapper.selectMemberById(authentication.getName());
		runningBoard.setWriter(member.getNickName());

		int cnt = mapper.insert(runningBoard);
		return cnt == 1;
	}

	public List<RunningBoard> listBoard() {

		return mapper.selectList();
	}

	public RunningBoard getBoard(Integer id) {

		return mapper.selectById(id);
	}

	public List<RunningBoard> getMyPageInfo(String writer) {

		return mapper.selectMyPageInfo(writer);
	}

	public List<RunningParty> getJoinMember(String writer) {

		return mapper.selectMemberId(writer);
	}

	public List<RunningBoard> getMateBoard() {

		return mapper.selectMate();
	}

	public List<RunningBoard> getMateBoard(Authentication authentication) {


		return mapper.selectMate();

	}
	
	public List<RunningBoard> getMateBoardByAddress(Authentication authentication, String type, String search) {

		if(authentication != null) {

		
		// 유저 정보 가져옴 Member member =
		Member member = mapper.selectMemberById(authentication.getName()); // 유저 정보 중 주소 정보 변수 저장
		String userAddress = member.getAddress();
		System.out.println(userAddress);
		// 주소가 들어갈 ArrayList
		List<String> addressList = new ArrayList<>();

		if (userAddress != "") {
			if (userAddress.equals("은평구")) {
				addressList.add("서대문구");
				addressList.add("관악구");
				addressList.add("은평구");
			}

		}// 마지막 괄호임
		
		System.out.println("addressList" + addressList);

		return mapper.selectMateByDistance(addressList, type, search);
	}
		
		return null;

	}

	public List<RunningParty> selectMemberIdByBoardId(Integer id, String writer) {

		return mapper.selectMemberIdByBoardId(id, writer);
	}

	public List<RunningParty> selectMemberIdByBoardId() {
		// TODO Auto-generated method stub
		return mapper.selectMember();
	}

	public List<Member> getUserId(String userId) {
		// TODO Auto-generated method stub
		return mapper.selectUserId(userId);
	}

	public Member getMembertUserId(String userId) {
		// TODO Auto-generated method stub
		return mapper.selectMemberUserId(userId);
	}

	public Map<String, Object> getBoardForModal(Integer boardId, Authentication authentication) {

		Map<String, Object> getMemberList = new HashMap<>();

		// board에 대한 정보가 들어감
		RunningBoard getList = mapper.selectById(boardId);
		getMemberList.put("board", getList);

		// 신청자가 들어감
		List<RunningParty> members = mapper.selectForMemberIdByBoardId(boardId);
		getMemberList.put("members", members);

		// 중복 신청 방지용
		List<Member> memberList = getUserId(authentication.getName());
		getMemberList.put("memberList", memberList);

		// 로그인한 사람 확인용 (닉네임)
		Member myNickName = mapper.getNickName(authentication.getName());
		getMemberList.put("myNickName", myNickName);

		return getMemberList;
	}

	public List<RunningBoard> getTotalMyPageInfo(String nickName1, String nickName2) {
		// TODO Auto-generated method stub
		return mapper.selectTotalMyPageInfo(nickName1, nickName2);
	}

	public boolean modify(RunningBoard runningBoard) {
		// TODO Auto-generated method stub
		return mapper.updateBoardById(runningBoard);
	}

	public boolean remove(Integer id) {

		// 참조키 제약 사항으로 러닝파티에서 boardId에 해당하는 것들을 지워함
		int cnt = partyMapper.deleteByBoardId(id);

		return mapper.deleteById(id);
	}

	public Object searchMate(String searchTerm) {
		// TODO Auto-generated method stub
		return mapper.selectBySearchTerm(searchTerm);
	}




}
