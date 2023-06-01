package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.example.demo.domain.Member;
import com.example.demo.domain.RunningBoard;
import com.example.demo.domain.RunningParty;

@Mapper
public interface RunningMapper {

	@Insert("""
			INSERT INTO RunningBoard (title, body, writer, Lat, Lng, people, time)
			VALUES (#{title}, #{body}, #{writer}, #{Lat}, #{Lng}, #{people}, #{time})
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
			    r.time,
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
			    r.people

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
			   b.people,
			   b.time
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
			select boardId ,memberId
			from RunningParty p left join RunningBoard b ON p.boardId = b.id
			where boardId = #{boardId} and userId = #{writer}
			""")
	List<RunningParty> selectMemberIdByBoardId(Integer boardId, String writer);

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
			    r.time,
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
			    r.people,
			    r.time
						""")
	@ResultMap("boardResultMap")
	List<RunningBoard> selectMate();

	@Select("""
			         select boardId ,memberId
			from RunningParty p left join RunningBoard b ON p.boardId = b.id
			""")
	List<RunningParty> selectMember();

	@Select("""
			select * from Member where userId = #{userId}
			""")
	Member selectMemberById(String userId);

	@Select("""
			select * from Member where userId = #{userId}
			""")
	List<Member> selectUserId(String userId);

	@Select("""
			select p.boardId , p.memberId, p.userId
			from RunningParty p left join RunningBoard b ON p.boardId = b.id
			where boardId = #{boardId} group by p.boardId, p.memberId;
						""")
	@ResultMap("boardResultMap2")
	List<RunningParty> selectForMemberIdByBoardId(Integer boardId);

	
	
	@Select("""
			select * from Member where userId = #{userId}
			""")
	Member getNickName(String userId);

}
