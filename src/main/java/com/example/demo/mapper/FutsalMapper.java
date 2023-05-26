package com.example.demo.mapper;

import java.util.*;

import org.apache.ibatis.annotations.*;

import com.example.demo.domain.*;

@Mapper
public interface FutsalMapper {

	@Select("""
			SELECT * 
			FROM FutsalBoard
			ORDER BY id DESC
			""")
	List<FutsalBoard> selectAll();
	
	@Select("""
			SELECT
				b.id,
				b.title,
				b.body,
				b.inserted,
				b.writer,
				f.fileName,
				Lat,
				Lng
				
			FROM FutsalBoard b LEFT JOIN FutsalFileName f ON b.id = f.boardId
			WHERE b.id = #{id}
			""")
	@ResultMap("futsalResultMap")
	FutsalBoard selectById(Integer id);

	@Insert("""
			INSERT INTO FutsalBoard (
					title,
					body,
					writer,
					Lat,
					Lng)
			VALUES (#{title}, #{body}, #{writer}, #{Lat}, #{Lng})
			""")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int insert(FutsalBoard futsalBoard);

	@Update("""
			UPDATE FutsalBoard
			SET
				title = #{title},
				body = #{body},
				Lat = #{Lat},
				Lng = #{Lng}
			WHERE
				id = #{id}
			""")
	int update(FutsalBoard futsalBoard);

	@Delete("""
			DELETE FROM FutsalBoard
			WHERE id = #{id}
			""")
	int deleteById(Integer id);

	@Insert("""
			INSERT INTO FutsalFileName (boardId, fileName)
			VALUES (#{boardId}, #{fileName})
			""")
	void insertFileName(Integer boardId, String fileName);

	@Select("""
			SELECT fileName FROM FutsalFileName
			WHERE boardId = #{boardId}
			""")
	List<String> selectFileNameByBoardId(Integer boardId);

	@Delete("""
			DELETE FROM FutsalFileName
			WHERE boardId = #{boardId}
			""")
	void deleteFileNameByBoardId(Integer id);

	@Delete("""
			DELETE FROM FutsalFileName
			WHERE boardId = #{boardId}
				AND fileName = #{fileName}

			""")
	void deleteFileNameByBoardIdAndFileName(Integer boardId, String fileName);

}













