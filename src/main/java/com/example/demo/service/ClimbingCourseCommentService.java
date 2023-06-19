package com.example.demo.service;

import java.time.*;
import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import com.example.demo.domain.*;
import com.example.demo.mapper.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class ClimbingCourseCommentService {
	
	@Autowired
	private ClimbingCourseCommentMapper climbingCourseCommentMapper;

	public Map<String, Object> add(ClimbingCourseComment comment, Authentication authentication) {
		comment.setMemberId(authentication.getName());

		var res = new HashMap<String, Object>();

		int cnt = climbingCourseCommentMapper.insert(comment);
		if (cnt == 1) {
			res.put("message", "댓글이 등록되었습니다.");
		} else {
			res.put("message", "댓글이 등록되지 않았습니다.");
		}

		return res;
	}

	public List<ClimbingCourseComment> list(Integer boardId, Authentication authentication) {
		List<ClimbingCourseComment> comments = climbingCourseCommentMapper.selectAllByBoardId(boardId);
		if (authentication != null) {

			for (ClimbingCourseComment comment : comments) {
				comment.setEditable(comment.getMemberId().equals(authentication.getName()));
			}
		}
		return comments;
	}

	public ClimbingCourseComment get(Integer id) {
		return climbingCourseCommentMapper.selectById(id);
	}

	public Map<String, Object> update(ClimbingCourseComment comment) {
		comment.setInserted(LocalDateTime.now());
		int cnt = climbingCourseCommentMapper.update(comment);
		var res = new HashMap<String, Object>();
		if (cnt == 1) {
			res.put("message", "댓글이 수정되었습니다.");
		} else {
			res.put("message", "댓글이 수정되지 않았습니다.");
		}
		return res;
	}

	public Map<String, Object> remove(Integer id) {
		int cnt = climbingCourseCommentMapper.deleteById(id);

		var res = new HashMap<String, Object>();

		if (cnt == 1) {
			res.put("message", "댓글이 삭제되었습니다.");
		} else {
			res.put("message", "댓글이 삭제 되지 않았습니다.");
		}

		return res;
	}

}
