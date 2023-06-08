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
	
	private List<String> fileName;

}
