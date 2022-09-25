package com.chick.controller.admin;


import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.chick.common.Loginimpl;
import com.cosmos.dao.MemberDao;


@Controller
public class Login {
	private static final Logger log = LoggerFactory.getLogger(Login.class);
	@Autowired
	MemberDao dao;
       
    @PostMapping("login")
	public String login(@RequestParam("id") String id, @RequestParam("pw") String pw, 
						HttpSession sess,Model model, RedirectAttributes rttr){
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
			viewPage = "main";
			
			break;
		default : //로그인실패
			rttr.addFlashAttribute("msg", map.get("msg"));
			viewPage = "redirect:/admin/";
		}		
		return viewPage;
	}
}
