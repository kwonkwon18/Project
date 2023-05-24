package com.example.demo.domain;

import java.time.*;
import java.util.*;

import lombok.*;

@Data
public class ClimbingCourse {
	private Integer id;
	private String title;
	private String body;
	private String writer;
	private LocalDate inserted;
	private List<String> fileName;
}
