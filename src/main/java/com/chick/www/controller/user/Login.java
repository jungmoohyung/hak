package com.chick.www.controller.user;


import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.chick.common.Loginimpl;
import com.cosmos.dao.MemberDao;
import com.cosmos.dao.MemberDaoimp;


@Controller
public class Login {
	private static final Logger log = LoggerFactory.getLogger(Login.class);
	@Autowired
	MemberDao dao;
       
    @PostMapping("login")
	public String login(@RequestParam("id") String id, @RequestParam("password") String pw, 
						HttpSession sess,Model model){
//    public String login(Member member){
		
		String viewPage = null;

		Map<String,String> map = dao.loginProc(id, pw);

		switch(map.get("msg")) {
		case "ok" : //로그인성공
			//세션설정
			Loginimpl loginUser = new Loginimpl(id, map.get("name"));
			
			sess.setAttribute("loginUser", loginUser);//세션으로 지정하는순간 이벤트발생
			//System.out.println(status.get("name"));
			
			model.addAttribute("msg", "loginOK");
			//resp.sendRedirect("mainpage.do");
			viewPage = "redirect:/";
			break;
		default : //로그인실패
			model.addAttribute("msg", map.get("msg"));
			viewPage = "index";
		}		
		return viewPage;
	}
}
