package com.example.demo.service;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import com.example.demo.domain.*;
import com.example.demo.mapper.*;

import software.amazon.awssdk.services.s3.*;


@Service
@Transactional(rollbackFor = Exception.class)
public class ClimbingMateService {
	
	@Autowired
	private S3Client s3;
	
	@Value("${aws.s3.bucketName}")
	private String bucketName;
	
	@Autowired
	private ClimbingMateMapper mateMapper;
	
	@Autowired
	private MemberMapper memberMapper;
	
	@Autowired
	private ClimbingPartyMapper partyMapper;
	
	public boolean addClimbingMate(ClimbingMate climbingMate, String myId) {
		climbingMate.setWriter(memberMapper.getNickNameByUserId(myId));
		int cnt = mateMapper.insert(climbingMate);
		return cnt == 1;
	}
	
	public List<ClimbingMate> listBoard() {
		
		return mateMapper.selectList();
	}

	public ClimbingMate getClimbingMate(Integer id) {

		return mateMapper.selectById(id);
	}

	public boolean modify(ClimbingMate climbingMate) {
		System.out.println(1234);
		return mateMapper.updateBoardById(climbingMate);
	}
	
	public boolean remove(Integer id) {

		// 참조키 제약 사항으로 러닝파티에서 boardId에 해당하는 것들을 지워함
		int cnt = partyMapper.deleteByBoardId(id);

		return mateMapper.deleteById(id);
	}
//	public boolean modify(ClimbingMate climbingMate) throws Exception {
//
//		// FileName 테이블 삭제
//		if (removeFileNames != null && !removeFileNames.isEmpty()) {
//			for (String fileName : removeFileNames) {
//				// s3에서 파일(객체) 삭제
//				String objectKey = "project/climbingMate/" + climbingMate.getId() + "/" + fileName;
//				DeleteObjectRequest dor = DeleteObjectRequest.builder()
//						.bucket(bucketName)
//						.key(objectKey)
//						.build();
//				s3.deleteObject(dor);
//
//				// 테이블에서 삭제
//				mateMapper.deleteFileNameByBoardIdAndFileName(climbingMate.getId(), fileName);
//			}
//		}
//
//		// 새 파일 추가
//		for (MultipartFile newFile : addFiles) {
//			if (newFile.getSize() > 0) {
//				// 테이블에 파일명 추가
//				mateMapper.insertFileName(climbingMate.getId(), newFile.getOriginalFilename());
//
//				// s3에 파일(객체) 업로드
//				String objectKey = "project/climbingMate/" + climbingMate.getId() + "/" + newFile.getOriginalFilename();
//				PutObjectRequest por = PutObjectRequest.builder()
//						.acl(ObjectCannedACL.PUBLIC_READ)
//						.bucket(bucketName)
//						.key(objectKey)
//						.build();
//				RequestBody rb = RequestBody.fromInputStream(newFile.getInputStream(), newFile.getSize());
//				s3.putObject(por, rb);
//			}
//		}
//
//		// 게시물(Board) 테이블 수정
//		int cnt = mateMapper.update(climbingMate);
//
//		return cnt == 1;
//	}
//
//	public boolean remove(Integer id) {
//
//	// 파일명 조회
//	List<String> fileNames = mateMapper.selectFileNamesByBoardId(id);
//	
//	// FileName 테이블의 데이터 지우기
//	mateMapper.deleteFileNameByMateId(id);
//	
//	// s3 bucket의 파일(객체) 지우기
//	for (String fileName : fileNames) {
//		String objectKey = "project/climbingMate/" + id + "/" + fileName;
//		DeleteObjectRequest dor = DeleteObjectRequest.builder()
//				.bucket(bucketName)
//				.key(objectKey)
//				.build();
//		s3.deleteObject(dor);
//
//	}
//		int cnt = mateMapper.deleteById(id);
//	
//		return cnt == 1;
//	}

	public List<ClimbingMate> getMateBoard() {

		return mateMapper.selectMate();
	}
	
	public List<ClimbingMate> getMyPageInfo(String writer) {
		
		return mateMapper.selectMyPageInfo(writer);
	}


	public List<ClimbingParty> selectMemberIdByBoardId(Integer id, String writer) {

		return mateMapper.selectMemberIdByBoardId(id, writer);
	}

	public List<ClimbingParty> selectMemberIdByBoardId() {

		return mateMapper.selectMember();
	}

	public List<ClimbingMate> searchMate(String searchTerm) {

		return mateMapper.selectBySearchTerm(searchTerm);
	}
	
	public List<Member> getUserId(String userId) {

		return mateMapper.selectUserId(userId);
	}

	public Map<String, Object> getBoardForModal(Integer boardId, Authentication authentication) {
		
		Map<String, Object> getMemberList = new HashMap<>();

		// board에 대한 정보가 들어감
		ClimbingMate getList = mateMapper.selectById(boardId);
		getMemberList.put("board", getList);

		// 신청자가 들어감
		List<ClimbingParty> members = mateMapper.selectForMemberIdByBoardId(boardId);
		getMemberList.put("members", members);
		
		// 대기자가 들어감
		List<ClimbingParty> waitingMembers = mateMapper.selectWaitingMemberIdByBoardIdForModal(boardId);
		getMemberList.put("waitingMembers", waitingMembers);
		
		// 거절멤버
		List<ClimbingParty> rejectMembers = mateMapper.selectRejectMemberIdByBoardIdForModal(boardId);
		getMemberList.put("rejectMembers", rejectMembers);

		// 중복 신청 방지용
		List<Member> memberList = getUserId(authentication.getName());
		getMemberList.put("memberList", memberList);

		// 로그인한 사람 확인용 (닉네임)
		Member myNickName = mateMapper.getNickName(authentication.getName());
		getMemberList.put("myNickName", myNickName);
		
		return getMemberList;
	}

	public List<ClimbingMate> getMateBoardByAddress(Authentication authentication, String type, String mateSearch) {
		
		if(authentication != null) {

		// 유저 정보 가져옴 Member member =
		Member member = mateMapper.selectMemberById(authentication.getName()); // 유저 정보 중 주소 정보 변수 저장
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

		return mateMapper.selectMateByDistance(addressList, type, mateSearch);
	}
		
		return null;
	}

	public Member getMemberUserId(String userId) {

		return mateMapper.selecMemberUserId(userId);
	}

	public List<ClimbingMate> getTotalMyPageInfo(String nickName) {

		return mateMapper.selectTotalMyPageInfo(nickName);
	}

	public List<ClimbingParty> getJoinMember(String writer) {

		return mateMapper.selectMemberId(writer);
	}

	public List<ClimbingParty> selectWaitingMemberIdByBoardId(Integer id, String writer) {
		// TODO Auto-generated method stub
		return mateMapper.selectWaitingMemberIdByBoardId(id, writer);
	}

	public List<ClimbingParty> selectRejectMemberIdByBoardId(Integer id, String writer) {

		return mateMapper.selectRejectMemberIdByBoardId(id, writer);
	}


}


