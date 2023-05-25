package com.example.demo.domain;

import java.time.*;

import lombok.*;

@Data
public class Chat {
	private Integer id;
	private Integer chatRoomId;
	private String senderId;
	private String recipientId;
	private String message;
	private LocalDateTime inserted;
}
