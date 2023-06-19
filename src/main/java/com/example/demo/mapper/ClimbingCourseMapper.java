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
				f.fileName,
				m.userId,
				(select count(*) from ClimbingCourseLike where boardId = c.id) likeCount
			FROM 
				ClimbingCourse c 
				LEFT JOIN ClimbingCourseFileName f ON c.id = f.courseId
				LEFT JOIN Member m ON c.writer = m.nickName
				LEFT JOIN ClimbingCourseLike cl on c.id = cl.boardId
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
			f.fileName, 
			(SELECT COUNT(*) FROM ClimbingCourseLike cl WHERE cl.boardId = c.id) as likeCount, 
			(SELECT COUNT(*) FROM ClimbingCourseComment ct WHERE ct.boardId = c.id) as commentCount 
			FROM ClimbingCourse c 
			LEFT JOIN ClimbingCourseFileName f ON c.id = f.courseId 
			LEFT JOIN ClimbingCourseLike cl on c.id = cl.boardId 
			LEFT JOIN ClimbingCourseFileName f2 ON c.id = f2.courseId WHERE c.title LIKE '%%' ORDER BY c.id DESC;

			""")
	@ResultMap("climbingCourseResultMap")
	List<ClimbingCourse> selectListByCourseSearch(String courseSearch);

	@Select("""
			SELECT * FROM ClimbingCourse
			WHERE title LIKE '%${searchTerm}%'
			""")
	List<ClimbingCourse> selectBySearchTerm(String searchTerm);

}
