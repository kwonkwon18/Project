package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.domain.ClimbingMate;
import com.example.demo.domain.Member;
import com.example.demo.domain.RunningBoard;
import com.example.demo.domain.RunningParty;
import com.example.demo.domain.RunningToday;
import com.example.demo.service.RunningPartyService;
import com.example.demo.service.RunningService;
import com.example.demo.service.RunningTodayService;

@Controller
@RequestMapping("running")
public class RunningController {

	@Autowired
	private RunningService service;

	@Autowired
	private RunningPartyService partyService;

	@Autowired
	private RunningTodayService todayService;

	@GetMapping("/runningList")
	public void list(Model model) {

		Map<String, Object> listMap = new HashMap<>();

		// 메이트 모집
		List<RunningBoard> list = service.listBoard(); // 페이지 처리 전
		listMap.put("boardList", list);

		// 오늘의 러닝
		List<RunningToday> today = todayService.listBoard();
		listMap.put("todayList", today);

		model.addAllAttributes(listMap);

	}

	@GetMapping("/runningAdd")
	public void addProcess() {

	}

	@PostMapping("/runningAdd")
	public String addResult(RunningBoard runningBoard, RedirectAttributes trrt, Authentication authentication) {

		boolean ok = service.addBoard(runningBoard, authentication);

		if (ok) {
			return "redirect:/running/runningList";
		} else {
			return "redirect:/running/runningAdd";
		}
	}

	@GetMapping("/id/{id}")
	public String detail(@PathVariable("id") Integer id, Model model, String writer, Authentication authentication) {

		Map<String, Object> getMemberList = new HashMap<>();

		RunningBoard getList = service.getBoard(id);
		getMemberList.put("board", getList);

		List<RunningParty> members = service.selectMemberIdByBoardId(id, getList.getWriter());
		getMemberList.put("members", members);
		System.out.println(members);

		List<Member> memberList = service.getUserId(authentication.getName());
		getMemberList.put("memberList", memberList);

		model.addAllAttributes(getMemberList);

		return "running/runningGet";
	}

	@GetMapping("/runningToday")
	public void addrunningShare() {

	}

	@PostMapping("/runningToday")
	public String addrunningShareResult(@RequestParam("files") MultipartFile[] files, RunningToday runningToday,
			RedirectAttributes rttr) throws Exception {

		boolean ok = todayService.addRunningToday(runningToday, files);

		if (ok) {
			return "redirect:/running/runningList";
		} else {
			return "redirect:/running/runningToday";
		}

	}

	@GetMapping("/todayId/{id}")
	public String detailToday(@PathVariable("id") Integer id, Model model) {

		RunningToday getList = todayService.getBoard(id);

		model.addAttribute("board", getList);

		return "running/runningTodayGet";
	}

	@GetMapping("/myPage")
	public void runningMyPage(Authentication authentication, Model model) {

		Member member = service.getMembertUserId(authentication.getName());
		System.out.println("접근함");

		Map<String, Object> myPageList = new HashMap<>();

		myPageList.put("MyNickName", member.getNickName());

		List<RunningBoard> totalMyData = service.getTotalMyPageInfo(member.getNickName(), member.getNickName());
		System.out.println("***" + totalMyData);
		myPageList.put("totalMyData", totalMyData);

		// id 기준으로 리스트업
//		List<RunningBoard> runningBoards = service.getMyPageInfo(authentication.getName());
//		myPageList.put("runningBoards", runningBoards);
//		System.out.println(runningBoards);

		// 참여자들 리스트업
		List<RunningParty> members = service.getJoinMember(member.getNickName());
		myPageList.put("members", members);
		System.out.println("멤버스 : " + members);

		model.addAllAttributes(myPageList);

	}

	// 여기서 List<String> Mapper 써줄 것임
	@GetMapping("/runningMate")
	public void runningMatePage(Model model, Authentication authentication,
			@RequestParam(value = "type", required = false) String type) {

		System.err.println("접근 1");

		Map<String, Object> getMemberList = new HashMap<>();

		List<RunningBoard> runningMates = service.getMateBoardByAddress(authentication, type);
		getMemberList.put("runningMates", runningMates);

		/* model.addAttribute("board", runningMates); */
		System.out.println(runningMates);

		List<RunningParty> members = service.selectMemberIdByBoardId();
		getMemberList.put("members", members);

		// 현재 로그인한 사람의 닉네임을 넘겨줘야함
		List<Member> memberList = service.getUserId(authentication.getName());
		getMemberList.put("memberList", memberList);

		model.addAllAttributes(getMemberList);
	}

	@GetMapping("/runningMate1")
	public void runningMatePage1(Model model, Authentication authentication) {

		Map<String, Object> getMemberList = new HashMap<>();

		List<RunningBoard> runningMates = service.getMateBoard();
		getMemberList.put("runningMates", runningMates);

		/* model.addAttribute("board", runningMates); */
		System.out.println(runningMates);

		List<RunningParty> members = service.selectMemberIdByBoardId();
		getMemberList.put("members", members);

		// 현재 로그인한 사람의 닉네임을 넘겨줘야함
		List<Member> memberList = service.getUserId(authentication.getName());
		getMemberList.put("memberList", memberList);

		model.addAllAttributes(getMemberList);
	}

	@GetMapping("/runningModify/{id}")
	public String runningModifyForm(@PathVariable("id") Integer id, Model model, String writer,
			Authentication authentication) {

		Map<String, Object> getMemberList = new HashMap<>();

		RunningBoard getList = service.getBoard(id);
		getMemberList.put("board", getList);

		List<RunningParty> members = service.selectMemberIdByBoardId(id, getList.getWriter());
		getMemberList.put("members", members);
		System.out.println(members);

		List<Member> memberList = service.getUserId(authentication.getName());
		getMemberList.put("memberList", memberList);

		model.addAllAttributes(getMemberList);

		return "running/runningModify";
	}

	@PostMapping("/runningModify/{id}")
	public String runningModifyProcess(RunningBoard runningBoard, RedirectAttributes rttr) throws Exception {

		boolean ok = service.modify(runningBoard);

		if (ok) {
			// 해당 게시물 보기로 리디렉션
//			rttr.addAttribute("success", "success");
			rttr.addFlashAttribute("message", runningBoard.getId() + "번 게시물이 수정되었습니다.");
			return "redirect:/running/id/" + runningBoard.getId();
		} else {
			// 수정 form 으로 리디렉션
//			rttr.addAttribute("fail", "fail");
			rttr.addFlashAttribute("message", runningBoard.getId() + "번 게시물이 수정되지 않았습니다.");
			return "redirect:/running/runningModify/" + runningBoard.getId();
		}
	}

	@PostMapping("/runningRemove")
	public String runningRemove(Integer id, RedirectAttributes rttr) {
		boolean ok = service.remove(id);
		if (ok) {
			// query string에 추가
//			rttr.addAttribute("success", "remove");

			// 모델에 추가
			rttr.addFlashAttribute("message", id + "번 게시물이 삭제되었습니다.");
			return "redirect:/running/runningMate";
		} else {
			return "redirect:/running/runningModify/" + id;
		}
	}

	@GetMapping("/runningMap")
	public void runningMapProcess() {

	}

	// ******************** AJAX

	@PostMapping("joinParty")
	public ResponseEntity<Map<String, Object>> joinParty(@RequestBody RunningParty runningParty,
			Authentication authentication) {
		return ResponseEntity.ok().body(partyService.join(runningParty, authentication));

	}

	@PostMapping("rejectParty")
	public ResponseEntity<Map<String, Object>> rejectParty(@RequestBody RunningParty runningParty,
			Authentication authentication) {
		return ResponseEntity.ok().body(partyService.reject(runningParty, authentication));

	}

	@GetMapping("/getRunningDetail")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> detailForModal(Integer boardId, Authentication authentication) {

		return ResponseEntity.ok().body(service.getBoardForModal(boardId, authentication));

	}

	@GetMapping("/search")
	@ResponseBody
	public Map<String, Object> mapSearch(@RequestParam("search") String searchTerm) {
		Map<String, Object> listSearch = new HashMap<>();

		// 검색어를 이용하여 필요한 처리를 수행하고 결과를 listSearch에 저장합니다.
		// 예: DB에서 검색 쿼리를 수행하거나 다른 로직을 수행합니다.

		// 결과를 listSearch에 저장하여 클라이언트로 전달합니다.
		listSearch.put("result", service.searchMate(searchTerm));

		System.out.println(listSearch.get("result"));
		return listSearch;
	}

}
