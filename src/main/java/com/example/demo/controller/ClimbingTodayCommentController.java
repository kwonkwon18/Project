package com.example.demo.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.security.core.*;
import org.springframework.web.bind.annotation.*;

import com.example.demo.domain.*;
import com.example.demo.service.*;

@RestController
@RequestMapping("climbingTodayComment")
public class ClimbingTodayCommentController {

	@Autowired
	private ClimbingTodayCommentService service;

	@PutMapping("update")
	public ResponseEntity<Map<String, Object>> update(@RequestBody ClimbingTodayComment comment) {
		Map<String, Object> res = service.update(comment);

		return ResponseEntity.ok().body(res);
	}

	@GetMapping("id/{id}")
	public ClimbingTodayComment get(@PathVariable("id") Integer id) {
		return service.get(id);
	}

	@PostMapping("add")
	public ResponseEntity<Map<String, Object>> add(@RequestBody ClimbingTodayComment comment, Authentication authentication) {

		if (authentication == null) {
			Map<String, Object> res = Map.of("message", "로그인 후 댓글을 작성해주세요");
			return ResponseEntity.status(401).body(res);
		} else {
			Map<String, Object> res = service.add(comment, authentication);
			return ResponseEntity.ok().body(res);
		}

	}

	@GetMapping("list")
	public List<ClimbingTodayComment> list(@RequestParam("board") Integer boardId, Authentication authentication) {

		return service.list(boardId, authentication);
	}

	@DeleteMapping("id/{id}")
	public ResponseEntity<Map<String, Object>> remove(@PathVariable("id") Integer id) {
		Map<String, Object> res = service.remove(id);

		return ResponseEntity.ok().body(res);
	}
}
	
