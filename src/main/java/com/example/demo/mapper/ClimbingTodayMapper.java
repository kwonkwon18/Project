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
			SELECT * FROM ClimbingToday;
			""")
	List<ClimbingToday> selectList();

	@Select("""
			SELECT
				c.id,
				c.title,
				c.body,
				c.writer,
				c.inserted,
				f.fileName
			FROM ClimbingToday c LEFT JOIN ClimbingTodayFileName f ON c.id = f.todayId
			WHERE c.id = #{id}
			""")
	@ResultMap("climbingTodayResultMap")
	ClimbingToday selectById(Integer id);

	@Insert("""
			INSERT INTO ClimbingTodayFileName (todayId, fileName)
			VALUES (#{todayId}, #{fileName})
			""")
	Integer insertFileName(Integer todayId, String fileName);

	@Delete("""
			DELETE FROM ClimbingTodayFileName
			WHERE todayId = #{todayId}
				AND fileName = #{fileName}
			""")
	void deleteFileNameByBoardIdAndFileName(Integer id, String fileName);

	@Update("""
			UPDATE ClimbingToday
			SET
				title = #{title},
				body = #{body}
			WHERE
				id = #{id}
			""")
	int update(ClimbingToday climbingToday);

	@Select("""
			SELECT fileName FROM ClimbingTodayFileName
			WHERE todayId = #{todayId}
			""")
	List<String> selectFileNamesByBoardId(Integer id);

	@Delete("""
			DELETE FROM ClimbingTodayFileName
			WHERE todayId = #{todayId}
				AND fileName = #{fileName}
			""")
	void deleteFileNameByTodayId(Integer id);

	@Delete("""
			DELETE FROM ClimbingToday
			WHERE id = #{id}
			""")
	int deleteById(Integer id);

}
