package com.example.demo.service;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
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

	public Map<String, Object> futsalPartyMember(FutsalPartyMember futsalPartyMember) {
		Map<String, Object> result = new HashMap<>();
	
		futsalPartyMember.setFutsalApplyMember("test");
		
		result.put("member", false);
		
		Integer deleteCnt = futsalPartyMapper.deletePartyMember(futsalPartyMember);
		
		if(deleteCnt != 1) {
			Integer insertCnt = futsalPartyMapper.insertPartyMember(futsalPartyMember);
			result.put("member", true);		
		}
		
		Integer count = futsalPartyMapper.countByFutsalPartyId(futsalPartyMember.getFutsalPartyId());
		result.put("count", count);
		
		return result;
	}


	
	
}
