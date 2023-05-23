package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.example.demo.domain.RunningBoard;

@Mapper
public interface RunningMapper {

	@Insert("""
			INSERT INTO RunningBoard (title, body, writer, Lat, Lng)
			VALUES (#{title}, #{body}, #{writer}, #{Lat}, #{Lng})
			""")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int insert(RunningBoard runningBoard);

	
	@Select("""
			select * from RunningBoard;
			""")
	List<RunningBoard> selectList();

	@Select("""
			select * from RunningBoard 
			where id = #{id}
			""")
	RunningBoard selectById(Integer id);

}
