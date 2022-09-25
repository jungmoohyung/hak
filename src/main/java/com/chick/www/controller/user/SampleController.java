package com.chick.www.controller.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.chick.dto.Board;
import com.chick.dto.Member;

@Controller
public class SampleController {

	private static final Logger logger = LoggerFactory.getLogger(SampleController.class);
//	리턴이없으면 매핑값으로 리턴줌
	@RequestMapping("doA")
	public ModelAndView doA() {
		logger.info("doA called..");
		
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("/member/memReg2");
		mv.addObject("msg", "회원가입폼");
		
		return mv;
	}
	//리턴타입 : String인 경우
	//@ModelAttribute는 자동으로 해당 객체를 뷰까지 전달
	@RequestMapping("doB")
	public String doB(@ModelAttribute("msg") String message, Model model) {
		logger.info("doB called..");
		logger.info("doB called..{}",message);
		
		Member m = new Member();
		m.setName("홍길동");
		m.setId("아이디");
//		model.addAttribute("msg","곧 점심시간");
		model.addAttribute(m);
		
		return "redirect:/doA";
	}
	
	@RequestMapping("doC")
	public String doC(RedirectAttributes rttr) {
		Member m = new Member();
		m.setName("홍길동");
		m.setId("아이디");
		Board b = new Board();
		b.setTitle("ㅎㅇ");
		rttr.addFlashAttribute("m", m);
		rttr.addFlashAttribute(b);
		return "redirect:/doA";
	}
	
	//json 라이브러리 추가
	@RequestMapping("doJASON")
	public @ResponseBody Member dojson() {
		
		Member m = new Member();
		
		m.setName("홍길동");
		m.setId("아이디");
		m.setPw("패스워드");

		return m;
		
	}
}
