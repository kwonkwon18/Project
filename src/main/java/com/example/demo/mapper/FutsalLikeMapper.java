package com.example.demo.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.demo.domain.FutsalLike;

@Mapper
public interface FutsalLikeMapper {

	@Delete("""
			DELETE FROM FutsalLike
			WHERE boardId = #{boardId} 
			  AND memberId = #{memberId} 
			""")
	Integer delete(FutsalLike like);

	@Insert("""
			INSERT INTO FutsalLike
			VALUES (#{boardId}, #{memberId})
			""")
	Integer insert(FutsalLike like);

	@Select("""
			SELECT COUNT(*) 
			FROM FutsalLike
			WHERE boardId = #{boardId}
			""")
	Integer countByBoardId(Integer boardId);
	
}
