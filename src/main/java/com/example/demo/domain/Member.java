package com.example.demo.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class Member {

	private Integer id;
	private String userId;
	private String password;
	private String name;
	private String nickName;
	private LocalDateTime inserted;
	private String birth;
	private Boolean gender;
	private String career;
	private String address;
	private String phone;
	private String email;
	private String introduce;
	private Integer manner;
	
	private List<String> authority;

	
}
