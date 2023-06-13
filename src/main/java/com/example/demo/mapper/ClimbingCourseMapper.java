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
			SELECT 
				c.id,
				c.title,
				c.body,
				c.writer,
				c.inserted,
				f.fileName
			FROM ClimbingCourse c LEFT JOIN ClimbingCourseFileName f ON c.id = f.courseId
			WHERE c.title LIKE '%${courseSearch}%'
			ORDER BY inserted DESC
			""")
	@ResultMap("climbingCourseResultMap")
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

	@Select("""
			SELECT * FROM Member
			WHERE userId = #{userId}
			""")
	Member selectMemberById(String name);
	
	@Delete("""
			DELETE FROM ClimbingCourseFileName
			WHERE courseId = #{courseId}
				AND fileName = #{fileName}
			""")
	void deleteFileNameByBoardIdAndFileName(Integer id, String fileName);

	@Update("""
			UPDATE ClimbingCourse
			SET
				title = #{title},
				body = #{body}
			WHERE
				id = #{id}
			""")
	int update(ClimbingCourse climbingCourse);

	@Select("""
			SELECT fileName FROM ClimbingCourseFileName
			WHERE courseId = #{courseId}
			""")
	List<String> selectFileNamesByBoardId(Integer id);

	@Delete("""
			DELETE FROM ClimbingCourseFileName
			WHERE courseId = #{courseId}
				AND fileName = #{fileName}
			""")
	void deleteFileNameByCourseId(Integer id);

	@Delete("""
			DELETE FROM ClimbingCourse
			WHERE id = #{id}
			""")
	int deleteById(Integer id);
	
	@Select("""
			SELECT
				c.id,
				c.title,
				c.body,
				c.writer,
				c.inserted,
				f.fileName
			FROM ClimbingCourse c LEFT JOIN ClimbingCourseFileName f ON c.id = f.courseId
			WHERE c.title LIKE '%${courseSearch}%'
			ORDER BY Id DESC;
			""")
	@ResultMap("climbingCourseResultMap")
	List<ClimbingCourse> selectListByCourseSearch(String courseSearch);


	
}
