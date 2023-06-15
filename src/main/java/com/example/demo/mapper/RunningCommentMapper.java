package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.domain.RunningComment;

@Mapper
public interface RunningCommentMapper {

	@Select("""
			SELECT *
			FROM RunningComment
			WHERE id = #{id}
			""")
	RunningComment selectById(Integer id);

	@Insert("""
			INSERT INTO RunningComment (boardId, content, memberId)
			VALUES (#{boardId}, #{content}, #{memberId})
			""")
	Integer insert(RunningComment comment);

	@Select("""
			SELECT *
			FROM RunningComment
			WHERE boardId = #{boardId}
			ORDER BY id
			""")
	List<RunningComment> selectAllByBoardId(Integer boardId);

	@Update("""
			UPDATE RunningComment
				SET
				content = #{content}
			WHERE
				id = #{id}
			""")
	Integer update(RunningComment comment);

	@Delete("""
			DELETE FROM RunningComment
			WHERE id = #{id}
			""")
	Integer deleteById(Integer id);

}
