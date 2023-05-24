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
@RequestMapping("futsal/")
public class FutsalController {

	@Autowired
	private FutsalService service;
	
	@GetMapping("list")
	public void list(Model model) {
		
		List<FutsalBoard> list = service.listBoard();
		
		model.addAttribute("boardList", list);
	}
	
	@GetMapping("add")
	public void addForm() {
	
	}
	
	@PostMapping("add")
	public String addProcess(
			@RequestParam("files") MultipartFile[] files,
			FutsalBoard futsalBoard, RedirectAttributes rttr) throws Exception {
		
		boolean ok = service.addBoard(futsalBoard, files);
		
		if (ok) {
			rttr.addFlashAttribute("message", "매치가 등록되었습니다.");
			return "redirect:/futsal/id/" + futsalBoard.getId();
		} else {
			rttr.addFlashAttribute("message", "매치 등록 중 문제가 발생하였습니다.");
			return "redirect:/futsal/add";
		}
		
	}
	
	
	@GetMapping("id/{id}")
	public String board(
			@PathVariable("id") Integer id,
			Model model) {
		FutsalBoard board = service.getFutsalBoard(id);
		
		model.addAttribute("board", board);
		
		return "futsal/get";
	}
	
	@GetMapping("modify/{id}")
	public String moifyForm(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("board", service.getFutsalBoard(id));
		return "futsal/modify";
	}
	
	@PostMapping("modify/{id}")
	public String modifyProcess(FutsalBoard futsalBoard,
			RedirectAttributes rttr) {
		boolean ok = service.modify(futsalBoard);
		
		if (ok) {
			// 해당 게시물 보기로 리디렉션
			rttr.addFlashAttribute("message", futsalBoard.getId() + "번 게시물이 수정되었습니다.");
			return "redirect:/futsal/id/" + futsalBoard.getId();
		} else {
			// 수정 form 으로 리디렉션
			rttr.addFlashAttribute("message", futsalBoard.getId() + "번 게시물이 수정되지 않았습니다.");
			return "redirect:futsal/modify/" + futsalBoard.getId();
		}
	}
	
	@PostMapping("remove")
	public String remove(Integer id, RedirectAttributes rttr) {
		boolean ok = service.remove(id);
		
		if(ok) {
			rttr.addFlashAttribute("message", id + "번 게시물이 삭제되었습니다.");
			
			return "redirect:/futsal/list";
		} else {
			return "redirect:/futsal/id/" + id;
		}
	}
	
}










