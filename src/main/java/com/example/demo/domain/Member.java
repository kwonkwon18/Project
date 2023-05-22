package com.example.demo.domain;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Data
public class Member {

	private Integer id;
	private String userId;
	private String password;
	private String name;
	private String nickName;
	private LocalTime inserted;
	private LocalDate birth;
	private Boolean gender;
	private String career;
	private String address;
	private String phone;
	private String email;
	private String introduce;
	private Integer manner;

}
