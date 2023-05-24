package com.example.demo.mapper;

import java.util.*;

import org.apache.ibatis.annotations.*;

import com.example.demo.domain.*;


@Mapper
public interface ClimbingBoardMapper {

	@Insert("""
			INSERT INTO ClimbingBoard (title, body, writer, Lat, Lng)
			VALUES (#{title}, #{body}, #{writer}, #{Lat}, #{Lng})
			""")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int insert(ClimbingBoard climbingBoard);

	@Select("""
			select * from ClimbingBoard;
			""")
	List<ClimbingBoard> selectList();

	@Select("""
			select * from ClimbingBoard 
			where id = #{id}
			""")
	ClimbingBoard selectById(Integer id);



}