package com.example.demo.mapper;

import java.util.*;

import org.apache.ibatis.annotations.*;

import com.example.demo.domain.*;


@Mapper
public interface ClimbingMateMapper {

	@Insert("""
			INSERT INTO ClimbingMate (title, body, writer, Lat, Lng)
			VALUES (#{title}, #{body}, #{writer}, #{Lat}, #{Lng})
			""")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int insert(ClimbingMate climbingMate);

	@Select("""
			SELECT * FROM ClimbingMate;
			""")
	List<ClimbingMate> selectList();

	@Select("""
			SELECT * FROM ClimbingMate 
			WHERE Id = #{id}
			""")
	ClimbingMate selectById(Integer id);

	List<ClimbingMate> selectById();

	@Delete("""
			DELETE FROM ClimbingMateFileName
			WHERE mateId = #{mateId}
				AND fileName = #{fileName}
			""")
	void deleteFileNameByBoardIdAndFileName(Integer id, String fileName);

	@Insert("""
			INSERT INTO FileName (mateId, fileName)
			VALUES (#{mateId}, #{fileName}) 
			""")
	void insertFileName(Integer id, String originalFilename);

	@Update("""
			UPDATE ClimbingMate
			SET
				title = #{title},
				body = #{body}
			WHERE
				id = #{id}
			""")
	int update(ClimbingMate climbingMate);

	@Select("""
			SELECT fileName FROM ClimbingMateFileName
			WHERE mateId = #{mateId}
			""")
	List<String> selectFileNamesBymateId(Integer id);

	@Delete("""
			DELETE FROM ClimbingMateFileName
			WHERE mateId = #{mateId}
				AND fileName = #{fileName}
			""")
	void deleteFileNameBymateId(Integer id);

	@Delete("""
			DELETE FROM ClimbingMate
			WHERE id = #{id}
			""")
	int deleteById(Integer id);

	@Select("""
			SELECT fileName FROM ClimbingMateFileName
			WHERE mateId = #{mateId}
			""")
	List<String> selectFileNamesByBoardId(Integer id);

	@Delete("""
			DELETE FROM ClimbingMateFileName
			WHERE mateId = #{mateId}
				AND fileName = #{fimeName}
			""")
	void deleteFileNameByMateId(Integer id);



}