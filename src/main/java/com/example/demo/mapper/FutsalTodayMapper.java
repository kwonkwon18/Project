package com.example.demo.mapper;

import java.util.*;

import org.apache.ibatis.annotations.*;

import com.example.demo.domain.*;


@Mapper
public interface FutsalTodayMapper {

	@Insert("""
			INSERT INTO FutsalToday (title, body, writer)
			VALUES (#{title}, #{body}, #{writer})
			""")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	Integer insertFutsalToday(FutsalToday futsalToday);

	@Insert("""
			insert into FutsalFileName (boardId, fileName)
			values (#{boardId}, #{fileName})
			""")
	void insertFileName(Integer boardId, String fileName);

	@Select("""
			SELECT
			    r.id,
			    r.title,
			    r.body,
			    r.inserted,
			    r.writer,
			    f.fileName
			FROM
			    FutsalToday r
			    LEFT JOIN FutsalFileName f ON r.id = f.boardId
			    LEFT JOIN FutsalLike rl ON r.id = rl.boardId
			    LEFT JOIN FutsalComment rc ON r.id = rc.boardId
			WHERE
				r.title LIKE CONCAT('%', #{search}, '%')
			    order by r.inserted desc
						""")
	@ResultMap("boardResultMap")
	List<FutsalToday> selectList(String search);

	@Select("""
			select * from FutsalToday where id = #{id};
			""")
	FutsalToday selectById(Integer id);

	@Select("""
			SELECT
			    r.id,
			    r.title,
			    r.body,
			    r.inserted,
			    r.writer,
			    f.fileName,
			    m.userId
			FROM
			    FutsalToday r
			    LEFT JOIN FutsalFileName f ON r.id = f.boardId
			    LEFT JOIN Member m on r.writer = m.nickName
			WHERE
			    r.id = #{id}
						""")
	@ResultMap("boardResultMap")
	FutsalToday selectFileNameById(Integer id);

	@Update("""
			UPDATE FutsalToday
			SET
				title = #{title},
				body = #{body}
			WHERE
				id = #{id}
			""")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int update(FutsalToday futsalToday);

	@Delete("""
			DELETE FROM FutsalFileName
			WHERE boardId = #{boardId}
			""")
	Integer deletefileNameById(Integer id);

	@Delete("""
			DELETE FROM FutsalToday
			WHERE id = #{id}
			""")
	boolean deleteTodayById(Integer id);

}
