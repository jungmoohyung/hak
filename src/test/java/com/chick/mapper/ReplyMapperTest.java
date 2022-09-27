package com.chick.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.chick.dto.Criteria;
import com.chick.dto.Member;
import com.chick.dto.Reply;
import com.chick.dto.ReplyVO;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class ReplyMapperTest {

	private static final Logger log = LoggerFactory.getLogger("ReplyMapperTest.class");
	
	@Autowired
	private ReplyMapper mapper;
	
//	@Test
//	public void test() {
//		Reply r = new Reply();
//		r.setComment("안녕하세요");
//		r.setBoardNo("129");
//		r.setId("joy");
//		
//		mapper.insert(r);
//		
//	}
	
//	@Test
//	public void testList() {
//		Criteria cri = new Criteria(1,5);
//		List<ReplyVO> list = mapper.getList(cri, 129L);
//		for (ReplyVO i : list) {
//			log.info("댓글내용:"+i.getContent());
//		}
//	}
	
//	@Test
//	public void testUpdate() {
//		ReplyVO vo = new ReplyVO();
//		vo.setContent("행복합니다");
//		vo.setSeqno(74L);
//		int rs = mapper.modify(vo);
//		log.info("응답번호 :" + rs);
//	}
	
	@Test
	public void testDelete() {
		int rs = mapper.delete(61L);
		log.info("응답번호 :" + rs);
	}
}
