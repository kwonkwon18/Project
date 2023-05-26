package com.example.demo.mapper;

import java.util.*;

import org.apache.ibatis.annotations.*;

import com.example.demo.domain.*;

@Mapper
public interface FutsalMapper {

	@Select("""
			SELECT * 
			FROM FutsalBoard
			""")
	List<FutsalBoard> selectAll();
	
	@Select("""
			SELECT *
			FROM FutsalBoard
			WHERE id = #{id}
			""")
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

	@Select("""
			INSERT INTO FutsalFileName (boardId, fileName)
			VALUES (#{boardId}, #{fileName})
			""")
	Integer insertFileName(Integer boardId, String fileName);

}













