package com.example.demo.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
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
	public void list(Model model) {

		Map<String, Object> listMap = new HashMap<>();

		// 메이트 구하기
		List<ClimbingMate> mate = mateService.listBoard(); // 페이지 처리 전
		listMap.put("climbingMateList", mate);

		// 오늘의 등산
		List<ClimbingToday> today = todayService.listBoard();
		listMap.put("climbingTodayList", today);

		// 추천 코스
		List<ClimbingCourse> course = courseService.listBoard();
		listMap.put("climbingCourseList", course);

		model.addAllAttributes(listMap);

	}
	
	@GetMapping("mateList")
	public void mateList(Model model, Authentication authentication,
			@RequestParam(value = "type", required = false) String type, 
			@RequestParam(value = "search", defaultValue = "") String search) {
		
		System.err.println("접근 1");

		Map<String, Object> listMap = new HashMap<>();

		// 메이트 구하기
		List<ClimbingMate> mate = mateService.getMateBoardByAddress(authentication, type, search); // 페이지 처리 전
		listMap.put("climbingMateList", mate);
		
		List<ClimbingParty> members = mateService.selectMemberIdByBoardId();
		listMap.put("members", members);

		// 현재 로그인한 사람의 닉네임을 넘겨줘야함
		List<Member> memberList = mateService.getUserId(authentication.getName());
		listMap.put("memberList", memberList);
		
		model.addAllAttributes(listMap);
	}

	@GetMapping("todayList")
	public void todayList(Model model
//			@RequestParam(value = "page", defaultValue = "1") Integer page,
//			@RequestParam(value = "search", defaultValue = "") String search,
//			@RequestParam(value = "type", required = false) String type) 
			) {
		
		Map<String, Object> listMap = new HashMap<>();		
		// 오늘의 등산
		List<ClimbingToday> today = todayService.listBoard(); // 페이지 처리 전
		listMap.put("climbingTodayList", today);
		
		model.addAllAttributes(listMap);

	}
	
	@GetMapping("courseList")
	public void courseList(Model model) {
		Map<String, Object> listMap = new HashMap<>();
		
		// 추천 코스
		List<ClimbingCourse> course = courseService.listBoard(); // 페이지 처리 전
		listMap.put("climbingCourseList", course);

		model.addAllAttributes(listMap);
	}

	@GetMapping("/mateAdd")
	public void addProcess() {

	}

	@PostMapping("/mateAdd")
	public String addResult(ClimbingMate climbingMate, RedirectAttributes rttr) throws Exception {

		boolean ok = mateService.addClimbingMate(climbingMate);

		if (ok) {
			return "redirect:/climbing/mateList";
		} else {
			return "redirect:/climbing/mateList";
		}
	}

	@GetMapping("/mateId/{id}")
	public String detail(@PathVariable("id") Integer id, Model model) {

		ClimbingMate getList = mateService.getClimbingMate(id);

		model.addAttribute("board", getList);

		return "climbing/mateGet";
	}

	@GetMapping("/mateModify/{id}")
	public String mateModifyForm(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("board", mateService.getClimbingMate(id));
		return "climbing/mateModify";
	}

//	@RequestMapping(value = "/modify/{id}", method = RequestMethod.POST)
	@PostMapping("/mateModify/{id}")
	
	// 수정하려는 게시물 id : mate.id
	public String modifyProcess(ClimbingMate climbingMate,
			@RequestParam(value = "files", required = false) MultipartFile[] addFiles,
			@RequestParam(value = "removeFiles", required = false) List<String> removeFileNames,
			RedirectAttributes rttr) throws Exception {
		
		boolean ok = mateService.modify(climbingMate, addFiles, removeFileNames);

		if (ok) {
			// 해당 게시물 보기로 리디렉션
//			rttr.addAttribute("success", "success");
			rttr.addFlashAttribute("message", climbingMate.getId() + "번 게시물이 수정되었습니다.");
			return "redirect:/id/" + climbingMate.getId();
		} else {
			// 수정 form 으로 리디렉션
//			rttr.addAttribute("fail", "fail");
			rttr.addFlashAttribute("message", climbingMate.getId() + "번 게시물이 수정되지 않았습니다.");
			return "redirect:/mateModify/" + climbingMate.getId();
		}
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
			return "redirect:/mateId/" + id;
		}
	}
	
	@GetMapping("/mateMap")
	public void mateMap(Model model) {
		Map<String, Object> listMap = new HashMap<>();

		// 메이트 구하기
		List<ClimbingMate> mate = mateService.listBoard(); // 페이지 처리 전
		listMap.put("climbingMateList", mate);
		
		model.addAllAttributes(listMap);
	}
	
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
	
	@GetMapping("/search")
	@ResponseBody
	public Map<String, Object> mateSearch(@RequestParam("search") String searchTerm) {
	    Map<String, Object> listSearch = new HashMap<>();
	    
	    
	    // 검색어를 이용하여 필요한 처리를 수행하고 결과를 listSearch에 저장합니다.
	    // 예: DB에서 검색 쿼리를 수행하거나 다른 로직을 수행합니다.
	    
	    
	    // 결과를 listSearch에 저장하여 클라이언트로 전달합니다.
	    listSearch.put("result", mateService.searchMate(searchTerm));
	    
	    System.out.println(listSearch.get("result"));
	    return listSearch;
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
			@RequestParam("files") MultipartFile[] files) throws Exception {

		boolean ok = todayService.addClimbingToday(climbingToday, files);

		if (ok) {
			return "redirect:/climbing/todayList";
		} else {
			return "redirect:/climbing/todayList";
		}
	}

	@GetMapping("/todayId/{id}")
	public String Todaydetail(@PathVariable("id") Integer id, Model model) {

		ClimbingToday todayList = todayService.getClimbingToday(id);

		model.addAttribute("board", todayList);

		return "climbing/todayGet";

	}
	
	@GetMapping("/todayModify/{id}")
	public String modifyForm(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("board", todayService.getClimbingToday(id));
		return "climbing/mateModify";
	}

//	@RequestMapping(value = "/modify/{id}", method = RequestMethod.POST)
	@PostMapping("/todayModify/{id}")
	
	// 수정하려는 게시물 id : mate.id
	public String modifyProcess(ClimbingToday climbingToday,
			@RequestParam(value = "files", required = false) MultipartFile[] addFiles,
			@RequestParam(value = "removeFiles", required = false) List<String> removeFileNames,
			RedirectAttributes rttr) throws Exception {
		
		boolean ok = todayService.modify(climbingToday, addFiles, removeFileNames);

		if (ok) {
			// 해당 게시물 보기로 리디렉션
//			rttr.addAttribute("success", "success");
			rttr.addFlashAttribute("message", climbingToday.getId() + "번 게시물이 수정되었습니다.");
			return "redirect:/id/" + climbingToday.getId();
		} else {
			// 수정 form 으로 리디렉션
//			rttr.addAttribute("fail", "fail");
			rttr.addFlashAttribute("message", climbingToday.getId() + "번 게시물이 수정되지 않았습니다.");
			return "redirect:/todayModify/" + climbingToday.getId();
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
			return "redirect:/todayId/" + id;
		}
	}


	@GetMapping("/courseAdd")
	public void courseProcess() {

	}

	@PostMapping("/courseAdd")
	public String CourseResult(
			ClimbingCourse climbingCourse,
			RedirectAttributes rttr,
			@RequestParam("files") MultipartFile[] files) throws Exception {

		boolean ok = courseService.addClimbingCourse(climbingCourse, files);

		if (ok) {
			return "redirect:/climbing/courseList";
		} else {
			return "redirect:/climbing/courseList";
		}
	}

	@GetMapping("/courseId/{id}")
	public String Coursedetail(@PathVariable("id") Integer id, Model model) {

		ClimbingCourse courseList = courseService.getBoard(id);

		model.addAttribute("board", courseList);

		return "climbing/courseGet";

	}

}
