package com.example.demo.mapper;

import org.apache.ibatis.annotations.*;

import com.example.demo.domain.*;

@Mapper
public interface ClimbingLikeMapper {
	// 라이크 추가해주기
	@Insert("""
			insert into ClimbingLike
			values (#{boardId}, #{memberId})
			""")
	int insert(ClimbingLike like);

	// 라이크 삭제하기, 한 게시물당 여러개의 라이크가 가능하기 때문에 두개의 조건을 줌
	@Delete("""
			delete from ClimbingLike
			where boardId = #{boardId}
			and memberId = #{memberId}
			""")
	int delete(ClimbingLike like);

	
	// 게시물 별 라이크 갯수 세어주기
	@Select("""
			select count(*) from ClimbingLike
			where boardId = #{boardId}
			""")
	Integer countByBoardId(Integer boardId);

	// 
	@Select("""
			select *
			from ClimbingLike
			where boardId = #{boardId}
			and memberId = #{memberId}
			""")
	ClimbingLike select(Integer boardId, String memberId);

	@Delete("""
			delete from ClimbingLike
			where boardId = #{boardId}
			""")
	void deleteByBoardId(Integer boardId);

	@Delete("""
			delete from ClimbingLike
			where memberId = #{memberId}
			""")
	void deleteByMemberId(String memberId);
}
