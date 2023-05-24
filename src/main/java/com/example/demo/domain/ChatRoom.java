package com.example.demo.domain;

import java.time.*;

import lombok.*;

@Data
public class ChatRoom {

	private Integer id;
	private String creater;
	private String invited;
	private LocalDateTime inserted;
}
