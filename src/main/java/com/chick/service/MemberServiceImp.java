package com.chick.service;

import java.util.List;
import java.util.Map;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chick.dto.Member;
import com.cosmos.dao.MemberDao;
import com.cosmos.dao.MemberDaoimp;

@Service
public class MemberServiceImp implements MemberService {
	
	@Autowired
	private MemberDao mdo;
	
	/*
	public MemberServiceImp(MemberDao mdo) {
		this.mdo = mdo;
	}
	*/
	@Override
	public Map<String, String> login(String id, String pw) {
		
		return mdo.loginProc(id, pw);
	}

	@Override
	public int insert(Member member) {
		
		return mdo.insertMember(member);
		
	}
	@Override
	public int idDoubleCheck(String id) {
		return mdo.selectById(id);
	}
	@Override
	public List<Member> list() {
		return mdo.getMember();
	}

}
