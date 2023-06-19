package com.example.demo.mapper;

import java.time.*;
import java.util.*;

import org.apache.ibatis.annotations.*;

import com.example.demo.domain.*;

@Mapper
public interface ClimbingTodayCommentMapper {

	@Select("""
			SELECT *
			FROM ClimbingTodayComment
			WHERE id = #{id}
			""")
	ClimbingTodayComment selectById(Integer id);

	@Insert("""
			INSERT INTO ClimbingTodayComment (boardId, content, memberId)
			VALUES (#{boardId}, #{content}, #{memberId})
			""")
	Integer insert(ClimbingTodayComment comment);

	@Select("""
			SELECT *
			FROM ClimbingTodayComment
			WHERE boardId = #{boardId}
			ORDER BY id
			""")
	List<ClimbingTodayComment> selectAllByBoardId(Integer boardId);

	@Update("""
			UPDATE ClimbingTodayComment
			SET
				content = #{content},
				inserted = #{inserted}
			WHERE
				id = #{id}
			""")
	Integer update(ClimbingTodayComment comment);

	@Delete("""
			DELETE FROM ClimbingTodayComment
			WHERE id = #{id}
			""")
	Integer deleteById(Integer id);

	@Delete("""
			DELETE FROM ClimbingTodayComment
			WHERE boardId = #{boardId}
			""")
	void deleteByBoardId(Integer boardId);

}
