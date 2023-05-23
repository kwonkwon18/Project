package com.example.demo.domain;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class RunningBoard {
	// db에 존재하는 필드
	private Integer id;
	private String title;
	private String body;
	private LocalDate inserted;
	private String writer;
	private List<String> fileName;
	private double Lat;
	private double Lng;
}
