package com.example.demo.domain;

import lombok.Data;

@Data
public class ClimbingParty {

	private Integer id;
	private Integer boardId; // ==> get 화면에서 가져올 수 있음
	private String userId; // 호스트 ==> get 화면에서 가져올 수 있음
	private String memberId; // 게스트 
	private Integer participation;
	private String host;
	private String guest;
	private Integer chatCount;	
	private String title;
	
	
}
