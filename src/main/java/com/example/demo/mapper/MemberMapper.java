package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.domain.Member;

@Mapper
public interface MemberMapper {

	@Select("""
			SELECT nickName FROM Member
			WHERE userId = #{userId}
			""")
	String getNickNameByUserId(String userId);

	// ** 서재권 추가내용
	@Select("""
			SELECT * FROM Member
			WHERE userId = #{userId}
			""")
	Member getMemberInfoByUserId(String userId);

	@Insert("""
			insert into Member
			(userId, password, name, nickName, birth, gender, address, phone, email, introduce)
			values (#{userId}, #{password}, #{name} ,#{nickName}, #{birth}, #{gender}, #{address}, #{phone}, #{email}, #{introduce})
			""")
	Integer insertMember(Member member);

	////////////////
	@Select("""
			SELECT *
			FROM Member
			ORDER BY inserted DESC
			""")
	List<Member> selectAll();

	@Select("""
			SELECT *
			FROM Member
			WHERE userId = #{userId}
			""")
	Member selectById(String integer);

	@Delete("""
			DELETE FROM Member
			WHERE userId=#{userId}
			""")
	Integer deleteById(String userId);

	@Update("""
			UPDATE Member
			SET password = #{password},
			   name     = #{name},
			   nickName = #{nickName},
			   birth     = #{birth},
			   gender     = #{gender},
			   address  = #{address},
			   phone    = #{phone},
			   email    = #{email},
			   introduce= #{introduce}
			WHERE
			   userId = #{userId};
			""")
	Integer update(Member member);

	@Select("""
			SELECT userId FROM Member
			WHERE nickName = #{invitedNickName}
			""")
	String getUserIdSelectByNickName(String invitedNickName);

	@Select("""
			SELECT userId FROM Member
			WHERE nickName LIKE CONCAT('%', #{search}, '%')
			""")
	List<String> UserIdSelectBySearch(String search);

	@Update("""
			UPDATE Member
			SET password = #{password},
			   name     = #{name},
			   nickName = #{nickName},
			   birth     = #{birth},
			   gender     = #{gender},
			   address  = #{address},
			   phone    = #{phone},
			   email    = #{email},
			   introduce= #{introduce}
			WHERE
			   id = #{id};
			""")
	Integer updateMember(Member member);

	@Select("""
			SELECT id FROM Member
			WHERE userId = #{userId}
			""")
	Integer getId(String userId);

	@Select("""
			SELECT *
			FROM Member
			WHERE NickName = #{NickName}
			""")
	Member selectByNickName(String nickName);

	@Select("""
			SELECT *
			FROM Member
			WHERE email = #{email}
			""")
	Member selectByEmail(String email);

	// 멤버 삭제 =====
	// 러닝 ***********
	// 러닝 메이트 신청 인원 삭제
	@Delete("""
			DELETE FROM RunningParty
			WHERE host = #{userId} or guest = #{userId}
			""")
	void deleteRunningPartyById(String userId);

	// 러닝 올린 게시물 삭제
	@Delete("""
			DELETE FROM RunningBoard
			WHERE writer = #{nickName}
			""")
	void deleteRunningBoardById(String nickName);

	// 러닝 today like 삭제
	@Delete("""
			DELETE FROM RunningLike
			WHERE memberId = #{userId}
			""")
	void deleteRunningTodayLikeById(String userId);

	// 러닝 today like 삭제
	@Delete("""
			DELETE FROM RunningComment
			WHERE memberId = #{userId}
			""")
	void deleteRunningTodayCommentById(String userId);

	// 러닝 today 삭제
	@Delete("""
			DELETE FROM RunningToday
			WHERE writer = #{nickName}
			""")
	void deleteRunningTodayById(String nickName);

	// 등산 ***********
	// 등산 메이트 신청 인원 삭제 1
	@Delete("""
			DELETE FROM ClimbingParty
			WHERE host = #{userId} or guest = #{userId}
			""")
	void deleteClimbingPartyById(String userId);

	// 등산 올린 게시물 삭제 2
	@Delete("""
			DELETE FROM ClimbingMate
			WHERE writer = #{nickName}
			""")
	void deleteClimbingMateById(String nickName);

	// 등산 today like 삭제 1
	@Delete("""
			DELETE FROM ClimbingTodayLike
			WHERE memberId = #{userId}
			""")
	void deleteClimbingTodayLikeById(String userId);

	// 등산 today comment 삭제 2
	@Delete("""
			DELETE FROM ClimbingTodayComment
			WHERE memberId = #{userId}
			""")
	void deleteClimbingTodayCommentById(String userId);

	// 등산 today 삭제 3
	@Delete("""
			DELETE FROM ClimbingToday
			WHERE writer = #{nickName}
			""")
	void deleteClimbingTodayById(String nickName);

	// 등산 Course like 삭제 1
	@Delete("""
			DELETE FROM ClimbingCourseLike
			WHERE memberId = #{userId}
			""")
	void deleteClimbingCourseLikeById(String userId);

	// 등산 Course comment 삭제 2
	@Delete("""
			DELETE FROM ClimbingCourseComment
			WHERE memberId = #{userId}
			""")
	void deleteClimbingCourseCommentById(String userId);

	// 등산 Course 삭제 3
	@Delete("""
			DELETE FROM ClimbingCourse
			WHERE writer = #{nickName}
			""")
	void deleteClimbingCourseById(String nickName);

	@Select("""
			SELECT id
			FROM RunningToday
			WHERE writer = #{nickName}
			""")
	List<Integer> selectIdByWriter(String nickName);

	@Delete("""
			DELETE FROM RunningFileName
			WHERE boardId = #{boardId}
			""")
	void deleteRunningFileNameById(Integer boardId);

	@Delete("""
			DELETE FROM ClimbingToday
			WHERE todayId = #{todayId}
			""")
	List<Integer> selectClimbIdByWriter(Integer todayId);

	@Select("""
			SELECT id
			FROM ClimbingToday
			WHERE writer = #{nickName}
			""")
	List<Integer> selectClimbingTodayByWriter(String nickName);

	@Delete("""
			DELETE FROM ClimbingTodayFileName
			WHERE todayId = #{todayId}
			""")
	void deleteClimbingTodayFileNameById(Integer todayId);

	@Select("""
			SELECT id
			FROM ClimbingCourse
			WHERE writer = #{nickName}
			""")
	List<Integer> selectClimbCourseIdByWriter(String nickName);

	@Delete("""
			DELETE FROM ClimbingCourseFileName
			WHERE courseId = #{courseId}
			""")
	void deleteClimbingCourseFileNameById(Integer courseId);

	@Delete("""
			DELETE FROM Member
			WHERE userId = #{userId}
			""")
	int deleteMember(String userId);

}