package com.example.demo.mapper;

import java.util.*;

import org.apache.ibatis.annotations.*;

import com.example.demo.domain.*;

@Mapper
public interface FutsalPartyMapper {

	@Select("""
			SELECT *
			FROM FutsalParty
			ORDER BY id DESC
			""")
	List<FutsalParty> selectPartyAll();

	@Select("""
			SELECT *
			FROM FutsalParty
			WHERE id = #{id}
			""")
	FutsalParty selectById(Integer id);
	
	@Insert("""
			INSERT INTO FutsalParty(
				title,
				body,
				writer,
				Lat,
				Lng,
				memberNum,
				stadium,
				startDate,
				startTime,
				futsalGender)
			VALUES (#{title}, #{body}, #{writer}, #{Lat}, #{Lng}, #{memberNum}, #{stadium}, #{startDate}, #{startTime}, #{futsalGender})
			""")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int insert(FutsalParty futsalParty);


}
