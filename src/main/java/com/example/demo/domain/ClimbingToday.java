package com.example.demo.domain;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class ClimbingToday {
	private Integer id;
	private String title;
	private String body;
	private String writer;
	private LocalDate inserted;
	private List<String> fileName;
	private String userId;
	private Integer likeCount;
	private boolean liked; 
}