package com.example.demo.domain;

import java.sql.*;
import java.time.*;

import lombok.*;

@Data
public class FutsalParty {
	private Integer id;
	private String title;
	private String body;
	private String writer;
	private String futsalGender;
	private String stadium;
	private Integer memberNum;
	private Integer lastMemberNum;
	private LocalDate startDate;
	private LocalTime startTime;
	private Double Lat;
	private Double Lng;
}
