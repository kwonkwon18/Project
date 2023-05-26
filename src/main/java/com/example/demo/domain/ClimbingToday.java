package com.example.demo.domain;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class ClimbingToday {
	private Integer id;
	private String writer;
	private String title;
	private String body;
	private LocalDate inserted;
	private List<String> fileName;
}