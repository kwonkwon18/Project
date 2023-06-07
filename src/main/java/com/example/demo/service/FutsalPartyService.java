package com.example.demo.service;

import java.util.*;

import javax.xml.transform.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.*;
import org.springframework.stereotype.*;

import com.example.demo.domain.*;
import com.example.demo.mapper.*;

import software.amazon.awssdk.services.s3.*;

@Service
public class FutsalPartyService {

	@Autowired
	private S3Client s3;
	
	@Value("${aws.s3.bucketName}")
	private String bucketName;
	
	@Autowired
	private FutsalPartyMapper futsalPartyMapper;

	public List<FutsalParty> partyList() {
		List<FutsalParty> partyList = futsalPartyMapper.selectPartyAll();
		return partyList;
	}

	public FutsalParty getFutsalParty(Integer id) {
		FutsalParty party = futsalPartyMapper.selectById(id);
		return party;
	}
	
	public boolean addFutsalParty(FutsalParty futsalParty) {
		int cnt = futsalPartyMapper.insert(futsalParty);
		
		return cnt == 1;
	}

	public Map<String, Object> futsalPartyMember(FutsalPartyMember futsalPartyMember,
			Authentication authentication) {
		
		// 멤버 객체 생성해서 userId로 받기
		Member member = futsalPartyMapper.selectByUserId(authentication.getName());
		FutsalParty party = futsalPartyMapper.selectById(futsalPartyMember.getFutsalPartyId());
		int applyNum = futsalPartyMapper.countByPartyId(futsalPartyMember.getFutsalPartyId());
		
		if (party.getMemberNum() > applyNum) {
			
			futsalPartyMember.setFutsalApplyMember(member.getNickName());
			
			Map<String, Object> result = new HashMap<>();
			
			result.put("member", false);
			
			Integer deleteCnt = futsalPartyMapper.deletePartyMember(futsalPartyMember);
			
			if(deleteCnt != 1) {
				Integer insertCnt = futsalPartyMapper.insertPartyMember(futsalPartyMember);
				result.put("member", true);		
			}
			
			Integer count = futsalPartyMapper.countByFutsalPartyId(futsalPartyMember.getFutsalPartyId());
			result.put("count", count);
			
			return result;
			
			
		} else {
			// 신청 불가능한 경우
			Map<String, Object> result = new HashMap<>();
			result.put("member", false);
			result.put("message", "신청이 불가능합니다.");
			return result;
		}
		
	}


	
	
}
