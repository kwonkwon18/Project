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

		List <ClimbingToday> todayList = service.listToday();
		
		model.addAttribute("boardList", list);

		model.addAttribute("todayList", todayList);
	
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
	public String TodayResult(ClimbingToday climbingToday, RedirectAttributes rttr) {
		
		boolean ok = service.addClimbingToday(climbingToday);
		
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
	

}













