package com.example.demo.mapper;

import java.util.*;

import org.apache.ibatis.annotations.*;

import com.example.demo.domain.*;

@Mapper
public interface ChatMapper {

	@Select("""
			SELECT * FROM ChatRoom
			WHERE creater = #{creater}
			""")
	List<ChatRoom> invitedSelectByCreater(String creater);

	@Select("""
			SELECT Message FROM Chat
			WHERE chatRoomId = #{id}
			ORDER BY inserted DESC
			LIMIT 1;
			""")
	String lastMessageSelectById(Integer id);

}
