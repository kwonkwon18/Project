package com.example.demo.mapper;

import java.util.*;

import org.apache.ibatis.annotations.*;

import com.example.demo.domain.*;

@Mapper
public interface ClimbingCommentMapper {

	@Select("""
			SELECT *
			FROM ClimbingComment
			WHERE id = #{id}
			""")
	ClimbingComment selectById(Integer id);

	@Insert("""
			INSERT INTO ClimbingComment (boardId, content, memberId)
			VALUES (#{boardId}, #{content}, #{memberId})
			""")
	Integer insert(ClimbingComment comment);

	@Select("""
			SELECT *
			FROM ClimbingComment
			WHERE boardId = #{boardId}
			ORDER BY id
			""")
	List<ClimbingComment> selectAllByBoardId(Integer boardId);

	@Update("""
			UPDATE ClimbingComment
			SET
				content = #{content}
			WHERE
				id = #{id}
			""")
	Integer update(ClimbingComment comment);

	@Delete("""
			DELETE FROM ClimbingComment
			WHERE id = #{id}
			""")
	Integer deleteById(Integer id);

	@Delete("""
			DELETE FROM ClimbingComment
			WHERE boardId = #{boardId}
			""")
	void deleteByBoardId(Integer boardId);

}
