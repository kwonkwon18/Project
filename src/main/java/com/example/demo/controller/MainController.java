package com.example.demo.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.domain.*;
import com.example.demo.service.*;

@Controller
@RequestMapping("/")
public class MainController {

	@Autowired
	private ChatService chatService;

	@Autowired
	private MemberService memberService;
	
	@GetMapping("checkEmail/{email}")
	@ResponseBody
	public Map<String, Object> checkEmail(@PathVariable("email") String email) {
		return memberService.checkEmail(email);
	}
	

	@GetMapping("checkNickName/{nickName}")
	@ResponseBody
	public Map<String, Object> checkNickName(@PathVariable("nickName") String nickName) {
		return memberService.checkNickName(nickName);
	}
	
	@GetMapping("IDCheck/{userId}")
	@ResponseBody
	public Map<String, Object> checkId(@PathVariable("id") String id) {
		
		return memberService.IDCheck(id);
	}
	
	@GetMapping({ "/", "main" })
	public void main() {

	}

	// 서재권 작업 내용***********************
	@GetMapping("login")
	public void loginForm() {

	}

	@GetMapping("signup")
	public void signupForm() {

	}

	@PostMapping("signup")
	public String signupProcess(Member member, RedirectAttributes rttr) {

		try {
			memberService.signup(member);
			// 정보 제공을 위한 것
			rttr.addFlashAttribute("member", member);
			// alert를 위한 것
			rttr.addFlashAttribute("message", "회원 가입되었습니다 ⭕⭕");
			return "redirect:/login";

		} catch (Exception e) {
			e.printStackTrace();
			rttr.addFlashAttribute("member", member);
			rttr.addFlashAttribute("message", "회원 가입 실패 ❌❌");
			return "redirect:/login";
		}
	}

	@GetMapping("list")
	public void list(Model model) {
		List<Member> list = memberService.listMember();
		model.addAttribute("memberList", list);
	}

	// 경로 : /member/info?userid=asdf
	@GetMapping("info")
	public void info(String userId, Model model) {
		Member member = memberService.get(userId);
		System.out.println(member);
		model.addAttribute("member", member);
	}

	@GetMapping("totalmypage")
	public void myapge(Authentication authentication, Model model) {
		Member member = memberService.get(authentication.getName());
		System.out.println(member);
		model.addAttribute("member", member);
	}

//	@PostMapping("remove")
//	public String remove(Member member,RedirectAttributes rttr) {
//		boolean ok = memberService.remove(member);
//		if(ok) {
//			rttr.addFlashAttribute("message", "회원 탈퇴되었습니다.");
//			return "redirect:/list";
//		}
//		else {
//			rttr.addFlashAttribute("message", "회원 탈퇴에 문제가 생겼습니다.");
//			return "redirect:/info?userId"+member.getUserId();
//		}
//	}
	// 1.
	@PostMapping("remove")
	public String remove(Member member, RedirectAttributes rttr) {

		boolean ok = memberService.remove(member);
		if (ok) {
			rttr.addFlashAttribute("message", "회원 탈퇴하였습니다.");
			return "redirect:/list";
		} else {
			rttr.addFlashAttribute("message", "회원 탈퇴시 문제가 발생하였습니다.");
			return "redirect:/info";
		}
	}

	@GetMapping("modify")
	public void modifyForm(Authentication authentication, Model model) {
		Member member = memberService.get(authentication.getName());
		model.addAttribute("member", member);
	}

	// 2.
	@PostMapping("modify")
	public String modifyProcess(Member member, RedirectAttributes rttr) {
		boolean ok = memberService.modify(member);

		if (ok) {
			rttr.addFlashAttribute("message", "회원 정보가 수정되었습니다.");
			return "redirect:/info?userId=" + member.getUserId();
		} else {
			rttr.addFlashAttribute("message", "회원 정보시 문제가 발생했습니다.");
			return "redirect:/modify?userId=" + member.getUserId();
		}
	}

	@GetMapping("welcomeMain")
	public void welcomMain() {
		
	}
}
