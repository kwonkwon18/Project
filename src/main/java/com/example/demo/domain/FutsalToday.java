package com.example.demo.domain;

import java.time.*;
import java.util.*;

import lombok.*;

@Data
public class FutsalToday {
	
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
