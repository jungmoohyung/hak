package com.chick.www.controller.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.chick.dto.Member;
import com.chick.service.MemberService;
import com.chick.service.MemberServiceImp;

@Controller
@RequestMapping("/member/")
public class MemberController {
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	private MemberService ms;
	
	//	MemberService ms = new MemberServiceImp();
	
	
	/*
	public MemberController(MemberService ms) {
		super();
		this.ms = ms;
	}
	*/
	
	@RequestMapping("memReg2")
	public void memReg2(){
		logger.info("회원가입폼 매핑");
		
	}
	
	@PostMapping("register")
	public String register(Member member) {
		logger.info("회원가입처리 매핑");
//		logger.info("아이디:{}",member.getId());
//		logger.info("비번:{}",member.getPw());
//		logger.info("이룸:{}",member.getName());
		ms.insert(member);
		return "redirect:/";
		
	}
	@GetMapping("idDoubleCheck")
	public ResponseEntity<String> idDoubleCheck(@RequestParam("id") String id){
		logger.info("idDoubleCheck called..");
		String rs = Integer.toString(ms.idDoubleCheck(id));
		return new ResponseEntity<String>(rs, HttpStatus.OK);
		
	}
	
}