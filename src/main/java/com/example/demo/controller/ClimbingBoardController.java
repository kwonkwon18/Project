package com.example.demo.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;
import org.springframework.web.servlet.mvc.support.*;

import com.example.demo.domain.*;
import com.example.demo.service.*;

@Controller
@RequestMapping("climbing")
public class ClimbingBoardController {

	@Autowired
	private ClimbingBoardService service;

	@GetMapping("list")
	public void list(Model model) {

		List<ClimbingBoard> list = service.listBoard(); // 페이지 처리 전

		List<ClimbingToday> todayList = service.listToday();
		
		List<ClimbingCourse> courseList = service.listCourse();

		model.addAttribute("boardList", list);

		model.addAttribute("todayList", todayList);
		
		model.addAttribute("courseList", courseList);

	}

	@GetMapping("/add")
	public void addProcess() {

	}

	@PostMapping("/add")
	public String addResult(ClimbingBoard climbingBoard, RedirectAttributes rttr) {

		boolean ok = service.addClimbingBoard(climbingBoard);

		if (ok) {
			return "redirect:/climbing/list";
		} else {
			return "redirect:/climbing/list";
		}
	}

	@GetMapping("/todayadd")
	public void todayProcess() {

	}

	@PostMapping("/todayadd")
	public String TodayResult(
			ClimbingToday climbingToday,
			RedirectAttributes rttr,
			@RequestParam("files") MultipartFile[] files) throws Exception {

		boolean ok = service.addClimbingToday(climbingToday, files);

		if (ok) {
			rttr.addFlashAttribute("message", climbingToday.getId() + "번 게시물이 등록되었습니다.");
			return "redirect:/id/" + climbingToday.getId();
		} else {
			rttr.addFlashAttribute("message", "게시물 등록 중 문제가 발생하였습니다.");
			rttr.addFlashAttribute("board", climbingToday);
			return "redirect:/add";
		}
	}

	@GetMapping("/courseadd")
	public void courseProcess() {

	}

	@PostMapping("/courseadd")
	public String CourseResult(ClimbingCourse climbingCourse, 
			RedirectAttributes rttr, 
			@RequestParam("files") MultipartFile[] files) throws Exception {

		boolean ok = service.addClimbingCourse(climbingCourse, files);

		if (ok) {
			return "redirect:/climbing/list";
		} else {
			return "redirect:/climbing/list";
		}
	}

	@GetMapping("/id/{id}")
	public String detail(@PathVariable("id") Integer id, Model model) {

		ClimbingBoard getList = service.getClimbingBoard(id);

		model.addAttribute("board", getList);

		return "climbing/get";
	}

	@GetMapping("/todayId/{id}")
	public String Todaydetail(@PathVariable("id") Integer id, Model model) {

		ClimbingToday todayList = service.getClimbingToday(id);

		model.addAttribute("board", todayList);

		return "climbing/todayget";

	}

	@GetMapping("/courseId/{id}")
	public String Coursedetail(@PathVariable("id") Integer id, Model model) {
		
		ClimbingCourse courseList = service.getClimbingCourseFileName(id);
		
		model.addAttribute("board", courseList);
		
		return "climbing/courseget";
		
	}

}
