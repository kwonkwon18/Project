package com.example.demo.mapper;

import java.util.*;

import org.apache.ibatis.annotations.*;

import com.example.demo.domain.*;

@Mapper
public interface ClimbingTodayMapper {

	@Insert("""
			INSERT INTO ClimbingToday (writer, title, body)
			VALUES (#{writer}, #{title}, #{body})
			""")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int insert(ClimbingToday climbingToday);
	
	@Select("""
			select * from ClimbingToday;
			""")
	List<ClimbingToday> selectList();

	@Select("""
			select * from ClimbingToday 
			where id = #{id}
			""")
	ClimbingToday selectById(Integer id);

	@Insert("""
			INSERT INTO ClimbingFileName (boardId, fileName)
			VALUES (#{boardId}, #{fileName})
			""")
	Integer insertFileName(Integer boardId, String fileName);

}
