package com.example.demo.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.demo.domain.RunningLike;



@Mapper
public interface RunningLikeMapper {

	// 라이크 추가해주기
	@Insert("""
			insert into RunningLike
			values (#{boardId}, #{memberId})
			""")
	int insert(RunningLike like);

	// 라이크 삭제하기, 한 게시물당 여러개의 라이크가 가능하기 때문에 두개의 조건을 줌
	@Delete("""
			delete from RunningLike
			where boardId = #{boardId}
			and memberId = #{memberId}
			""")
	int delete(RunningLike like);

	
	// 게시물 별 라이크 갯수 세어주기
	@Select("""
			select count(*) from RunningLike
			where boardId = #{boardId}
			""")
	Integer countByBoardId(Integer boardId);

	// 
	@Select("""
			select *
			from RunningLike
			where boardId = #{boardId}
			and memberId = #{memberId}
			""")
	RunningLike select(Integer boardId, String memberId);

	@Delete("""
			delete from RunningLike
			where boardId = #{boardId}
			""")
	void deleteByBoardId(Integer boardId);

	@Delete("""
			delete from RunningLike
			where memberId = #{memberId}
			""")
	void deleteByMemberId(String memberId);

}
