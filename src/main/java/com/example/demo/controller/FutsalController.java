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
@RequestMapping("futsal")
public class FutsalController {

	@Autowired
	private FutsalService service;

	@Autowired
	private FutsalPartyService partyService;

	@Autowired
	private FutsalTodayService todayService;

	@GetMapping("/futsalList")
	public void list(Model model) {

		Map<String, Object> listMap = new HashMap<>();

		// 메이트 모집
		List<FutsalBoard> list = service.listBoard(); // 페이지 처리 전
		listMap.put("boardList", list);

		// 오늘의 러닝
		List<FutsalToday> today = todayService.listBoard();
		listMap.put("todayList", today);

		model.addAllAttributes(listMap);

	}

	@GetMapping("/futsalAdd")
	public void addProcess() {

	}

	@PostMapping("/futsalAdd")
	public String addResult(FutsalBoard futsalBoard, RedirectAttributes rttr, Authentication authentication) {

		boolean ok = service.addBoard(futsalBoard, authentication);

		if (ok) {
			rttr.addFlashAttribute("message", futsalBoard.getTitle() + "  게시물이 등록되었습니다.");
			return "redirect:/futsal/futsalMate";
		} else {
			rttr.addFlashAttribute("message", "게시물 등록 실패 !! ");
			return "redirect:/futsal/futsalAdd";
		}
	}

	@GetMapping("/id/{id}")
	public String detail(@PathVariable("id") Integer id, Model model, String writer, Authentication authentication) {

		Map<String, Object> getMemberList = new HashMap<>();

		FutsalBoard getList = service.getBoard(id);
		getMemberList.put("board", getList);

		List<FutsalParty> members = service.selectMemberIdByBoardId(id, getList.getWriter());
		getMemberList.put("members", members);
//		System.out.println(members);

		List<Member> memberList = service.getUserId(authentication.getName());
		getMemberList.put("memberList", memberList);

		model.addAllAttributes(getMemberList);

		return "futsal/futsalGet";
	}

	@GetMapping("/futsalToday")
	public void addfutsalShare(Authentication authentication, Model model) {

		
	}

	
	@PostMapping("/futsalToday")
	public String addfutsalShareResult(@RequestParam("files") MultipartFile[] files, FutsalToday futsalToday,
			RedirectAttributes rttr, Authentication authentication) throws Exception {

		boolean ok = todayService.addFutsalToday(authentication, futsalToday, files);

		if (ok) {
			return "redirect:/futsal/futsalTodayList";
		} else {
			return "redirect:/futsal/futsalTodayList";
		}

	}

	@GetMapping("/todayId/{id}")
	public String detailToday(@PathVariable("id") Integer id, Model model) {

		FutsalToday getList = todayService.getBoard(id);

		System.out.println("getList" + getList);

		model.addAttribute("board", getList);

		return "futsal/futsalTodayGet";
	}

	@GetMapping("/myPage")
	public void futsalMyPage(Authentication authentication, Model model) {

		// 로그인 닉네임 확인
		Member member = service.getMembertUserId(authentication.getName());
//		System.out.println("접근함");

		Map<String, Object> myPageList = new HashMap<>();

		myPageList.put("MyNickName", member.getNickName());

		List<FutsalBoard> totalMyData = service.getTotalMyPageInfo(member.getNickName(), member.getNickName());
//		System.out.println("***" + totalMyData);
		myPageList.put("totalMyData", totalMyData);

		// id 기준으로 리스트업
//		List<FutsalBoard> futsalBoards = service.getMyPageInfo(authentication.getName());
//		myPageList.put("futsalBoards", futsalBoards);
//		System.out.println(futsalBoards);

		// 참여자들 리스트업
		List<FutsalParty> members = service.getJoinMember(member.getNickName());
		myPageList.put("members", members);
//		System.out.println("멤버스 : " + members);

		model.addAllAttributes(myPageList);

	}

	// 여기서 List<String> Mapper 써줄 것임
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/futsalMate")
	public void futsalMatePage(Model model, Authentication authentication,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "search", defaultValue = "") String search) {

//		System.err.println("접근 1");

		Map<String, Object> getMemberList = new HashMap<>();

		List<FutsalBoard> futsalMates = service.getMateBoardByAddress(authentication, type, search);
		getMemberList.put("futsalMates", futsalMates);

		/* model.addAttribute("board", futsalMates); */
//		System.out.println(futsalMates);

		List<FutsalParty> members = service.selectMemberIdByBoardId();
		getMemberList.put("members", members);

		// 현재 로그인한 사람의 닉네임을 넘겨줘야함
		List<Member> memberList = service.getUserId(authentication.getName());
		getMemberList.put("memberList", memberList);

		model.addAllAttributes(getMemberList);
	}

	@GetMapping("/futsalMate1")
	public void futsalMatePage1(Model model, Authentication authentication) {

		Map<String, Object> getMemberList = new HashMap<>();

		List<FutsalBoard> futsalMates = service.getMateBoard();
		getMemberList.put("futsalMates", futsalMates);

		/* model.addAttribute("board", futsalMates); */
//		System.out.println(futsalMates);

		List<FutsalParty> members = service.selectMemberIdByBoardId();
		getMemberList.put("members", members);

		// 현재 로그인한 사람의 닉네임을 넘겨줘야함
		List<Member> memberList = service.getUserId(authentication.getName());
		getMemberList.put("memberList", memberList);

		model.addAllAttributes(getMemberList);
	}

	@GetMapping("/futsalModify/{id}")
	public String futsalModifyForm(@PathVariable("id") Integer id, Model model, String writer,
			Authentication authentication) {

		Map<String, Object> getMemberList = new HashMap<>();

		FutsalBoard getList = service.getBoard(id);
		getMemberList.put("board", getList);

		List<FutsalParty> members = service.selectMemberIdByBoardId(id, getList.getWriter());
		getMemberList.put("members", members);
//		System.out.println(members);

		List<Member> memberList = service.getUserId(authentication.getName());
		getMemberList.put("memberList", memberList);

		model.addAllAttributes(getMemberList);

		return "futsal/futsalModify";
	}

	@PostMapping("/futsalModify/{id}")
	public String futsalModifyProcess(FutsalBoard futsalBoard, RedirectAttributes rttr) throws Exception {

		boolean ok = service.modify(futsalBoard);


		if (ok) {
			// 해당 게시물 보기로 리디렉션
//			rttr.addAttribute("success", "success");
			rttr.addFlashAttribute("message", futsalBoard.getId() + "번 게시물이 수정되었습니다.");
			return "redirect:/futsal/futsalList";
		} else {
			// 수정 form 으로 리디렉션
//			rttr.addAttribute("fail", "fail");
			rttr.addFlashAttribute("message", futsalBoard.getId() + "번 게시물이 수정되지 않았습니다.");
			return "redirect:/futsal/futsalModify/" + futsalBoard.getId();
		}
	}

	@PostMapping("/futsalRemove")
	public String futsalRemove(Integer id, RedirectAttributes rttr) {
		boolean ok = service.remove(id);
		if (ok) {
			// query string에 추가
//			rttr.addAttribute("success", "remove");

			// 모델에 추가
			rttr.addFlashAttribute("message", id + "번 게시물이 삭제되었습니다.");
			return "redirect:/futsal/futsalMate";
		} else {
			return "redirect:/futsal/futsalModify/" + id;
		}
	}

	@GetMapping("/futsalMap")
	public void futsalMapProcess() {

	}
	
	// ******* TODAY
	
	@GetMapping("/futsalTodayModify/{id}")
	public String todayModifyForm(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("board", todayService.getBoard(id));
		return "futsal/futsalTodayModify";
	}
	
	
	@PostMapping("/futsalTodayModify/{id}")
	// 수정하려는 게시물 id : board.getId()
	public String modifyProcess(FutsalToday futsalToday, RedirectAttributes rttr,
			// requestParam을 통해서 jsp로 넘어오는 인자를 value 로 처래해줌을 표시해준다.
			// 파일 이름을 인자로 받아서 삭제해줄 파일들을 지정해줌
			@RequestParam(value = "removeFiles", required = false) List<String> removeFileNames,
			// 파일들을 실제로 받아서 (MultipartFile[]) 인자로 넣어줌
			@RequestParam(value = "files", required = false) MultipartFile[] addFiles) throws Exception {
//		System.out.println(removeFileNames); 확인용

		// removeFileNames 로 넘어온 파일명을 찾아서 삭제 해줌

		// 새로 입력받은 file 폴더를 만들어줌

		// 변경된 것들을 테이블에 수정해줌

		boolean ok = todayService.todayModify(futsalToday, removeFileNames, addFiles);

		if (ok) {
			// 해당 게시물 보기로 리디렉션 ==> 다시 화면을 작성해줘야하기 때문에
//			rttr.addAttribute("success", "success");
			// addFlashAttribute 는 attibute를 전달해줄 수 있다.
			rttr.addFlashAttribute("message", futsalToday.getId() + "번 게시물이 수정되었습니다.");
			return "redirect:/futsal/todayId/" + futsalToday.getId();
		} else {
			// 수정 form 으로 리디렉션
//			rttr.addAttribute("fail", "fail");
			rttr.addFlashAttribute("message", futsalToday.getId() + "번 게시물이 수정되지 않았습니다.");
			return "redirect:/futsal/todayId/" + futsalToday.getId();
		}
	}

	@PostMapping("/todayRemove")
	public String remove(Integer id, RedirectAttributes rttr) {
		boolean ok = todayService.removeById(id);
		if (ok) {
			// query string에 추가
//			rttr.addAttribute("success", "remove");

			// 모델에 추가
			rttr.addFlashAttribute("message", id + "번 게시물이 삭제되었습니다.");

			return "redirect:/futsal/futsalList";
		} else {
			return "redirect:/futsal/futsalTodayModify/" + id;
		}
	}
	
	@GetMapping("futsalTodayList")
	public void todayList(Model model
//			@RequestParam(value = "page", defaultValue = "1") Integer page,
//			@RequestParam(value = "search", defaultValue = "") String search,
//			@RequestParam(value = "type", required = false) String type) 
			) {
		
		Map<String, Object> todayList = new HashMap<>();		
		
		List<FutsalToday> today = todayService.listBoard();
		todayList.put("futsalTodayList", today);
		
		model.addAllAttributes(todayList);

	}
	
	
	// *********
	
	
	
	

	// ******************** AJAX

	@PostMapping("joinParty")
	public ResponseEntity<Map<String, Object>> joinParty(@RequestBody FutsalParty futsalParty,
			Authentication authentication) {
		return ResponseEntity.ok().body(partyService.join(futsalParty, authentication));

	}

	/*
	 * @PostMapping("rejectParty") public ResponseEntity<Map<String, Object>>
	 * rejectParty(@RequestBody FutsalParty futsalParty, Authentication
	 * authentication) { return
	 * ResponseEntity.ok().body(partyService.reject(futsalParty, authentication));
	 * 
	 * }
	 */

	@GetMapping("/getFutsalDetail")
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

		return listSearch;
	}

}
