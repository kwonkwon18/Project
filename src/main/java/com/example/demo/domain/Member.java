package com.example.demo.domain;

import java.time.*;
import java.util.*;

import lombok.*;

@Data
public class Member {

	private Integer id;
	private String userId;
	private String password;
	private String name;
	private String nickName;
	private LocalDateTime inserted;
	private String birth;
	private String gender;
	private String address;
	private String phone;
	private String email;
	private String introduce;
	private Integer manner;
	
	private List<String> authority;

	
}
