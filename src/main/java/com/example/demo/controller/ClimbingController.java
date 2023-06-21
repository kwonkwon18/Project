package com.example.demo.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.security.access.prepost.*;
import org.springframework.security.core.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;
import org.springframework.web.servlet.mvc.support.*;

import com.example.demo.domain.*;
import com.example.demo.service.*;

@Controller
@RequestMapping("climbing")
public class ClimbingController {
	
	@Autowired
	private ClimbingMateService mateService;

	@Autowired
	private ClimbingTodayService todayService;

	@Autowired
	private ClimbingCourseService courseService;
	
	@Autowired
	private ClimbingPartyService partyService;


	@GetMapping("list")
	public void list(Model model, Authentication authentication) {

		Map<String, Object> listMap = new HashMap<>();

		// 메이트 구하기
		List<ClimbingMate> mate = mateService.listBoard(); // 페이지 처리 전
		listMap.put("climbingMateList", mate);

		// 오늘의 등산
		List<ClimbingToday> today = todayService.todayListBoard();
		listMap.put("climbingTodayList", today);

		// 추천 코스
		List<ClimbingCourse> course = courseService.listBoard();
		listMap.put("climbingCourseList", course);
		
		// 현재 로그인한 사람의 닉네임을 넘겨줘야함
		List<Member> memberList = mateService.getUserId(authentication.getName());
		listMap.put("memberList", memberList);
		
		System.out.println(memberList);

		model.addAllAttributes(listMap);

	}
	
	@GetMapping("mateList")
	public void mateList(Model model, Authentication authentication,
			@RequestParam(value = "type", required = false) String type, 
			@RequestParam(value = "mateSearch", defaultValue = "") String mateSearch) {
		
		System.err.println("접근 1");

		Map<String, Object> listMap = new HashMap<>();

		// 메이트 구하기
		List<ClimbingMate> mate = mateService.getMateBoardByAddress(authentication, type, mateSearch); // 페이지 처리 전
		listMap.put("climbingMateList", mate);
		
		List<ClimbingParty> members = mateService.selectMemberIdByBoardId();
		listMap.put("members", members);

		// 현재 로그인한 사람의 닉네임을 넘겨줘야함
		List<Member> memberList = mateService.getUserId(authentication.getName());
		listMap.put("memberList", memberList);
		System.out.println(memberList);
		
		model.addAllAttributes(listMap);
	}
	
	@GetMapping("/mateList1")
	public void climbingMatePage1(Model model, Authentication authentication) {

		Map<String, Object> getMemberList = new HashMap<>();

		List<ClimbingMate> climbingMates = mateService.getMateBoard();
		getMemberList.put("climbingMates", climbingMates);

		List<ClimbingParty> members = mateService.selectMemberIdByBoardId();
		getMemberList.put("members", members);

		// 현재 로그인한 사람의 닉네임을 넘겨줘야함
		List<Member> memberList = mateService.getUserId(authentication.getName());
		getMemberList.put("memberList", memberList);

		model.addAllAttributes(getMemberList);
	}

	@GetMapping("todayList")
	public void todayList(Model model, Authentication authentication,
			@RequestParam(value = "todaySearch", defaultValue = "") String todaySearch) {
		
		System.err.println("접근 2");
		
		Map<String, Object> listMap = new HashMap<>();		
		
		// 오늘의 등산
		List<ClimbingToday> today = todayService.listBoard(todaySearch); // 페이지 처리 전
		for(ClimbingToday i : today) {
			System.out.println(i.getCommentCount());
		}
		listMap.put("climbingTodayList", today);
		
		model.addAllAttributes(listMap);

	}
	
	@GetMapping("courseList")
	public void courseList(Model model, Authentication authentication,
			@RequestParam(value = "courseSearch", defaultValue = "") String courseSearch) {
		
		System.err.println("접근 3");
		
		Map<String, Object> listMap = new HashMap<>();
		
		// 추천 코스
		List<ClimbingCourse> course = courseService.listBoard(courseSearch); // 페이지 처리 전
		for(ClimbingCourse i : course) {
			System.out.println(i.getCommentCount());
		}
		listMap.put("climbingCourseList", course);
		
		model.addAllAttributes(listMap);
	}

	@GetMapping("/mateAdd")
	public void addProcess() {

	}

	@PostMapping("/mateAdd")
	public String addResult(ClimbingMate climbingMate, RedirectAttributes rttr, Authentication authentication) throws Exception {

		boolean ok = mateService.addClimbingMate(climbingMate, authentication.getName());

		if (ok) {
			return "redirect:/climbing/mateList";
		} else {
			return "redirect:/climbing/mateList";
		}
	}

//	@GetMapping("/mateId/{id}")
//	public String detail(@PathVariable("id") Integer id, Model model) {
//
//		ClimbingMate getList = mateService.getClimbingMate(id);
//
//		model.addAttribute("board", getList);
//
//		return "climbing/mateGet";
//	}

//	@GetMapping("/mateModify/{id}")
//	public String mateModifyForm(@PathVariable("id") Integer id, Model model) {
//		model.addAttribute("board", mateService.getClimbingMate(id));
//		return "climbing/mateModify";
//	}
	
	@GetMapping("/mateModify/{id}")
	public String climbingModifyForm(@PathVariable("id") Integer id, Model model, String writer,
			Authentication authentication) {

		Map<String, Object> getMemberList = new HashMap<>();

		ClimbingMate getList = mateService.getClimbingMate(id);
		getMemberList.put("board", getList);

		List<ClimbingParty> members = mateService.selectMemberIdByBoardId(id, getList.getWriter());
		getMemberList.put("members", members);
//		System.out.println(members);

		List<Member> memberList = mateService.getUserId(authentication.getName());
		getMemberList.put("memberList", memberList);

		model.addAllAttributes(getMemberList);

		return "climbing/mateModify";
	}

//	@RequestMapping(value = "/modify/{id}", method = RequestMethod.POST)
	@PostMapping("/mateModify/{id}")
	// 수정하려는 게시물 id : mate.id
	public String modifyProcess(ClimbingMate climbingMate, RedirectAttributes rttr) throws Exception {
		
		boolean ok = mateService.modify(climbingMate);

		if (ok) {
			// 해당 게시물 보기로 리디렉션
//			rttr.addAttribute("success", "success");
			rttr.addFlashAttribute("message", climbingMate.getId() + "번 게시물이 수정되었습니다.");
			return "redirect:/climbing/id/" + climbingMate.getId();
		} else {
			// 수정 form 으로 리디렉션
//			rttr.addAttribute("fail", "fail");
			rttr.addFlashAttribute("message", climbingMate.getId() + "번 게시물이 수정되지 않았습니다.");
			return "redirect:/climbing/mateModify/" + climbingMate.getId();
		}
	}
	
	@GetMapping("/id/{id}")
	public String detail(@PathVariable("id") Integer id, Model model, String writer, Authentication authentication) {

		Map<String, Object> getMemberList = new HashMap<>();

		ClimbingMate getList = mateService.getClimbingMate(id);
		getMemberList.put("board", getList);

		// 초대 수락된 멤버
		List<ClimbingParty> members = mateService.selectMemberIdByBoardId(id, getList.getWriter());
		getMemberList.put("members", members);
		System.out.println(members);
		
		// 초대 대기멤버
		List<ClimbingParty> waitingMembers = mateService.selectWaitingMemberIdByBoardId(id, getList.getWriter());
		getMemberList.put("waitingMembers", waitingMembers);
		
		// 거절 멤버
		List<ClimbingParty> rejectMembers = mateService.selectRejectMemberIdByBoardId(id, getList.getWriter());
		getMemberList.put("rejectMembers", rejectMembers);
		
		List<Member> memberList = mateService.getUserId(authentication.getName());
		getMemberList.put("memberList", memberList);
		

		model.addAllAttributes(getMemberList);

		return "climbing/mateGet";
	}
	
	@PostMapping("/mateRemove")
	public String mateRemove(Integer id, RedirectAttributes rttr) {
		boolean ok = mateService.remove(id);
		if (ok) {
			// query string에 추가
//			rttr.addAttribute("success", "remove");

			// 모델에 추가
			rttr.addFlashAttribute("message", id + "번 게시물이 삭제되었습니다.");

			return "redirect:/climbing/mateList";
		} else {
			return "redirect:/climbing/climbingModify/" + id;
		}
	}
	
	@GetMapping("/mateMap")
	public void mateMapProcess() {

	}

	
//	@GetMapping("/mateMap")
//	public void mateMap(Model model) {
//		Map<String, Object> listMap = new HashMap<>();
//
//		// 메이트 구하기
//		List<ClimbingMate> mate = mateService.listBoard(); // 페이지 처리 전
//		listMap.put("climbingMateList", mate);
//		
//		model.addAllAttributes(listMap);
//	}
	
//	@GetMapping("/climbingMate")
//	public void climbingMatePage(Model model) {
//		
//		Map<String, Object> getMemberList = new HashMap<>();
//		
//		List<ClimbingMate> climbingMates = mateService.getMateBoard();
//		getMemberList.put("climbingMates", climbingMates);
//
//		/* model.addAttribute("board", climbingMates); */
//		System.out.println(climbingMates);
//		
//		List<ClimbingParty> members = mateService.selectMemberIdByBoardId();
//		getMemberList.put("members", members);
//		System.out.println(members);
//		model.addAllAttributes(getMemberList);
//	}
	
	@GetMapping("/mateSearch")
	@ResponseBody
	public Map<String, Object> mateSearch(@RequestParam("search") String searchTerm) {
	    Map<String, Object> listSearch = new HashMap<>();
	    System.out.println(searchTerm);
	    System.out.println("도착함");
	    // 검색어를 이용하여 필요한 처리를 수행하고 결과를 listSearch에 저장합니다.
	    // 예: DB에서 검색 쿼리를 수행하거나 다른 로직을 수행합니다.
	    
	    
	    // 결과를 listSearch에 저장하여 클라이언트로 전달합니다.
	    listSearch.put("result", mateService.searchMate(searchTerm));
	    
	    System.out.println("gkgkgkgk" + listSearch.get("result"));
	    return listSearch;
	}
	
	@GetMapping("/myPage")
	public void climbingMyPageGet(Authentication authentication, Model model) {

		// 로그인 닉네임 확인
		Member member = mateService.getMemberUserId(authentication.getName());

		Map<String, Object> myPageList = new HashMap<>();

		myPageList.put("MyNickName", member.getNickName());
		
		List<ClimbingMate> totalMyData = mateService.getTotalMyPageInfo(member.getNickName());
		myPageList.put("totalMyData", totalMyData);
		System.out.println(totalMyData);
		
		// 참여자들 리스트업
		List<ClimbingParty> members = mateService.getJoinMember(member.getNickName());
		myPageList.put("members", members);
//		System.out.println("멤버스 : " + members);
		model.addAllAttributes(myPageList);
	}
	
	@GetMapping("/myPageJs")
	@ResponseBody
	public Map<String, Object> climbingMyPage(Authentication authentication) {

		// 로그인 닉네임 확인
		Member member = mateService.getMemberUserId(authentication.getName());

		Map<String, Object> myPageList = new HashMap<>();

		myPageList.put("MyNickName", member.getNickName());
		
		List<ClimbingMate> totalMyData = mateService.getTotalMyPageInfo(member.getNickName());
		myPageList.put("totalMyData", totalMyData);
		System.out.println(totalMyData);
		
		// 참여자들 리스트업
		List<ClimbingParty> members = mateService.getJoinMember(member.getNickName());
		myPageList.put("members", members);
//		System.out.println("멤버스 : " + members);

		return myPageList;
	}

	@GetMapping("/getClimbingDetail")
	@ResponseBody
	public ResponseEntity<Object> detailForModal(Integer boardId, Authentication authentication) {
		
		return ResponseEntity.ok().body(mateService.getBoardForModal(boardId, authentication));
		
	}
	
	@PostMapping("joinParty")
	public ResponseEntity<Map<String, Object>> joinParty(@RequestBody ClimbingParty climbingParty,
			Authentication authentication) {
		return ResponseEntity.ok().body(partyService.join(climbingParty, authentication));

	}

	@PostMapping("rejectParty")
	public ResponseEntity<Map<String, Object>> rejectParty(@RequestBody ClimbingParty climbingParty,
			Authentication authentication) {
		return ResponseEntity.ok().body(partyService.reject(climbingParty, authentication));

	}
	

	@GetMapping("/todayAdd")
	public void todayProcess() {

	}

	@PostMapping("/todayAdd")
	public String TodayResult(
			ClimbingToday climbingToday,
			RedirectAttributes rttr,
			@RequestParam("files") MultipartFile[] files, Authentication authentication) throws Exception {

		boolean ok = todayService.addClimbingToday(climbingToday, files, authentication);

		if (ok) {
			return "redirect:/climbing/todayList";
		} else {
			return "redirect:/climbing/todayList";
		}
	}

	@GetMapping("/todayId/{id}")
	public String Todaydetail(@PathVariable("id") Integer id, Model model, Authentication authentication) {

		ClimbingToday todayList = todayService.getClimbingToday(id, authentication.getName());

		model.addAttribute("board", todayList);

		return "climbing/todayGet";

	}
	
	@GetMapping("/todayModify/{id}")
	public String todayModifyForm(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("board", todayService.getClimbingToday(id, null));
		return "climbing/todayModify";
	}

	
//	@RequestMapping(value = "/modify/{id}", method = RequestMethod.POST)
	@PostMapping("/todayModify/{id}")
	// 수정하려는 게시물 id : mate.id
	public String modifyProcess(ClimbingToday climbingToday,
			@RequestParam(value = "removeFiles", required = false) List<String> removeFileNames,
			@RequestParam(value = "files", required = false) MultipartFile[] addFiles,
			RedirectAttributes rttr) throws Exception {
		
		boolean ok = todayService.modify(climbingToday, addFiles, removeFileNames);

		if (ok) {
			// 해당 게시물 보기로 리디렉션
//			rttr.addAttribute("success", "success");
			rttr.addFlashAttribute("message", climbingToday.getId() + "번 게시물이 수정되었습니다.");
			return "redirect:/climbing/todayId/" + climbingToday.getId();
		} else {
			// 수정 form 으로 리디렉션
//			rttr.addAttribute("fail", "fail");
			rttr.addFlashAttribute("message", climbingToday.getId() + "번 게시물이 수정되지 않았습니다.");
			return "redirect:/climbing/todayId/" + climbingToday.getId();
		}
	}
	
	
	@PostMapping("/todayRemove")
	public String remove(Integer id, RedirectAttributes rttr) {
		boolean ok = todayService.remove(id);
		if (ok) {
			// query string에 추가
//			rttr.addAttribute("success", "remove");

			// 모델에 추가
			rttr.addFlashAttribute("message", id + "번 게시물이 삭제되었습니다.");

			return "redirect:/climbing/todayList";
		} else {
			return "redirect:/climbing/todayModify/" + id;
		}
	}
	
	@GetMapping("/todaySearch")
	@ResponseBody
	public Map<String, Object> todaySearch(@RequestParam("todaySearch") String searchTerm) {
	    Map<String, Object> listSearch = new HashMap<>();
	    
	    
	    // 검색어를 이용하여 필요한 처리를 수행하고 결과를 listSearch에 저장합니다.
	    // 예: DB에서 검색 쿼리를 수행하거나 다른 로직을 수행합니다.
	    
	    
	    // 결과를 listSearch에 저장하여 클라이언트로 전달합니다.
	    listSearch.put("result", todayService.searchToday(searchTerm));
	    
	    System.out.println(listSearch.get("result"));
	    return listSearch;
	}


	@GetMapping("/courseAdd")
	public void courseProcess() {

	}

	@PostMapping("/courseAdd")
	public String CourseResult(
			ClimbingCourse climbingCourse,
			RedirectAttributes rttr,
			@RequestParam("files") MultipartFile[] files, Authentication authentication) throws Exception {

		boolean ok = courseService.addClimbingCourse(climbingCourse, files, authentication);

		if (ok) {
			return "redirect:/climbing/courseList";
		} else {
			return "redirect:/climbing/courseList";
		}
	}

	@GetMapping("/courseId/{id}")
	public String Coursedetail(@PathVariable("id") Integer id, Model model, Authentication authentication) {

		ClimbingCourse courseList = courseService.getClimbingCourse(id, authentication.getName());

		model.addAttribute("board", courseList);

		return "climbing/courseGet";

	}

	@GetMapping("/courseModify/{id}")
	public String courseModifyForm(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("board", courseService.getClimbingCourse(id, null));
		return "climbing/courseModify";
	}

//	@RequestMapping(value = "/modify/{id}", method = RequestMethod.POST)
	@PostMapping("/courseModify/{id}")
	
	// 수정하려는 게시물 id : mate.id
	public String courseModifyProcess(ClimbingCourse climbingCourse,
			@RequestParam(value = "files", required = false) MultipartFile[] addFiles,
			@RequestParam(value = "removeFiles", required = false) List<String> removeFileNames,
			RedirectAttributes rttr) throws Exception {
		
		boolean ok = courseService.modify(climbingCourse, addFiles, removeFileNames);

		if (ok) {
			// 해당 게시물 보기로 리디렉션
//			rttr.addAttribute("success", "success");
			rttr.addFlashAttribute("message", climbingCourse.getId() + "번 게시물이 수정되었습니다.");
			return "redirect:/id/" + climbingCourse.getId();
		} else {
			// 수정 form 으로 리디렉션
//			rttr.addAttribute("fail", "fail");
			rttr.addFlashAttribute("message", climbingCourse.getId() + "번 게시물이 수정되지 않았습니다.");
			return "redirect:/courseModify/" + climbingCourse.getId();
		}
	}
	
	
	@PostMapping("/courseRemove")
	public String courseRemove(Integer id, RedirectAttributes rttr) {
		boolean ok = todayService.remove(id);
		if (ok) {
			// query string에 추가
//			rttr.addAttribute("success", "remove");

			// 모델에 추가
			rttr.addFlashAttribute("message", id + "번 게시물이 삭제되었습니다.");

			return "redirect:/climbing/courseList";
		} else {
			return "redirect:/courseId/" + id;
		}
	}
	
	@GetMapping("/courseListArea")
	public void courseListArea() {
		
	}
	
	@PostMapping("/climbingTodayLike")
	// responseEntitiy를 해주는 이유는 에러 메시지를 함께 보내주기 위함이다.
	// @RequestBody Like like를 해준 것은 like에 있는 인자들을 json으로 보내주기 위함
	// 또한 등록 된 사람들만 like를 할 수 있게 하게 위해서 Authentication 을 인자로 추가해주었다.
	// 홈페이지의 대부분의 정보는 nickName 활용하였지만, like는 userId를 쓰기로 함
	public ResponseEntity<Map<String, Object>> like(@RequestBody ClimbingTodayLike like, Authentication auth) {
		System.out.println(like);
		System.out.println(auth);

		if (auth == null) {
			// 만약에 인증되지 않은 사용자가 들어왔으면...
			return ResponseEntity.status(403) // 상태값 반환
					.body(Map.of("message", "로그인 후 좋아요 클릭 해주세요")); // body에 해당 Map을 담아서
			// 넘긴다. 이때 넘길 때는 js에 넘겨서 비동기 처리가 된다.

		} else {

			return ResponseEntity.ok().body(todayService.like(like, auth));
		}

	}
	
	@PostMapping("/climbingCourseLike")
	// responseEntitiy를 해주는 이유는 에러 메시지를 함께 보내주기 위함이다.
	// @RequestBody Like like를 해준 것은 like에 있는 인자들을 json으로 보내주기 위함
	// 또한 등록 된 사람들만 like를 할 수 있게 하게 위해서 Authentication 을 인자로 추가해주었다.
	// 홈페이지의 대부분의 정보는 nickName 활용하였지만, like는 userId를 쓰기로 함
	public ResponseEntity<Map<String, Object>> like(@RequestBody ClimbingCourseLike like, Authentication auth) {
		System.out.println(like);
		System.out.println(auth);

		if (auth == null) {
			// 만약에 인증되지 않은 사용자가 들어왔으면...
			return ResponseEntity.status(403) // 상태값 반환
					.body(Map.of("message", "로그인 후 좋아요 클릭 해주세요")); // body에 해당 Map을 담아서
			// 넘긴다. 이때 넘길 때는 js에 넘겨서 비동기 처리가 된다.

		} else {

			return ResponseEntity.ok().body(courseService.like(like, auth));
		}

	}

	@GetMapping("alarm")
	@PreAuthorize("authenticated")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> alarm(ClimbingParty climbingParty, Authentication authentication) {
		return ResponseEntity.ok().body(partyService.alarm(climbingParty, authentication));
	}

	@PostMapping("agreeParty")
	@PreAuthorize("authenticated")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> agreeParty(@RequestBody ClimbingParty climbingParty,
			Authentication authentication) {
		return ResponseEntity.ok().body(partyService.agreeParty(climbingParty, authentication));
	}

	@PostMapping("disagreeParty")
	@PreAuthorize("authenticated")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> disagreeParty(@RequestBody ClimbingParty climbingParty,
			Authentication authentication) {
		return ResponseEntity.ok().body(partyService.disagreeParty(climbingParty, authentication));
	}
	
	@PostMapping("confirmation")
	@PreAuthorize("authenticated")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> confirmationGood(@RequestBody ClimbingParty climbingParty,
			Authentication authentication) {
		return ResponseEntity.ok().body(partyService.confirmation(climbingParty, authentication));
	}
	
	@GetMapping("countOfAlarm")
	@PreAuthorize("authenticated")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> countOfAlarm(Authentication authentication) {
		return ResponseEntity.ok().body(partyService.countOfAlarm(authentication));
	}
	
	@GetMapping("restaurant")
	public void restaurant() {
		
	}

}
