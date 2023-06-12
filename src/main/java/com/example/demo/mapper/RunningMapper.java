package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.domain.Member;
import com.example.demo.domain.RunningBoard;
import com.example.demo.domain.RunningParty;

@Mapper
public interface RunningMapper {

	@Insert("""
			INSERT INTO RunningBoard (title, body, writer, Lat, Lng, people, time, address)
			VALUES (#{title}, #{body}, #{writer}, #{Lat}, #{Lng}, #{people}, #{time}, #{address})
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
			    r.address,
			    m.userId,
			    COUNT(rp.boardId) AS currentNum
			FROM
			    RunningBoard r
			    LEFT JOIN RunningParty rp ON r.id = rp.boardId and participation = 1
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
			    r.people,
			    r.address

						""")
	@ResultMap("boardResultMap")
	RunningBoard selectById(Integer id);

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
			    r.address,
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
			    r.people,
			    r.address

						""")
	@ResultMap("boardResultMap")
	RunningBoard selectByIdForMate(Integer id);

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
			   b.time,
			   b.address
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

	/*
	 * @Select(""" select boardId ,memberId from RunningParty p left join
	 * RunningBoard b ON p.boardId = b.id where boardId = #{boardId} and userId =
	 * #{writer} """) List<RunningParty> selectMemberIdByBoardId(Integer boardId,
	 * String writer);
	 */
	
	@Select("""
			select boardId ,memberId
			from RunningParty p 
				left join RunningBoard b ON p.boardId = b.id
			where boardId = #{boardId} and userId = #{writer} and participation = 1
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
			    r.address,
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
			    r.time,
			    r.address
						""")
	@ResultMap("boardResultMap")
	List<RunningBoard> selectMate();

	// 주소 반영한 것
	@Select("""
			<script>
			<bind name="pattern" value="'%' + search + '%'" />
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
			    r.address,
			    COUNT(rp.boardId) AS currentNum
			FROM
			    RunningBoard r
			    LEFT JOIN RunningParty rp ON r.id = rp.boardId

				<where>

				<if test = "(type eq 'all') or (type eq 'title')">
				title LIKE #{pattern}
				</if>


				<if test = "(type eq 'all') or (type eq 'address')">
				OR address LIKE #{pattern}
				</if>



				<if test = "(type eq 'distance')">
				address IN (
				<foreach collection="addressList" item="item" separator=", ">
					#{item}
				</foreach>
				)
				</if>
				
				 AND time > DATE_SUB(NOW(), INTERVAL 3 DAY) -- 수정된 부분

				</where>

			GROUP BY
			    r.id,
			    r.title,
			    r.body,
			    r.inserted,
			    r.writer,
			    r.Lat,
			    r.Lng,
			    r.people,
			    r.time,
			    r.address
			    ORDER BY r.inserted desc
			    </script>
					""")
	List<RunningBoard> selectMateByDistance(List<String> addressList, String type, String search);

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
			select * from Member where userId = #{userId}
			""")
	Member selectMemberUserId(String userId);

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
			   b.time,
			   b.address,
			   memberId
			   FROM RunningBoard b left join RunningParty p on b.id = p.boardId
			   where b.writer = #{nickName} or p.memberId = #{nickName2};
			""")
	List<RunningBoard> selectTotalMyPageInfo(String nickName, String nickName2);

	@Update("""
			UPDATE RunningBoard
			SET
				title = #{title},
				body = #{body},
				writer = #{writer},
				Lat = #{Lat},
				Lng = #{Lng},
				people = #{people},
				time = #{time},
				address = #{address}
			WHERE
				id = #{id}
			""")
	boolean updateBoardById(RunningBoard runningBoard);

	@Delete("""
			DELETE FROM RunningBoard
			WHERE id = #{id}
			""")
	boolean deleteById(Integer id);

	@Select("""
			SELECT * FROM RunningBoard
			WHERE address LIKE '%${searchTerm}%'
			""")
	List<RunningBoard> selectBySearchTerm(String searchTerm);

	@Delete("""
			delete from RunningFileName
			where boardId = #{id} AND fileName = #{fileName}
			""")
	void deleteFileNameByBoardIdAndFileName(Integer id, String fileName);

//	@Select("""
//			<scipt>
//			   SELECT *
//
//			FROM RunningBoard
//			WHERE address IN ('은평구', '성동구', '서대문구', '마포구', '관악구')
//			ORDER BY CASE address
//
//			<foreach collection="addressList" item="item" index = "index">
//			WHEN #{item} THEN #{index}
//			</foreach>
//			ELSE 3
//			END;
//			</scipt>
//					""")
//	List<RunningBoard> searchByDistance(List<String> addressList);

}
