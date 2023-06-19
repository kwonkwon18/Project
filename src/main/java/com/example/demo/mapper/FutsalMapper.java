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
import com.example.demo.domain.FutsalBoard;
import com.example.demo.domain.FutsalParty;

@Mapper
public interface FutsalMapper {

	@Insert("""
			INSERT INTO FutsalBoard (title, body, writer, Lat, Lng, people, time, address)
			VALUES (#{title}, #{body}, #{writer}, #{Lat}, #{Lng}, #{people}, #{time}, #{address})
			""")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int insert(FutsalBoard futsalBoard);

	@Select("""
			select * from FutsalBoard;
			""")
	List<FutsalBoard> selectList();

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
			    FutsalBoard r
			    LEFT JOIN FutsalParty rp ON r.id = rp.boardId
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
	FutsalBoard selectById(Integer id);

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
			    FutsalBoard r
			    LEFT JOIN FutsalParty rp ON r.id = rp.boardId
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
	FutsalBoard selectByIdForMate(Integer id);

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
			   FROM FutsalBoard b
			   where b.writer = #{writer}
					""")
	@ResultMap("boardResultMap")
	List<FutsalBoard> selectMyPageInfo(String writer);

	@Select("""
			select boardId ,memberId
			from FutsalParty p left join FutsalBoard b ON p.boardId = b.id
			where userId = #{writer}
			""")
	List<FutsalParty> selectMemberId(String writer);

	@Select("""
			select boardId ,memberId
			from FutsalParty p left join FutsalBoard b ON p.boardId = b.id
			where boardId = #{boardId} and userId = #{writer}
			""")
	List<FutsalParty> selectMemberIdByBoardId(Integer boardId, String writer);

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
			    FutsalBoard r
			    LEFT JOIN FutsalParty rp ON r.id = rp.boardId
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
	List<FutsalBoard> selectMate();

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
			    FutsalBoard r
			    LEFT JOIN FutsalParty rp ON r.id = rp.boardId

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
	List<FutsalBoard> selectMateByDistance(List<String> addressList, String type, String search);

	@Select("""
			         select boardId ,memberId
			from FutsalParty p left join FutsalBoard b ON p.boardId = b.id
			""")
	List<FutsalParty> selectMember();

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
			from FutsalParty p left join FutsalBoard b ON p.boardId = b.id
			where boardId = #{boardId} group by p.boardId, p.memberId;
						""")
	@ResultMap("boardResultMap2")
	List<FutsalParty> selectForMemberIdByBoardId(Integer boardId);

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
			   FROM FutsalBoard b left join FutsalParty p on b.id = p.boardId
			   where b.writer = #{nickName} or p.memberId = #{nickName2};
			""")
	List<FutsalBoard> selectTotalMyPageInfo(String nickName, String nickName2);

	@Update("""
			UPDATE FutsalBoard
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
	boolean updateBoardById(FutsalBoard futsalBoard);

	@Delete("""
			DELETE FROM FutsalBoard
			WHERE id = #{id}
			""")
	boolean deleteById(Integer id);

	@Select("""
			SELECT * FROM FutsalBoard
			WHERE address LIKE '%${searchTerm}%'
			""")
	List<FutsalBoard> selectBySearchTerm(String searchTerm);

	@Delete("""
			delete from FutsalFileName
			where boardId = #{id} AND fileName = #{fileName}
			""")
	void deleteFileNameByBoardIdAndFileName(Integer id, String fileName);

//	@Select("""
//			<scipt>
//			   SELECT *
//
//			FROM FutsalBoard
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
