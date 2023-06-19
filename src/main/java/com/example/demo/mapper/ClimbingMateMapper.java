package com.example.demo.mapper;

import java.util.*;

import org.apache.ibatis.annotations.*;

import com.example.demo.domain.*;

@Mapper
public interface ClimbingMateMapper {

	@Insert("""
			INSERT INTO ClimbingMate (title, body, writer, Lat, Lng, time, address, people)
			VALUES (#{title}, #{body}, #{writer}, #{Lat}, #{Lng}, #{time}, #{address}, #{people})
			""")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int insert(ClimbingMate climbingMate);

	@Select("""
			SELECT * FROM ClimbingMate
			ORDER BY Id DESC;
			""")
	List<ClimbingMate> selectList();

	@Select("""
			SELECT
			    c.id,
			    c.title,
			    c.body,
			    c.inserted,
			    c.writer,
			    c.Lat,
			    c.Lng,
			    c.people,
			    c.address,
			    c.time,
			    m.userId,
			    COUNT(cp.boardId) AS currentNum
			FROM
			    ClimbingMate c
			    LEFT JOIN ClimbingParty cp ON c.id = cp.boardId and participation = 1
			    LEFT JOIN Member m ON c.writer = m.nickName
			WHERE
			    c.id = #{id}
			GROUP BY
			    c.id,
			    c.title,
			    c.body,
			    c.inserted,
			    c.writer,
			    c.Lat,
			    c.Lng,
			    c.people,
			    c.address;
			""")
	@ResultMap("climbingMateResultMap")
	ClimbingMate selectById(Integer id);

	@Select("""
			select
			   c.id,
			   c.title,
			   c.body,
			   c.writer,
			   c.inserted,
			   c.Lat,
			   c.Lng,
			   c.people,
			   c.time,
			   c.address
			   FROM ClimbingMate c
			   where b.writer = #{writer}
			""")
	@ResultMap("climbingMateResultMap")
	List<ClimbingMate> selectMyPageInfo(String writer);

	@Select("""
			select p.boardId , p.memberId
			from ClimbingParty p left join ClimbingMate c ON p.boardId = c.id
			where p.userId = #{writer}
			""")
	List<ClimbingParty> selectMemberId(String writer);

	@Select("""
			select boardId , memberId
			from ClimbingParty p
				left join ClimbingMate c ON p.boardId = c.id
			where userId = #{writer} and boardId = #{boardId} and participation = 1;
			""")
	List<ClimbingParty> selectMemberIdByBoardId(Integer boardId, String writer);

	@Select("""
			SELECT
			    c.id,
			    c.title,
			    c.body,
			    c.inserted,
			    c.writer,
			    c.Lat,
			    c.Lng,
			    c.people,
			    COUNT(cp.boardId) AS currentNum
			FROM
			    ClimbingMate c
			    LEFT JOIN ClimbingParty cp ON c.id = cp.boardId
			GROUP BY
			    c.id,
			    c.title,
			    c.body,
			    c.inserted,
			    c.writer,
			    c.Lat,
			    c.Lng,
			    c.people
			""")
	@ResultMap("climbingMateResultMap")
	List<ClimbingMate> selectMate();

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
	boolean deleteById(Integer id);

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

	@Select("""
			select boardId ,memberId
			from ClimbingParty p left join ClimbingBoard b ON p.boardId = b.id
			""")
	List<ClimbingParty> selectMember();

	@Select("""
			SELECT * FROM ClimbingMate
			WHERE address LIKE '%${searchTerm}%'
			""")
	List<ClimbingMate> selectBySearchTerm(String searchTerm);

	@Select("""
			select * from Member where userId = #{userId}
			""")
	List<Member> selectUserId(String userId);

	@Select("""
			select p.boardId , p.memberId, p.userId
			from ClimbingParty p left join ClimbingMate b ON p.boardId = b.id
			where boardId = #{boardId} group by p.boardId, p.memberId;
						""")
	@ResultMap("climbingMateResultMap2")
	List<ClimbingParty> selectForMemberIdByBoardId(Integer boardId);

	@Select("""
			select * from Member where userId = #{userId}
			""")
	Member getNickName(String name);

	@Select("""
			select * from Member where userId = #{userId}
			""")
	Member selectMemberById(String name);

	// 주소 반영한 것
	@Select("""
			<script>
			<bind name="pattern" value="'%' + mateSearch + '%'" />
			SELECT
			    c.id,
			    c.title,
			    c.body,
			    c.inserted,
			    c.writer,
			    c.Lat,
			    c.Lng,
			    c.people,
			    c.time,
			    c.address,
			    COUNT(cp.boardId) AS currentNum
			FROM
			    ClimbingMate c
			    LEFT JOIN ClimbingParty cp ON c.id = cp.boardId

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

				 AND time > DATE_SUB(NOW(), INTERVAL 3 DAY)

				</where>

			GROUP BY
			    c.id,
			    c.title,
			    c.body,
			    c.inserted,
			    c.writer,
			    c.Lat,
			    c.Lng,
			    c.people,
			    c.time,
			    c.address
			    ORDER BY c.inserted desc
			    </script>
					""")
	List<ClimbingMate> selectMateByDistance(List<String> addressList, String type, String mateSearch);

	@Select("""
			select * from Member where userId = #{userId}
			""")
	Member selecMemberUserId(String userId);

	@Select("""
			select
			    c.id,
			    c.title,
			    c.body,
			    c.writer,
			    c.inserted,
			    c.Lat,
			    c.Lng,
			    c.people,
			    c.time,
			    c.address,
			    p.memberId
			from ClimbingMate c
			left join ClimbingParty p on c.id = p.boardId
			where (c.writer = #{nickName} or p.memberId = #{nickName})
			    and c.writer <> p.memberId
			GROUP BY b.id
			order by c.inserted desc;
								         """)
	List<ClimbingMate> selectTotalMyPageInfo(String nickName);

	@Select("""
			select p.boardId , p.memberId, p.userId
			from ClimbingParty p left join ClimbingMate c ON p.boardId = c.id
			where boardId = #{boardId} and participation = 0 group by p.boardId, p.memberId;
						""")
	@ResultMap("climbingMateResultMap2")
	List<ClimbingParty> selectWaitingMemberIdByBoardIdForModal(Integer boardId);

	@Select("""
			select boardId ,memberId
			from ClimbingParty p
				left join ClimbingMate c ON p.boardId = c.id
			where boardId = #{boardId} and userId = #{writer} and participation = 0
			""")
	List<ClimbingParty> selectWaitingMemberIdByBoardId(Integer boardId, String writer);

	@Select("""
			select boardId ,memberId
			from ClimbingParty p
				left join ClimbingMate c ON p.boardId = c.id
			where boardId = #{boardId} and userId = #{writer} and participation = 2
			""")
	List<ClimbingParty> selectRejectMemberIdByBoardId(Integer boardId, String writer);

	@Select("""
			select p.boardId , p.memberId, p.userId
			from ClimbingParty p left join ClimbingMate c ON p.boardId = c.id
			where boardId = #{boardId} and participation = 2 group by p.boardId, p.memberId;
						""")
	@ResultMap("climbingMateResultMap2")
	List<ClimbingParty> selectRejectMemberIdByBoardIdForModal(Integer boardId);

	@Update("""
			UPDATE ClimbingMate
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
	boolean updateBoardById(ClimbingMate climbingMate);

	@Select("""
			Select userId from Member where nickName = #{hostNickName}
			""")
	String findHost(String hostNickName);

}