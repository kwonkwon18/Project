package com.example.demo.mapper;

import org.apache.ibatis.annotations.*;

import com.example.demo.domain.*;

@Mapper
public interface ClimbingLikeMapper {
	// 라이크 추가해주기
	@Insert("""
			insert into ClimbingTodayLike
			values (#{boardId}, #{memberId})
			""")
	int insert(ClimbingTodayLike like);

	// 라이크 삭제하기, 한 게시물당 여러개의 라이크가 가능하기 때문에 두개의 조건을 줌
	@Delete("""
			delete from ClimbingTodayLike
			where boardId = #{boardId}
			and memberId = #{memberId}
			""")
	int delete(ClimbingTodayLike like);

	
	// 게시물 별 라이크 갯수 세어주기
	@Select("""
			select count(*) from ClimbingTodayLike
			where boardId = #{boardId}
			""")
	Integer countByBoardId(Integer boardId);

	// 
	@Select("""
			select *
			from ClimbingTodayLike
			where boardId = #{boardId}
			and memberId = #{memberId}
			""")
	ClimbingTodayLike select(Integer boardId, String memberId);

	@Delete("""
			delete from ClimbingTodayLike
			where boardId = #{boardId}
			""")
	void deleteByBoardId(Integer boardId);

	@Delete("""
			delete from ClimbingTodayLike
			where memberId = #{memberId}
			""")
	void deleteByMemberId(String memberId);

	/*--------------------------------------*/
	
	// 라이크 추가해주기
	@Insert("""
			insert into ClimbingCourseLike
			values (#{boardId}, #{memberId})
			""")
	int insert1(ClimbingCourseLike like);

	// 라이크 삭제하기, 한 게시물당 여러개의 라이크가 가능하기 때문에 두개의 조건을 줌
	@Delete("""
			delete from ClimbingCourseLike
			where boardId = #{boardId}
			and memberId = #{memberId}
			""")
	int delete1(ClimbingCourseLike like);

	
	// 게시물 별 라이크 갯수 세어주기
	@Select("""
			select count(*) from ClimbingCourseLike
			where boardId = #{boardId}
			""")
	Integer countByBoardId1(Integer boardId);

	// 
	@Select("""
			select *
			from ClimbingCourseLike
			where boardId = #{boardId}
			and memberId = #{memberId}
			""")
	ClimbingCourseLike select1(Integer boardId, String memberId);

	@Delete("""
			delete from ClimbingCourseLike
			where boardId = #{boardId}
			""")
	void deleteByBoardId1(Integer boardId);

	@Delete("""
			delete from ClimbingCourseLike
			where memberId = #{memberId}
			""")
	void deleteByMemberId1(String memberId);
}
