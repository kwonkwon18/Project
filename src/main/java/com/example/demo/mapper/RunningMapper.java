package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.example.demo.domain.RunningBoard;

@Mapper
public interface RunningMapper {

	@Insert("""
			INSERT INTO RunningBoard (title, body, writer, Lat, Lng, people)
			VALUES (#{title}, #{body}, #{writer}, #{Lat}, #{Lng}, #{people})
			""")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int insert(RunningBoard runningBoard);

	@Select("""
			select * from RunningBoard;
			""")
	List<RunningBoard> selectList();

	@Select("""
			select
				r.id,
				r.title,
				r.body,
				r.inserted,
				r.writer,
				r.Lat,
				r.Lng,
				r.people,
				(select count(*)
				from RunningParty where boardId = r.id) currentNum
				from RunningBoard r left join RunningParty on r.id = RunningParty.boardId
			where r.id = #{id}
			""")
	@ResultMap("boardResultMap")
	RunningBoard selectById(Integer id);

}

