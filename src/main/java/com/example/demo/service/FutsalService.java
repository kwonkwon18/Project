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
import com.example.demo.domain.FutsalBoard;
import com.example.demo.domain.FutsalParty;
import com.example.demo.domain.RunningToday;
import com.example.demo.mapper.FutsalMapper;
import com.example.demo.mapper.FutsalPartyMapper;
import com.example.demo.mapper.FutsalTodayMapper;

import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;

@Service
public class FutsalService {

	@Autowired
	private FutsalMapper mapper;

	@Autowired
	private FutsalTodayMapper todayMapper;

	@Autowired
	private FutsalPartyMapper partyMapper;

	public boolean addBoard(FutsalBoard futsalBoard, Authentication authentication) {

		Member member = mapper.selectMemberById(authentication.getName());
		futsalBoard.setWriter(member.getNickName());

		int cnt = mapper.insert(futsalBoard);
		return cnt == 1;
	}

	public List<FutsalBoard> listBoard() {

		return mapper.selectList();
	}

	public FutsalBoard getBoard(Integer id) {

		return mapper.selectById(id);
	}

	public List<FutsalBoard> getMyPageInfo(String writer) {

		return mapper.selectMyPageInfo(writer);
	}

	public List<FutsalParty> getJoinMember(String writer) {

		return mapper.selectMemberId(writer);
	}

	public List<FutsalBoard> getMateBoard() {

		return mapper.selectMate();
	}

	public List<FutsalBoard> getMateBoard(Authentication authentication) {


		return mapper.selectMate();

	}
	
	public List<FutsalBoard> getMateBoardByAddress(Authentication authentication, String type, String search) {

		if(authentication != null) {

		
		// 유저 정보 가져옴 Member member =
		Member member = mapper.selectMemberById(authentication.getName()); // 유저 정보 중 주소 정보 변수 저장
		String userAddress = member.getAddress();
		System.out.println(userAddress);
		// 주소가 들어갈 ArrayList
		List<String> addressList = new ArrayList<>();

		if (userAddress != "") {
			if (userAddress.equals("은평구")) {
				addressList.add("은평구");
				addressList.add("마포구");
				addressList.add("서대문구");
			} else if (userAddress.equals("마포구")) {
				addressList.add("마포구");
				addressList.add("서대문구");
				addressList.add("은평구");
				addressList.add("용산구");
			} else if (userAddress.equals("종로구")) {
				addressList.add("종로구");
				addressList.add("성북구");
				addressList.add("중구");
				addressList.add("서대문구");
			} else if (userAddress.equals("중구")) {
				addressList.add("중구");
				addressList.add("종로구");
				addressList.add("성동구");
				addressList.add("용산구");
			} else if (userAddress.equals("용산구")) {
				addressList.add("용산구");
				addressList.add("마포구");
				addressList.add("중구");
				addressList.add("성동구");
			} else if (userAddress.equals("성동구")) {
				addressList.add("성동구");
				addressList.add("중구");
				addressList.add("동대문구");
				addressList.add("광진구");
			} else if (userAddress.equals("광진구")) {
				addressList.add("광진구");
				addressList.add("성동구");
				addressList.add("동대문구");
				addressList.add("중랑구");
			} else if (userAddress.equals("동대문구")) {
				addressList.add("동대문구");
				addressList.add("성북구");
				addressList.add("중랑구");
				addressList.add("성동구");
			} else if (userAddress.equals("중랑구")) {
				addressList.add("중랑구");
				addressList.add("노원구");
				addressList.add("동대문구");
				addressList.add("광진구");
			} else if (userAddress.equals("성북구")) {
				addressList.add("성북구");
				addressList.add("강북구");
				addressList.add("동대문구");
				addressList.add("종로구");
			} else if (userAddress.equals("강북구")) {
				addressList.add("강북구");
				addressList.add("도봉구");
				addressList.add("노원구");
				addressList.add("성북구");
			} else if (userAddress.equals("도봉구")) {
				addressList.add("도봉구");
				addressList.add("노원구");
				addressList.add("강북구");
				addressList.add("성북구");
			} else if (userAddress.equals("노원구")) {
				addressList.add("노원구");
				addressList.add("도봉구");
				addressList.add("강북구");
				addressList.add("중랑구");
			} else if (userAddress.equals("서대문구")) {
				addressList.add("서대문구");
				addressList.add("은평구");
				addressList.add("종로구");
				addressList.add("마포구");
			} else if (userAddress.equals("양천구")) {
				addressList.add("양천구");
				addressList.add("강서구");
				addressList.add("영등포구");
				addressList.add("구로구");
			} else if (userAddress.equals("강서구")) {
				addressList.add("강서구");
				addressList.add("양천구");
				addressList.add("구로구");
				addressList.add("영등포구");
			} else if (userAddress.equals("구로구")) {
				addressList.add("구로구");
				addressList.add("양천구");
				addressList.add("영등포구");
				addressList.add("금천구");
			} else if (userAddress.equals("금천구")) {
				addressList.add("금천구");
				addressList.add("구로구");
				addressList.add("관악구");
				addressList.add("영등포구");
			} else if (userAddress.equals("영등포구")) {
				addressList.add("영등포구");
				addressList.add("양천구");
				addressList.add("구로구");
				addressList.add("동작구");
			} else if (userAddress.equals("동작구")) {
				addressList.add("동작구");
				addressList.add("영등포구");
				addressList.add("관악구");
				addressList.add("서초구");
			} else if (userAddress.equals("관악구")) {
				addressList.add("관악구");
				addressList.add("금천구");
				addressList.add("동작구");
				addressList.add("서초구");
			} else if (userAddress.equals("서초구")) {
				addressList.add("서초구");
				addressList.add("강남구");
				addressList.add("동작구");
				addressList.add("관악구");
			} else if (userAddress.equals("강남구")) {
				addressList.add("강남구");
				addressList.add("서초구");
				addressList.add("송파구");
				addressList.add("성동구");
			} else if (userAddress.equals("송파구")) {
				addressList.add("송파구");
				addressList.add("강남구");
				addressList.add("강동구");
				addressList.add("광진구");
			} else if (userAddress.equals("강동구")) {
				addressList.add("강동구");
				addressList.add("송파구");
				addressList.add("광진구");
				addressList.add("강남구");
			}
		

		}// 마지막 괄호임
		
		System.out.println("addressList" + addressList);

		return mapper.selectMateByDistance(addressList, type, search);
	}
		
		return null;

	}

	public List<FutsalParty> selectMemberIdByBoardId(Integer id, String writer) {

		return mapper.selectMemberIdByBoardId(id, writer);
	}

	public List<FutsalParty> selectMemberIdByBoardId() {
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
		FutsalBoard getList = mapper.selectById(boardId);
		getMemberList.put("board", getList);

		// 신청자가 들어감
		List<FutsalParty> members = mapper.selectForMemberIdByBoardId(boardId);
		getMemberList.put("members", members);

		// 중복 신청 방지용
		List<Member> memberList = getUserId(authentication.getName());
		getMemberList.put("memberList", memberList);

		// 로그인한 사람 확인용 (닉네임)
		Member myNickName = mapper.getNickName(authentication.getName());
		getMemberList.put("myNickName", myNickName);

		return getMemberList;
	}

	public List<FutsalBoard> getTotalMyPageInfo(String nickName1, String nickName2) {
		// TODO Auto-generated method stub
		return mapper.selectTotalMyPageInfo(nickName1, nickName2);
	}

	public boolean modify(FutsalBoard runningBoard) {
		System.out.println(1234);
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
