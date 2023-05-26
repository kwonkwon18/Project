package com.example.demo.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import com.example.demo.service.*;

@Controller
@RequestMapping("comment")
public class ClimbingCommentController {

	@Autowired
	private ClimbingCommentService commentService;
	
	
}
