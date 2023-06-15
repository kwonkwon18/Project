package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.domain.RunningToday;

@Mapper
public interface RunningTodayMapper {

	@Insert("""
			INSERT INTO RunningToday (title, body, writer)
			VALUES (#{title}, #{body}, #{writer})
			""")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	Integer insertRunningToday(RunningToday runningToday);

	@Insert("""
			insert into RunningFileName (boardId, fileName)
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
			    f.fileName,
			    (SELECT COUNT(*) FROM RunningLike WHERE boardId = r.id) likeCount,
			    (SELECT COUNT(*) FROM RunningComment WHERE boardId = r.id) commentCount
			FROM
			    RunningToday r
			    LEFT JOIN RunningFileName f ON r.id = f.boardId
			    LEFT JOIN RunningLike rl ON r.id = rl.boardId
			    LEFT JOIN RunningComment rc ON r.id = rc.boardId
			WHERE
			    r.title LIKE CONCAT('%', #{search}, '%')
			ORDER BY
			    r.inserted DESC
									""")
	@ResultMap("boardResultMap")
	List<RunningToday> selectList(String search);

	@Select("""
			select * from RunningToday where id = #{id};
			""")
	RunningToday selectById(Integer id);

	@Select("""
			SELECT
			    r.id,
			    r.title,
			    r.body,
			    r.inserted,
			    r.writer,
			    f.fileName,
			    m.userId,
			    (select count(*) from RunningLike where boardId = r.id) likeCount
			FROM
			    RunningToday r
			    LEFT JOIN RunningFileName f ON r.id = f.boardId
			    LEFT JOIN Member m on r.writer = m.nickName
			    LEFT JOIN RunningLike rl on r.id = rl.boardId
			WHERE
			    r.id = #{id}
						""")
	@ResultMap("boardResultMap")
	RunningToday selectFileNameById(Integer id);

	@Update("""
			UPDATE RunningToday
			SET
				title = #{title},
				body = #{body}
			WHERE
				id = #{id}
			""")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int update(RunningToday runningToday);

	@Delete("""
			DELETE FROM RunningFileName
			WHERE boardId = #{boardId}
			""")
	Integer deletefileNameById(Integer id);

	@Delete("""
			DELETE FROM RunningToday
			WHERE id = #{id}
			""")
	boolean deleteTodayById(Integer id);

}
