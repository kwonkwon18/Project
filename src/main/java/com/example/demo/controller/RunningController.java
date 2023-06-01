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

		// 보드의 writer를 지정해줌
		// runningBoard.setWriter(authentication.getName());

		boolean ok = service.addBoard(runningBoard, authentication);

		if (ok) {

			return "redirect:/running/runningList";
		} else {
			return "redirect:/running/runningList";
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

		Map<String, Object> myPageList = new HashMap<>();

		// id 기준으로 리스트업 
		List<RunningBoard> runningBoards = service.getMyPageInfo(authentication.getName());
		myPageList.put("runningBoards", runningBoards);
		System.out.println(runningBoards);

		// 참여자들 리스트업 
		List<RunningParty> members = service.getJoinMember(authentication.getName());
		myPageList.put("members", members);
		System.out.println(members);

		model.addAllAttributes(myPageList);

	}

	@GetMapping("/runningMate")
	public void runningMatePage(Model model, Authentication authentication) {

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
	


}
