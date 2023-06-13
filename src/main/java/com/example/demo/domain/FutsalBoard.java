package com.example.demo.domain;

import java.time.*;
import java.util.*;

import lombok.*;

@Data
public class FutsalBoard {
	private Integer id;
	private String title;
	private String body;
	private String writer;
	private LocalDateTime inserted;
	private List<String> fileName;
	private double Lat;
	private double Lng;
	
	private Boolean liked;
	private Integer likeCount;
}
