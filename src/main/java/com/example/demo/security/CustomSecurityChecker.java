package com.example.demo.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.example.demo.domain.FutsalBoard;
import com.example.demo.domain.FutsalComment;
import com.example.demo.domain.RunningParty;
import com.example.demo.mapper.FutsalCommentMapper;
import com.example.demo.mapper.RunningMapper;

@Component
public class CustomSecurityChecker {

	@Autowired
	private FutsalCommentMapper commentMapper;
	
	@Autowired
	private RunningMapper mapper;

	public boolean checkBoardJoinMember(Authentication authentication, String writer) {

		List<RunningParty> board = mapper.selectMemberId(writer);

		String username = authentication.getName();
		
		for(int i = 0 ; i < board.size() ; i++) {
			if(board.get(i).getMemberId().equals(username)) {
				
				return true;
				
			}
		}

		return false;

	}
	
	// 진명 추가
	public boolean checkCommentWriter(Authentication authentication,
			Integer commentId) {
		FutsalComment comment = commentMapper.selectById(commentId);
		
		return comment.getMemberId().equals(authentication.getName());
	}
	

}
