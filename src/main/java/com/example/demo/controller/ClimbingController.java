package com.example.demo.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.access.prepost.*;
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

	@GetMapping("list")
	public void list(Model model) {

		Map<String, Object> listMap = new HashMap<>();

		// 메이트 구하기
		List<ClimbingMate> mate = mateService.listBoard(); // 페이지 처리 전
		listMap.put("mateList", mate);

		// 오늘의 등산
		List<ClimbingToday> today = todayService.listBoard();
		listMap.put("todayList", today);

		// 추천 코스
		List<ClimbingCourse> course = courseService.listBoard();
		listMap.put("courseList", course);

		model.addAllAttributes(listMap);

	}

	@GetMapping("/mateAdd")
	public void addProcess() {

	}

	@PostMapping("/mateAdd")
	public String addResult(ClimbingMate climbingMate, RedirectAttributes rttr) throws Exception {

		boolean ok = mateService.addClimbingMate(climbingMate);

		if (ok) {
			return "redirect:/climbing/list";
		} else {
			return "redirect:/climbing/list";
		}
	}

	@GetMapping("/mateId/{id}")
	public String detail(@PathVariable("id") Integer id, Model model) {

		ClimbingMate getList = mateService.getClimbingMate(id);

		model.addAttribute("board", getList);

		return "climbing/mateGet";
	}

	@GetMapping("/mateModify/{id}")
	public String modifyForm(@PathVariable("id") Integer id, Model model) {
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
			return "redirect:/modify/" + climbingMate.getId();
		}
	}
	
	
	@PostMapping("/mateRemove")
	public String remove(Integer id, RedirectAttributes rttr) {
		boolean ok = mateService.remove(id);
		if (ok) {
			// query string에 추가
//			rttr.addAttribute("success", "remove");

			// 모델에 추가
			rttr.addFlashAttribute("message", id + "번 게시물이 삭제되었습니다.");

			return "redirect:/climbing/list";
		} else {
			return "redirect:/mateId/" + id;
		}
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
			return "redirect:/climbing/list";
		} else {
			return "redirect:/climbing/todayAdd";
		}
	}

	@GetMapping("/todayId/{id}")
	public String Todaydetail(@PathVariable("id") Integer id, Model model) {

		ClimbingToday todayList = todayService.getBoard(id);

		model.addAttribute("board", todayList);

		return "climbing/todayGet";

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
			return "redirect:/climbing/list";
		} else {
			return "redirect:/climbing/courseAdd";
		}
	}

	@GetMapping("/courseId/{id}")
	public String Coursedetail(@PathVariable("id") Integer id, Model model) {

		ClimbingCourse courseList = courseService.getBoard(id);

		model.addAttribute("board", courseList);

		return "climbing/courseGet";

	}

}
