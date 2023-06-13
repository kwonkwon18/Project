package com.example.demo.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class RunningToday {
	// db에 존재하는 필드
	private Integer id;
	private String title;
	private String body;
	private LocalDateTime inserted;
	private String writer;
	private String userId;
	
	private List<String> fileName;
	private Integer likeCount;
	private boolean liked;
	private Integer commentCount;
}
