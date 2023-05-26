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
			SELECT * FROM ClimbingCourse;
			""")
	List<ClimbingCourse> selectList();

	@Select("""
			SELECT 
				c.id,
				c.title,
				c.body,
				c.writer,
				c.inserted,
				f.fileName
			FROM ClimbingCourse c LEFT JOIN ClimbingCourseFileName f ON c.id = f.courseId
			WHERE c.id = #{id}
			""")
	@ResultMap("climbingCourseResultMap")
	ClimbingCourse selectById(Integer id);

	@Insert("""
			INSERT INTO ClimbingCourseFileName (courseId, fileName)
			VALUES (#{courseId}, #{fileName})
			""")
	Integer insertFileName(Integer courseId, String fileName);


	
}
