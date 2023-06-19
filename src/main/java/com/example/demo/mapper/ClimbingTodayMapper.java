package com.example.demo.mapper;

import java.util.*;

import org.apache.ibatis.annotations.*;

import com.example.demo.domain.*;

@Mapper
public interface ClimbingTodayMapper {

	@Insert("""
			INSERT INTO ClimbingToday (writer, title, body)
			VALUES (#{writer}, #{title}, #{body})
			""")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int insert(ClimbingToday climbingToday);

	// ****
//	@Select("""
//			SELECT * FROM ClimbingToday
//			ORDER BY Id DESC;
//			""")
//	List<ClimbingToday> selectList();
	
	
	@Select("""
		    SELECT
		        c.id,
		        c.title,
		        c.body,
		        c.writer,
		        c.inserted,
		        f.fileName,
		        (SELECT COUNT(*) FROM ClimbingTodayLike cl WHERE cl.boardId = c.id) likeCount,
		        (SELECT COUNT(*) FROM ClimbingTodayComment ct WHERE ct.boardId = c.id) commentCount
		    FROM 
		        ClimbingToday c 
		        LEFT JOIN ClimbingTodayFileName f ON c.id = f.todayId
		        LEFT JOIN ClimbingTodayLike cl on c.id = cl.boardId
		        LEFT JOIN ClimbingTodayComment ct on c.id = ct.boardId
		    WHERE c.title LIKE '%${todaySearch}%'
		    ORDER BY c.id DESC
		""")
		@ResultMap("climbingTodayResultMap")
		List<ClimbingToday> selectListByTodaySearch(String todaySearch);

	
	@Select("""
			SELECT
				c.id,
				c.title,
				c.body,
				c.writer,
				c.inserted,
				f.fileName
			FROM ClimbingToday c LEFT JOIN ClimbingTodayFileName f ON c.id = f.todayId
			WHERE c.title LIKE '%${todaySearch}%'
			ORDER BY Id DESC
			""")
	@ResultMap("climbingTodayResultMap")
	List<ClimbingToday> selectListForList();

	@Select("""
			SELECT
				c.id,
				c.title,
				c.body,
				c.writer,
				c.inserted,
				f.fileName,
				m.userId,
				(select count(*) from ClimbingTodayLike where boardId = c.id) likeCount
			FROM 
				ClimbingToday c 
				LEFT JOIN ClimbingTodayFileName f ON c.id = f.todayId
				LEFT JOIN Member m ON c.writer = m.nickName
				LEFT JOIN ClimbingTodayLike cl on c.id = cl.boardId
			WHERE c.id = #{id}
			""")
	@ResultMap("climbingTodayResultMap")
	ClimbingToday selectById(Integer id);

	@Insert("""
			INSERT INTO ClimbingTodayFileName (todayId, fileName)
			VALUES (#{todayId}, #{fileName})
			""")
	Integer insertFileName(Integer todayId, String fileName);

	@Delete("""
			DELETE FROM ClimbingTodayFileName
			WHERE todayId = #{todayId}
				AND fileName = #{fileName}
			""")
	void deleteFileNameByBoardIdAndFileName(Integer id, String fileName);

	@Update("""
			UPDATE ClimbingToday
			SET
				title = #{title},
				body = #{body}
			WHERE
				id = #{id}
			""")
	int update(ClimbingToday climbingToday);

	@Select("""
			SELECT fileName FROM ClimbingTodayFileName
			WHERE todayId = #{todayId}
			""")
	List<String> selectFileNamesByBoardId(Integer id);

	@Delete("""
			DELETE FROM ClimbingTodayFileName
			WHERE todayId = #{todayId}
			""")
	void deleteFileNameByTodayId(Integer id);

	@Delete("""
			DELETE FROM ClimbingToday
			WHERE id = #{id}
			""")
	int deleteById(Integer id);

	@Select("""
			SELECT * FROM Member
			WHERE userId = #{userId}
			""")
	Member selectMemberById(String userId);

	@Select("""
			SELECT * FROM ClimbingToday
			WHERE title LIKE '%${searchTerm}%'
			""")	
	List<ClimbingToday> selectBySearchTerm(String searchTerm);

	@Select("""
		    SELECT
		        c.id,
		        c.title,
		        c.body,
		        c.writer,
		        c.inserted,
		        f.fileName,
		        (SELECT COUNT(*) FROM ClimbingTodayLike cl WHERE cl.boardId = c.id) likeCount,
		        (SELECT COUNT(*) FROM ClimbingTodayComment ct WHERE ct.boardId = c.id) commentCount
		    FROM 
		        ClimbingToday c 
		        LEFT JOIN ClimbingTodayFileName f ON c.id = f.todayId
		        LEFT JOIN ClimbingTodayLike cl on c.id = cl.boardId
		        LEFT JOIN ClimbingTodayComment ct on c.id = ct.boardId
		    ORDER BY c.id DESC
		""")
	@ResultMap("climbingTodayResultMap")
	List<ClimbingToday> selectTodayList();
	

}
