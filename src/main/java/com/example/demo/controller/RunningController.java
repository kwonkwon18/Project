package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

		Map<String, Object> listMap =  new HashMap<>();
		
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
	public String addResult(RunningBoard runningBoard, RedirectAttributes trrt) {

		boolean ok = service.addBoard(runningBoard);

		if (ok) {

			return "redirect:/running/runningList";
		} else {
			return "redirect:/running/runningList";
		}
	}

	@GetMapping("/id/{id}")
	public String detail(@PathVariable("id") Integer id, Model model) {

		RunningBoard getList = service.getBoard(id);

		model.addAttribute("board", getList);

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
	
	
	
	
	
	// ******************** AJAX

	@PostMapping("joinParty")
	public ResponseEntity<Map<String, Object>> joinParty(@RequestBody RunningParty runningParty) {
		return ResponseEntity.ok().body(partyService.join(runningParty));

	}

}