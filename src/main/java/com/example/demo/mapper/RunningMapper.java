package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.example.demo.domain.RunningBoard;
import com.example.demo.domain.RunningParty;

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
			SELECT
			    r.id,
			    r.title,
			    r.body,
			    r.inserted,
			    r.writer,
			    r.Lat,
			    r.Lng,
			    r.people,
			    m.userId,
			    COUNT(rp.boardId) AS currentNum
			FROM
			    RunningBoard r
			    LEFT JOIN RunningParty rp ON r.id = rp.boardId
			    LEFT JOIN Member m ON r.writer = m.nickName
			WHERE
			    r.id = #{id}
			GROUP BY
			    r.id,
			    r.title,
			    r.body,
			    r.inserted,
			    r.writer,
			    r.Lat,
			    r.Lng,
			    r.people;
						""")
	@ResultMap("boardResultMap")
	RunningBoard selectById(Integer id);

	
	
	@Select("""
			select
			   b.id,
			   b.title,
			   b.body,
			   b.writer,
			   b.inserted,
			   b.Lat,
			   b.Lng,
			   b.people
			   FROM RunningBoard b 
			   where b.writer = #{writer}
					""")
	@ResultMap("boardResultMap")
	List<RunningBoard> selectMyPageInfo(String writer);

	

	
	@Select("""
            select boardId ,memberId 
			from RunningParty p left join RunningBoard b ON p.boardId = b.id
			where userId = #{writer}
			""")
	List<RunningParty> selectMemberId(String writer);
	
	
	@Select("""
			SELECT
			    r.id,
			    r.title,
			    r.body,
			    r.inserted,
			    r.writer,
			    r.Lat,
			    r.Lng,
			    r.people,
			    COUNT(rp.boardId) AS currentNum
			FROM
			    RunningBoard r
			    LEFT JOIN RunningParty rp ON r.id = rp.boardId
			GROUP BY
			    r.id,
			    r.title,
			    r.body,
			    r.inserted,
			    r.writer,
			    r.Lat,
			    r.Lng,
			    r.people
						""")
	@ResultMap("boardResultMap")
	List<RunningBoard> selectMate();

}
