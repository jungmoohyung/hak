package com.chick.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.chick.dto.Member;
import com.google.gson.Gson;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml",
									"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
public class MemberControllerTest {

	private static final Logger log = LoggerFactory.getLogger("MemberControllerTest.class");
	
	@Inject
	private WebApplicationContext wac;
	//요청과 응답 처리
	private MockMvc mockMvc;
	//junit 임포트
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		log.info("mockMvc setup...");
		
	}
//	일반 컨트롤러 테스트
	@Test
	public void test() {
		
		try {
			String rs = mockMvc.perform(MockMvcRequestBuilders.post("/login")
					.param("id","joy").param("password","80888088"))
					.andReturn().getModelAndView().getViewName();
			log.info(rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
//	RESTController 테스트
	@Test
	public void test2() throws Exception {
		Member member = new Member();
		member.setId("joy");
		member.setPw("80888088");
		member.setName("홍길동");
		member.setGender("F");
		
		String jsonStr = new Gson().toJson(member);
		log.info(jsonStr);
		mockMvc.perform(MockMvcRequestBuilders.post("/ex/member/")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonStr)).andExpect(status().is(200));
	}
}