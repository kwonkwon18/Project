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
	private LocalDateTime time;
	private List<String> fileName;
	
	private double Lat;
	private double Lng;
	private String address;
	
	private Integer people;
	private Integer currentNum;
	
	private String userId; // 호스트 ==> get 화면에서 가져올 수 있음
	private String memberId; // 게스트
	
	private Boolean liked;
	private Integer likeCount;
}
