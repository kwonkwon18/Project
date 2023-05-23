package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.domain.RunningBoard;
import com.example.demo.service.RunningService;

@Controller
@RequestMapping("running")
public class RunningController {

	@Autowired
	private RunningService service;

	@GetMapping("/list")
	public void list(Model model) {

		List<RunningBoard> list = service.listBoard(); // 페이지 처리 전

		model.addAttribute("boardList", list);

	
	}

	@GetMapping("/add")
	public void addProcess() {

	}

	@PostMapping("/add")
	public String addResult(RunningBoard runningBoard, RedirectAttributes trrt) {

		boolean ok = service.addBoard(runningBoard);

		if (ok) {
			return "redirect:/running/list";
		} else {
			return "redirect:/running/list";
		}
	}
	
	
	@GetMapping("/id/{id}")
	public String detail(@PathVariable("id") Integer id, Model model) {
		
		RunningBoard getList = service.getBoard(id);

		model.addAttribute("board", getList);
		
		return "running/get";
	}
	
	

}
