package com.example.demo.mapper;

import java.util.*;

import org.apache.ibatis.annotations.*;

import com.example.demo.domain.*;

@Mapper
public interface FutsalPartyMapper {

	@Select("""
			SELECT *
			FROM FutsalParty
			ORDER BY id DESC
			""")
	List<FutsalParty> selectPartyAll();

	@Select("""
			SELECT *
			FROM FutsalParty
			WHERE id = #{id}
			""")
	FutsalParty selectById(Integer id);
	
	@Insert("""
			INSERT INTO FutsalParty(
				title,
				body,
				writer,
				Lat,
				Lng,
				memberNum,
				stadium,
				startDate,
				startTime,
				futsalGender)
			VALUES (#{title}, #{body}, #{writer}, #{Lat}, #{Lng}, #{memberNum}, #{stadium}, #{startDate}, #{startTime}, #{futsalGender})
			""")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int insert(FutsalParty futsalParty);

	@Delete("""
			DELETE FROM FutsalPartyMember
			WHERE futsalPartyId = #{futsalPartyId}
			AND futsalApplyMember = #{futsalApplyMember}
			""")
	Integer deletePartyMember(FutsalPartyMember futsalPartyMember);
	
	@Insert("""
			INSERT INTO FutsalPartyMember
			VALUES (#{futsalPartyId}, #{futsalApplyMember})
			""")
	Integer insertPartyMember(FutsalPartyMember futsalPartyMember);

	@Select("""
			SELECT COUNT(*)
			FROM FutsalPartyMember
			WHERE futsalPartyId = #{futsalPartyId}
			""")
	Integer countByFutsalPartyId(Integer futsalPartyId);


}
