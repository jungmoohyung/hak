package com.chick.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.chick.dto.Member;


public interface MemberService {
	
	Map<String, String> login(String id,String pw);
	
	int insert(Member member);
	
	int idDoubleCheck(String id);
	
	public List<Member> list();
}
