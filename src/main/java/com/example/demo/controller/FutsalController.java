package com.example.demo.controller;

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

import com.example.demo.domain.FutsalBoard;
import com.example.demo.domain.FutsalLike;
import com.example.demo.domain.FutsalParty;
import com.example.demo.domain.FutsalPartyMember;
import com.example.demo.service.FutsalPartyService;
import com.example.demo.service.FutsalService;


@Controller
@RequestMapping("futsal")
public class FutsalController {

	// 매치 후기 게시판
	
	@Autowired
	private FutsalService futsalService;
	
	@Autowired
	private FutsalPartyService futsalPartyService;
	
	@GetMapping("list")
	public void list(Model model) {
		
		List<FutsalBoard> list = futsalService.listBoard();
		
		model.addAttribute("boardList", list);
	}
	
	@GetMapping("add")
	public void addForm() {
	
	}
	
	@PostMapping("add")
	public String addProcess(
			@RequestParam("files") MultipartFile[] files,
			FutsalBoard futsalBoard, RedirectAttributes rttr) throws Exception {
		
		
		boolean ok = futsalService.addBoard(futsalBoard, files);
		if (ok) {
			rttr.addFlashAttribute("message", "게시물이 등록되었습니다.");
			return "redirect:/futsal/id/" + futsalBoard.getId();
		} else {
			rttr.addFlashAttribute("message", "게시물 등록 중 문제가 발생하였습니다.");
			return "redirect:/futsal/add";
		}
		
	}
	
	
	@GetMapping("/id/{id}")
	public String board(
			@PathVariable("id") Integer id,
			Model model,
			Authentication authentication) {
		FutsalBoard board = futsalService.getFutsalBoard(id);
		
		model.addAttribute("board", board);
		
		return "futsal/get";
	}
	
	@GetMapping("/modify/{id}")
	public String moifyForm(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("board", futsalService.getFutsalBoard(id));
		return "futsal/modify";
	}
	
	@PostMapping("/modify/{id}")
	public String modifyProcess(FutsalBoard futsalBoard,
			@RequestParam(value = "files", required = false) MultipartFile[] addFiles,
			@RequestParam(value = "removeFiles", required = false) List<String> removeFileNames,
			RedirectAttributes rttr) throws Exception {
		boolean ok = futsalService.modify(futsalBoard, addFiles, removeFileNames);
		
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
		boolean ok = futsalService.remove(id);
		
		if(ok) {
			rttr.addFlashAttribute("message", id + "번 게시물이 삭제되었습니다.");
			
			return "redirect:/futsal/list";
		} else {
			return "redirect:/futsal/id/" + id;
		}
	}
	
	
	// 매치 구하기
	@GetMapping("/futsalPartyList")
	public void futsalPartyList(Model model) {
		
		List<FutsalParty> list = futsalPartyService.partyList();
		
		model.addAttribute("partyList", list);
	}
	
	@GetMapping("/partyId/{id}")
	public String futsalPartyBoard(
			@PathVariable("id") Integer id,
			Model model) {
		FutsalParty futsalParty = futsalPartyService.getFutsalParty(id);
		
		model.addAttribute("party", futsalParty);
		
		return "futsal/futsalPartyGet";
	}
	
	@GetMapping("futsalPartyAdd")
	public void futsalPartyAddForm() {
	
	}
	
	@PostMapping("futsalPartyAdd")
	public String futsalPartyAddProcess(
			FutsalParty futsalParty, 
			RedirectAttributes rttr,
			Authentication authentication) throws Exception {
		
		futsalParty.setWriter(authentication.getName());
		boolean ok = futsalPartyService.addFutsalParty(futsalParty);
		if (ok) {
			rttr.addFlashAttribute("message", "매치가 등록되었습니다.");
			return "redirect:/futsal/partyId/" + futsalParty.getId();
		} else {
			rttr.addFlashAttribute("message", "매치 등록 중 문제가 발생하였습니다.");
			return "redirect:/futsal/futsalPartyAdd";
		}
	}
	

	@PostMapping("futsalPartyMember")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> futsalPartyMember (
			@RequestBody FutsalPartyMember futsalPartyMember,
			Authentication authentication) {
			
		System.out.println(authentication);
		if (authentication == null) {
			return ResponseEntity
					.status(403)
					.body(Map.of("message", "로그인 후 참여해주세여."));
		} else {
			return ResponseEntity
					.ok()
					.body(futsalPartyService.futsalPartyMember(futsalPartyMember, authentication));						
		}
	}
	
	@PostMapping("/like")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> futsalLike(
			@RequestBody FutsalLike like,
			Authentication authentication) {
		
		if(authentication == null) {
			return ResponseEntity
					.status(403)
					.body(Map.of("message", "로그인 후 좋아요 클릭해주세요"));
		} else {
			return ResponseEntity
					.ok()
					.body(futsalService.like(like, authentication));
		}
	}
	
}










