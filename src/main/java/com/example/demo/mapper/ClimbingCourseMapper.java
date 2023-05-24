package com.example.demo.mapper;

import java.util.*;

import org.apache.ibatis.annotations.*;

import com.example.demo.domain.*;

@Mapper
public interface ClimbingCourseMapper {

	@Insert("""
			INSERT INTO ClimbingCourse (writer, title, body)
			VALUES (#{writer}, #{title}, #{body})
			""")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int insert(ClimbingCourse climbingCourse);
	
	@Select("""
			select * from ClimbingCourse;
			""")
	List<ClimbingCourse> selectList();

	@Select("""
			select * from ClimbingCourse 
			where id = #{id}
			""")
	ClimbingCourse selectById(Integer id);

	@Insert("""
			INSERT INTO ClimbingFileName (boardId, fileName)
			VALUES (#{boardId}, #{fileName})
			""")
	Integer insertFileName(Integer id, String originalFilename);


	
}
