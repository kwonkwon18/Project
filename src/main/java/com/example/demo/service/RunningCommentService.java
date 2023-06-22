package com.example.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.FutsalComment;
import com.example.demo.domain.RunningComment;
import com.example.demo.mapper.RunningCommentMapper;

@Service
@Transactional(rollbackFor = Exception.class)
public class RunningCommentService {

	@Autowired
	private RunningCommentMapper mapper;

	public Map<String, Object> add(RunningComment comment, Authentication authentication) {
		comment.setMemberId(authentication.getName());

		var res = new HashMap<String, Object>();

		int cnt = mapper.insert(comment);
		if (cnt == 1) {
			res.put("message", "댓글이 등록되었습니다.");
		} else {
			res.put("message", "댓글이 등록되지 않았습니다.");
		}

		return res;
	}

	public List<RunningComment> list(Integer boardId, Authentication authentication) {
		List<RunningComment> comments = mapper.selectAllByBoardId(boardId);
		if (authentication != null) {

			for (RunningComment comment : comments) {
				comment.setEditable(comment.getMemberId().equals(authentication.getName()));
			}
		}
		return comments;
	}

	public RunningComment get(Integer id) {
		return mapper.selectById(id);
	}

	public Map<String, Object> update(RunningComment comment) {
		int cnt = mapper.update(comment);
		System.out.println(cnt + "100");
		var res = new HashMap<String, Object>();
		if (cnt == 1) {
			res.put("message", "댓글이 수정되었습니다.");
		} else {
			res.put("message", "댓글이 수정되지 않았습니다.");
		}
		return res;
	}

	public Map<String, Object> remove(Integer id) {
		int cnt = mapper.deleteById(id);

		var res = new HashMap<String, Object>();

		if (cnt == 1) {
			res.put("message", "댓글이 삭제되었습니다.");
		} else {
			res.put("message", "댓글이 삭제 되지 않았습니다.");
		}

		return res;
	}

}
